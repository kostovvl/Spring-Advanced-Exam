package springadvanced.exam.integrationTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.repository.CategoryRepository;
import springadvanced.exam.product.domain.Product;
import springadvanced.exam.product.repository.ProductRepository;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;
import springadvanced.exam.user.service.UserEntityService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @BeforeEach
    public void setUp() {
        this.productRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.userEntityRepository.deleteAll();

        Product product = new Product();
        Category category = new Category();
        category.setName("Test category");
        category.setProducts(new ArrayList<>());
        category.setDescription("Description of test category");
        this.categoryRepository.saveAndFlush(category);

        product.setTitle("Product");
        product.setDescription("Product description");
        product.setCategory(category);
        product.setAddedOn(LocalDateTime.now());
        product.setLastUpdated(LocalDateTime.now());
        product.setPictureUrl("pictureUrl.com");
        product.setPrice(new BigDecimal("10"));
        product.setMaxDiscountPercent(15);
        product.setAdminDiscount(7);
        product.setNumberOfPurchases(150);

        this.productRepository.saveAndFlush(product);

        UserEntity userEntity = new UserEntity();
        UserRole userRoleUser = new UserRole("ROLE_USER");
        userRoleUser.setUser(userEntity);

        UserRole userRoleAdmin = new UserRole("ROLE_ADMIN");
        userRoleAdmin.setUser(userEntity);

        UserRole userRoleRootAdmin = new UserRole("ROLE_ROOT_ADMIN");
        userRoleAdmin.setUser(userEntity);

        userEntity.setUsername("Pesho");
        userEntity.setPassword("123");
        userEntity.setEmail("pesho@pesho.com");
        userEntity.setPersonalDiscount(0.0);
        userEntity.setRoles(List.of(userRoleUser, userRoleAdmin, userRoleRootAdmin));
        userEntity.setCart(new Cart());
        userEntity.setTotalPurchases(0);
        userEntity.setRegisteredOn(LocalDateTime.now());

        this.userEntityRepository.saveAndFlush(userEntity);
    }

    @AfterEach
    public void setDown() {
        this.productRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.userEntityRepository.deleteAll();
    }

    @Test
    public void should_Return_Product_Add_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/products/add").
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("product/add-product"))
                .andExpect(model().attributeExists("productAdd"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void should_Add_New_Product() throws Exception {

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/products/add").
                        with(csrf()).
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN"))
                .param("title", "Chorap")
                .param("description", "Description of Chorap")
                .param("category", "Test category")
                .param("pictureUrl", "http://www.google.com/search?")
                .param("price", "10")
                .param("maxDiscountPercent", "10")
                .param("adminDiscountPercent", "5"))
                .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/admin"));

        //assert
        Assertions.assertEquals(2, this.productRepository.count());
    }

    @Test
    public void should_Not_Add_New_Product_If_Wrong_Parameters() throws Exception {

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/products/add").
                        with(csrf()).
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN"))
                .param("title", "Chorap")
                .param("description", "short")
                .param("category", "Test category")
                .param("pictureUrl", "http://www.google.com/search?")
                .param("price", "10")
                .param("maxDiscountPercent", "10")
                .param("adminDiscountPercent", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products/add"));

        //assert
        Assertions.assertEquals(1, this.productRepository.count());
    }

    @Test
    public void should_Return_Product_Admin_Details_Page() throws Exception {
        //arrange
        String productId = this.productRepository.findByTitle("Product").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/products/details").param("id", productId).
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("product/details-product-admin"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void should_Return_Product_User_Details_Page() throws Exception {
        //arrange
        String productId = this.productRepository.findByTitle("Product").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/products/details").param("id", productId).
                        with(user("Pesho").password("123").roles("USER", "ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("product/details-product-user"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void should_Delete_Product() throws Exception {
        //arrange
        String productId = this.productRepository.findByTitle("Product").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/products/delete").param("id", productId)
                .with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        //assert
        Assertions.assertEquals(0, this.productRepository.count());
    }

    @Test
    public void should_Return_Product_Update_Page() throws Exception {
        //arrange
        String productId = this.productRepository.findByTitle("Product").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/products/update").param("id", productId).
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("product/update-product"))
                .andExpect(model().attributeExists("productUpdate"))
                .andExpect(model().attributeExists("categories"));
    }


}
