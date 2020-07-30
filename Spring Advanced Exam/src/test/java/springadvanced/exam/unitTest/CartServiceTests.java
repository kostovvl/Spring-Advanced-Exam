package springadvanced.exam.unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.cart.repository.CartRepository;
import springadvanced.exam.cart.service.CartService;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.repository.CategoryRepository;
import springadvanced.exam.category.service.CategoryService;
import springadvanced.exam.product.domain.Product;
import springadvanced.exam.product.domain.ProductCartView;
import springadvanced.exam.product.repository.ProductRepository;
import springadvanced.exam.product.service.ProductService;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.user.repository.UserEntityRepository;
import springadvanced.exam.user.repository.UserRoleRepository;
import springadvanced.exam.user.service.UserEntityService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

    private ProductService productService;
    private UserEntityService userEntityService;
    private CartService cartService;
    private CategoryService categoryService;

    private Category category;
    private Product product;
    private UserRole userRole;
    private UserEntity userEntity;
    private Cart cart;

    @Mock
    CartRepository cartRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UserEntityRepository userEntityRepository;
    @Mock
    UserRoleRepository userRoleRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        //initializeServices
        this.userEntityService = new UserEntityService(userEntityRepository, userRoleRepository,
                new ModelMapper(), passwordEncoder);
        this.categoryService = new CategoryService(categoryRepository, new ModelMapper());
        this.productService = new ProductService(productRepository, this.categoryService,
                this.userEntityService, new ModelMapper());
        this.cartService = new CartService(productService, userEntityService,
                cartRepository, new ModelMapper());

        //initializeEntities
        category = new Category();
        product = new Product();
        cart = new Cart();
        userRole = new UserRole("ROLE_USER");

        //setUpCategory

        category.setId("1");
        category.setName("Konservi");
        category.setDescription("Konservi s bob i kufteta!");
        category.setProducts(List.of(product));
        userEntity = new UserEntity();

        //setUpProduct
        product.setId("1");
        product.setTitle("Product");
        product.setDescription("Product description");
        product.setCategory(category);
        product.setAddedOn(LocalDateTime.now());
        product.setLastUpdated(LocalDateTime.now());
        product.setPictureUrl("pictureUrl.com");
        product.setPrice(new BigDecimal("10"));
        product.setMaxDiscountPercent(15);
        product.setAdminDiscount(0);
        product.setNumberOfPurchases(150);

        //setUpCart
        cart.setId("1");
        cart.setProducts(Map.of(product.getTitle(), 1));

        //setUpUserRole
        userRole.setId("1");
        userRole.setUser(userEntity);

        //setUpUserEntity
        userEntity.setId("1");
        userEntity.setUsername("Pesho");
        userEntity.setPassword("123");
        userEntity.setEmail("pesho@pesho.com");
        userEntity.setPersonalDiscount(0.0);
        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);
        userEntity.setRoles(roles);
        userEntity.setCart(cart);
        userEntity.setTotalPurchases(0);
        userEntity.setRegisteredOn(LocalDateTime.now());

    }


    @Test
    public void should_Return_Count_Of_Products_In_Cart() {
        //arrange
        when(this.userEntityRepository.findByUsername("Pesho")).thenReturn(Optional.of(userEntity));

        //act
        int result = this.cartService.getTotalProductsInCart("Pesho");

        //assert
        Assertions.assertEquals(1, result);
    }

    @Test
    public void should_Return_AllProducts_In_Cart() {
        //arrange
        when(this.cartRepository.findById("1")).thenReturn(Optional.of(cart));
        when(this.productRepository.findByTitle("Product")).thenReturn(Optional.of(product));

        //act
        List<ProductCartView> result = this.cartService.getAllProducts("1", 0.0);

        //assert
        Assertions.assertEquals(1, result.size());
        ProductCartView expected = result.get(0);
        Assertions.assertEquals(expected.getTitle(), product.getTitle());
    }

    @Test
    public void should_Return_The_Total_Price_Of_All_Products() {

        //arrange
        when(this.cartRepository.findById("1")).thenReturn(Optional.of(cart));
        when(this.productRepository.findByTitle("Product")).thenReturn(Optional.of(product));

        //act
        BigDecimal result = this.cartService.totalCartPrice("1", 0.0);

        //assert
        Assertions.assertEquals(result, product.getPrice());

    }


}
