package springadvanced.exam.integrationTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;
import java.time.LocalDateTime;
import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserEntityRepository userEntityRepository;


    @BeforeEach
    public void setUp() {

        userEntityRepository.deleteAll();

        UserEntity userEntity = new UserEntity();
        UserRole userRoleUser = new UserRole("ROLE_USER");
        userRoleUser.setUser(userEntity);

        UserRole userRoleAdmin = new UserRole("ROLE_ADMIN");
        userRoleAdmin.setUser(userEntity);

        userEntity.setUsername("Pesho");
        userEntity.setPassword("123");
        userEntity.setEmail("pesho@pesho.com");
        userEntity.setPersonalDiscount(0.0);
        userEntity.setRoles(List.of(userRoleUser, userRoleAdmin));
        userEntity.setCart(new Cart());
        userEntity.setTotalPurchases(0);
        userEntity.setRegisteredOn(LocalDateTime.now());

        this.userEntityRepository.saveAndFlush(userEntity);

    }





    @Test
    public void should_Return_Register_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("registerUser"));
    }



    @Test
    public void should_Return_Login_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
    }

    @Test
    public void should_Return_User_Details_Page() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/details").
                        with(user("Pesho").password("123").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("user/details-user"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void should_Return_User_Update_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/update").
                        with(user("Pesho").password("123").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update-user"))
                .andExpect(model().attributeExists("updateUser"));
    }

    @Test
    public void should_Delete_User() throws Exception {
        //arrange
        String id = this.userEntityRepository.findByUsername("Pesho").orElse(null).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/delete").param("id", id).
                        with(user("Pesho").password("123").roles("USER","ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Assertions.assertEquals(0, this.userEntityRepository.count());
    }


}
