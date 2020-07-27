package springadvanced.exam.utils.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeDefaultAdmins implements CommandLineRunner {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public InitializeDefaultAdmins(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if(this.userEntityRepository.count() != 0) {
            return;
        }

        List<UserEntity> admins = new ArrayList<>();

        for (int i = 1; i <=2 ; i++) {
            UserEntity admin = new UserEntity();
            admin.setUsername("Admin" + i);
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("Admin" + i + "@Admin.com");
            admin.setTotalPurchases(0);
            admin.setPersonalDiscount(0.0);

            Cart cart = new Cart();
            cart.setUser(admin);
            admin.setCart(cart);

            UserRole roleUser = new UserRole("ROLE_USER");
            roleUser.setUser(admin);

            UserRole roleAdmin = new UserRole("ROLE_ADMIN");
            roleAdmin.setUser(admin);

            UserRole roleRootAdmin = new UserRole("ROLE_ROOT_ADMIN");
            roleRootAdmin.setUser(admin);

            admin.setRoles(List.of(roleUser, roleAdmin, roleRootAdmin));

            admins.add(admin);
        }

        this.userEntityRepository.saveAll(admins);

    }
}
