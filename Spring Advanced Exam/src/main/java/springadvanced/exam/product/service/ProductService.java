package springadvanced.exam.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.service.CategoryService;
import springadvanced.exam.product.domain.*;
import springadvanced.exam.product.repository.ProductRepository;
import springadvanced.exam.user.service.UserEntityService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserEntityService userEntityService;
    private final ModelMapper mapper;

    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService, UserEntityService userEntityService, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userEntityService = userEntityService;
        this.mapper = mapper;
    }

    @Transactional
    public void addProduct(ProductAddBinding productAddBinding) {
        Category category = this.mapper.map(this.categoryService.findByName(productAddBinding.getCategory()), Category.class);
        Product product = this.mapper.map(productAddBinding, Product.class);
        product.setCategory(category);
        product.setAddedOn(LocalDateTime.now());
        product.setLastUpdated(LocalDateTime.now());
        product.setNumberOfPurchases(0);

        this.productRepository.saveAndFlush(product);
    }

    public boolean productExists(String title) {
        return this.productRepository.findByTitle(title).isPresent();
    }

    public List<ProductUserView> findAllProductsHomepage (String username) {

        Double loggedUserDiscount = this.userEntityService.findByUsername(username).getPersonalDiscount();

        return this.productRepository.findAll().stream()
                .map(p -> {
                   ProductUserView productUserView = this.mapper.map(p, ProductUserView.class);
                   double totalDiscount = p.getAdminDiscount() + loggedUserDiscount;
                   if (totalDiscount > p.getMaxDiscountPercent()) {
                       totalDiscount = p.getMaxDiscountPercent();
                   }
                    BigDecimal discountedPrice = p.getPrice().multiply(new BigDecimal(totalDiscount/100));
                   productUserView.setDiscountedPrice(discountedPrice);

                   return productUserView;
                }).collect(Collectors.toList());

    }

    public List<ProductDto> findAllProductsAdmin() {
        return this.productRepository.findAll().stream()
                .map(p -> this.mapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductAdminView findById(String id) {
        return this.productRepository.findById(id).
                map(p -> this.mapper.map(p, ProductAdminView.class)).orElse(null);
    }
}
