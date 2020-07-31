package springadvanced.exam.integrationTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.cart.repository.CartRepository;
import springadvanced.exam.cart.service.CartService;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.repository.CategoryRepository;
import springadvanced.exam.product.domain.Product;
import springadvanced.exam.product.repository.ProductRepository;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        this.cartRepository.deleteAll();
        this.userEntityRepository.deleteAll();
        this.productRepository.deleteAll();
        this.cartRepository.deleteAll();
        this.categoryRepository.deleteAll();

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

        Cart cart = new Cart();
        cart.setUser(userEntity);
        cart.setProducts(new HashMap<String, Integer>());

        userEntity.setUsername("Pesho");
        userEntity.setPassword("123");
        userEntity.setEmail("pesho@pesho.com");
        userEntity.setPersonalDiscount(0.0);
        userEntity.setRoles(List.of(userRoleUser, userRoleAdmin, userRoleRootAdmin));
        userEntity.setCart(cart);
        userEntity.setTotalPurchases(0);
        userEntity.setRegisteredOn(LocalDateTime.now());

        this.userEntityRepository.saveAndFlush(userEntity);
    }

    @AfterEach
    public void setDown() {
        this.cartRepository.deleteAll();
        this.userEntityRepository.deleteAll();
        this.productRepository.deleteAll();
        this.cartRepository.deleteAll();
        this.categoryRepository.deleteAll();
    }

    @Test
    public void should_Add_Product_To_Cart() throws Exception {
        //arrange
        String productId = this.productRepository.findByTitle("Product").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/add").param("id", productId).
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")));

        //assert
        Assertions.assertEquals(1, this.cartService.getTotalProductsInCart("Pesho"));

    }

    @Test
    public void should_Return_Cart_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/all").
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("price"));
    }

    @Test
    public void should_Remove_Product_From_Cart() throws Exception {
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/remove").param("title", "Product").
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().is3xxRedirection());

        //assert
        Assertions.assertEquals(0, this.cartService.getTotalProductsInCart("Pesho"));
    }

    @Test
    public void should_Remove_All_Products_From_Cart() throws Exception {
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/buy/all").
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().is3xxRedirection());

        //assert
        Assertions.assertEquals(0, this.cartService.getTotalProductsInCart("Pesho"));
    }


}
