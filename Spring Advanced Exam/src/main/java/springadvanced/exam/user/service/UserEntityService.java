package springadvanced.exam.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.user.domain.userEntity.UserEntityUpdateBinding;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;
import springadvanced.exam.user.repository.UserRoleRepository;
import springadvanced.exam.utils.event.EventPublisher;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EventPublisher eventPublisher;

    public UserEntityService(UserEntityRepository userEntityRepository,
                             UserRoleRepository userRoleRepository, ModelMapper mapper,
                             PasswordEncoder passwordEncoder, EventPublisher eventPublisher) {
        this.userEntityRepository = userEntityRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    public boolean userExists(String username) {
        return this.userEntityRepository.findByUsername(username).isPresent();
    }

    public boolean emailExists(String email) {
        return this.userEntityRepository.findByEmail(email).isPresent();
    }

    public void registerUser(UserEntityDto userEntityDto) {
        UserEntity userEntity = this.mapper.map(userEntityDto, UserEntity.class);
        Cart cart = new Cart();
        UserRole user = new UserRole("ROLE_USER");

        cart.setUser(userEntity);
        user.setUser(userEntity);

        userEntity.setCart(cart);
        userEntity.setRoles(List.of(user));
        userEntity.setPersonalDiscount(0.0);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setTotalPurchases(0);
        userEntity.setRegisteredOn(LocalDateTime.now());
        this.userEntityRepository.saveAndFlush(userEntity);

        this.eventPublisher.userRegistered(userEntityDto);
    }


    public UserEntityDto findByUsername(String username) {
        return this.userEntityRepository.findByUsername(username)
                .map(u -> this.mapper.map(u, UserEntityDto.class))
                .orElse(null);
    }


    public String getUserOldPassword(String username) {

        return findByUsername(username).getPassword();
    }

    public void updateUser(UserEntityUpdateBinding userEntityUpdateBinding) {
        UserEntity updatedUserEntity = this.userEntityRepository.getOne(userEntityUpdateBinding.getId());

        updatedUserEntity.setUsername(userEntityUpdateBinding.getUsername());
        updatedUserEntity.setPassword(userEntityUpdateBinding.getNewPassword());
        updatedUserEntity.setEmail(userEntityUpdateBinding.getEmail());
        updatedUserEntity.setPassword(this.passwordEncoder.encode(userEntityUpdateBinding.getNewPassword()));
        this.userEntityRepository.save(updatedUserEntity);

    }

    public void deleteUser(String id) {

        String username = this.userEntityRepository.findById(id).orElse(null).getUsername();

        this.userEntityRepository.deleteById(id);

        this.eventPublisher.userDeleted(username);
    }

    public List<UserEntityDto> getAllUsers() {
        return this.userEntityRepository.findAll().stream()
                .map(u -> this.mapper.map(u, UserEntityDto.class))
                .filter(u -> !u.getUsername().equals("Admin1") && !u.getUsername().equals("Admin2"))
                .collect(Collectors.toList());
    }

    public UserEntityDto findById(String id) {
        return this.userEntityRepository.findById(id)
                .map(u -> this.mapper.map(u, UserEntityDto.class)).orElse(null);
    }

    @Transactional
    public void changeRole(String role, String id) {

        UserEntity userEntity = this.userEntityRepository.findById(id).orElse(null);


        if (role.equals("user")) {
            userEntity.getRoles().forEach(r -> {
                if (r.getRole().equals("ROLE_ADMIN")) {
                    this.userRoleRepository.deleteById(r.getId());
                }
            });

            userEntity.setRoles(userEntity.getRoles().stream().
                    filter(r -> r.getRole().equals("ROLE_USER")).collect(Collectors.toList()));

        } else if (role.equals("admin")) {
            UserRole admin = new UserRole("ROLE_ADMIN");
            admin.setUser(userEntity);
            List<UserRole> roles = userEntity.getRoles();
            roles.add(admin);
            userEntity.setRoles(roles);
        }

        this.userEntityRepository.save(userEntity);
    }

    public void increasePurchases(String username) {

        UserEntity user = this.userEntityRepository.findByUsername(username).orElse(null);
        user.setTotalPurchases(user.getTotalPurchases() + 1);

        this.userEntityRepository.save(user);

    }

    public void refreshPersonalDiscount() {
        this.userEntityRepository.findAll().stream()
                .map(user -> {
                    int totalPurchases = user.getTotalPurchases();
                    if (totalPurchases % 20 == 0) {
                        user.setPersonalDiscount(user.getPersonalDiscount() + 1.0);
                    }
                    return user;
                }).forEach(this.userEntityRepository::save);
    }


}
