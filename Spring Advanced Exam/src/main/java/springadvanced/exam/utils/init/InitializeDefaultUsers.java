package springadvanced.exam.utils.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeDefaultUsers implements CommandLineRunner {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public InitializeDefaultUsers(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.userEntityRepository.count() > 2) {
            return;
        }

        List<UserEntity> users = new ArrayList<>();

        for (int i = 1; i <=3 ; i++) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("TestUser" + i);
            userEntity.setPassword(passwordEncoder.encode("123"));
            userEntity.setEmail("TestUser" + i + "@test.com");
            userEntity.setPersonalDiscount(0.0);
            userEntity.setTotalPurchases(0);
            userEntity.setRegisteredOn(LocalDateTime.now());

            Cart cart = new Cart();
            cart.setUser(userEntity);

            userEntity.setCart(cart);

            UserRole user = new UserRole("ROLE_USER");
            user.setUser(userEntity);

            if (i == 1) {
                UserRole admin = new UserRole("ROLE_ADMIN");
                admin.setUser(userEntity);
                userEntity.setRoles(List.of(user, admin));
            } else {
                userEntity.setRoles(List.of(user));
            }

            users.add(userEntity);
        }

        this.userEntityRepository.saveAll(users);

    }
}
