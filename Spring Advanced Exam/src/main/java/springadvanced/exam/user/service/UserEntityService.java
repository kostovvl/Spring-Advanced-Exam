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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserEntityService(UserEntityRepository userEntityRepository,
                             ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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
        cart.setTotalPrice(new BigDecimal("0"));
        UserRole user = new UserRole("ROLE_USER");

        cart.setUser(userEntity);
        user.setUser(userEntity);

        userEntity.setCart(cart);
        userEntity.setRoles(List.of(user));
        userEntity.setPersonalDiscount(0.0);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setTotalPurchases(0);
        this.userEntityRepository.saveAndFlush(userEntity);
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

        this.userEntityRepository.deleteById(id);

    }

    public List<UserEntityDto> getAllUsers() {
        return this.userEntityRepository.findAll().stream()
                .map(u -> this.mapper.map(u, UserEntityDto.class))
                .collect(Collectors.toList());
    }
}
