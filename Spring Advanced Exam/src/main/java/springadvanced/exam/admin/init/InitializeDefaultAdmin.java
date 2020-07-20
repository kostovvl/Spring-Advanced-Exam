package springadvanced.exam.admin.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InitializeDefaultAdmin implements CommandLineRunner {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public InitializeDefaultAdmin(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if(this.userEntityRepository.count() != 0) {
            return;
        }

        UserEntity admin = new UserEntity();
        admin.setUsername("Admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("Admin@Admin.com");
        admin.setTotalPurchases(0);

        Cart cart = new Cart();
        cart.setTotalPrice(new BigDecimal("0"));
        cart.setUser(admin);

        UserRole roleAdmin = new UserRole("ROLE_ADMIN");
        roleAdmin.setUser(admin);

        UserRole roleRootAdmin = new UserRole("ROLE_ROOT_ADMIN");
        roleRootAdmin.setUser(admin);

        admin.setPersonalDiscount(0.0);
        admin.setCart(cart);

        admin.setRoles(List.of(roleAdmin, roleRootAdmin));

        this.userEntityRepository.saveAndFlush(admin);

    }
}
