package springadvanced.exam.unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.service.CategoryService;
import springadvanced.exam.product.domain.Product;
import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.product.domain.ProductUserView;
import springadvanced.exam.product.repository.ProductRepository;
import springadvanced.exam.product.service.ProductService;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.user.repository.UserEntityRepository;
import springadvanced.exam.user.repository.UserRoleRepository;
import springadvanced.exam.user.service.UserEntityService;
import springadvanced.exam.utils.event.EventPublisher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    private ProductService productService;
    private UserEntityService userEntityService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryService categoryService;

    @Mock
    UserEntityRepository userEntityRepository;

    @Mock
    UserRoleRepository userRoleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private Product product;
    private UserEntity userEntity;
    private EventPublisher eventPublisher;

    @BeforeEach
    public void setUp() {

        userEntityService = new UserEntityService(userEntityRepository, userRoleRepository,
                new ModelMapper(), passwordEncoder, eventPublisher);

        productService = new ProductService(productRepository, categoryService,
                userEntityService, new ModelMapper());


        product = new Product();
        product.setId("1");
        product.setTitle("Product");
        product.setDescription("Product description");
        Category category = new Category();
        category.setName("Test category");
        product.setCategory(category);
        product.setAddedOn(LocalDateTime.now());
        product.setLastUpdated(LocalDateTime.now());
        product.setPictureUrl("pictureUrl.com");
        product.setPrice(new BigDecimal("10"));
        product.setMaxDiscountPercent(15);
        product.setAdminDiscount(7);
        product.setNumberOfPurchases(150);

        userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setPersonalDiscount(0.0);

    }


    @Test
    public void should_Return_True_If_Product_With_Such_Title_Exists() {
        //arrange
        when(this.productRepository.findByTitle("Product")).thenReturn(Optional.of(product));

        //act
        boolean result = this.productService.productExists("Product");

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void should_Return_List_Of_ProductUserViews() {
        //arrange
        when(this.productRepository.findAll()).thenReturn(List.of(product));
        when(this.userEntityRepository.findByUsername("Ignat")).thenReturn(Optional.of(userEntity));

        //act
        List<ProductUserView> result = this.productService.findAllProductsHomepage("Ignat");

        //assert
        Assertions.assertEquals(1, result.size());
        ProductUserView expected = result.get(0);

        Assertions.assertEquals(expected.getId(), product.getId());
        Assertions.assertEquals(expected.getTitle(), product.getTitle());
        Assertions.assertEquals(expected.getDescription(), product.getDescription());
    }

    @Test
    public void should_Return_List_Of_ProductDtos() {
        //arrange
        when(this.productRepository.findAll()).thenReturn(List.of(product));

        //act
        List<ProductDto> result = this.productService.findAllProductsAdmin();

        //assert
        Assertions.assertEquals(1, result.size());
        ProductDto expected = result.get(0);

        Assertions.assertEquals(expected.getId(), product.getId());
        Assertions.assertEquals(expected.getTitle(), product.getTitle());
        Assertions.assertEquals(expected.getDescription(), product.getDescription());
    }

    @Test
    public void should_Find_Product_By_Id() {
        //arrange
        when(this.productRepository.findById("1")).thenReturn(Optional.of(product));

        //act
        ProductDto result = this.productService.findById("1");

        //assert
        Assertions.assertEquals(result.getId(), product.getId());
        Assertions.assertEquals(result.getTitle(), product.getTitle());
        Assertions.assertEquals(result.getDescription(), product.getDescription());
    }


    @Test
    public void should_Find_Product_View_By_Id_And_Logged_User_Username() {
        //arrange
        when(this.productRepository.findById("1")).thenReturn(Optional.of(product));
        when(this.userEntityRepository.findByUsername("Ignat")).thenReturn(Optional.of(userEntity));

        //act
        ProductUserView result = this.productService.findByIdView("1", "Ignat");

        //assert
        Assertions.assertEquals(result.getId(), product.getId());
        Assertions.assertEquals(result.getTitle(), product.getTitle());
        Assertions.assertEquals(result.getDescription(), product.getDescription());
    }

    @Test
    public void should_Return_Product_By_Title() {
        //arrange
        when(this.productRepository.findByTitle("Product")).thenReturn(Optional.of(product));

        //act
        ProductDto result = this.productService.findByTitle("Product");

        //assert
        Assertions.assertEquals(result.getId(), product.getId());
        Assertions.assertEquals(result.getTitle(), product.getTitle());
        Assertions.assertEquals(result.getDescription(), product.getDescription());
    }

}
