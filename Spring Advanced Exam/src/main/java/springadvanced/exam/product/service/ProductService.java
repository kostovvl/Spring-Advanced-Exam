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

                    BigDecimal discount = p.getPrice().multiply(new BigDecimal(totalDiscount))
                            .divide(new BigDecimal("100"));

                    BigDecimal discountedPrice = p.getPrice().subtract(discount);
                   productUserView.setDiscountedPrice(discountedPrice);

                   return productUserView;
                }).collect(Collectors.toList());

    }

    public List<ProductDto> findAllProductsAdmin() {
        return this.productRepository.findAll().stream()
                .map(p -> this.mapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto findById(String id) {
        return this.productRepository.findById(id).
                map(p -> this.mapper.map(p, ProductDto.class)).orElse(null);
    }

    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }

    public void updateProduct(ProductUpdateBinding productUpdateBinding) {
        Product existing = this.productRepository.getOne(productUpdateBinding.getId());

        existing.setTitle(productUpdateBinding.getTitle());
        existing.setDescription(productUpdateBinding.getDescription());
        existing.setCategory(this.mapper.map(
                this.categoryService.findByName(productUpdateBinding.getCategory()),
                        Category.class));
        existing.setPictureUrl(productUpdateBinding.getPictureUrl());
        existing.setPrice(productUpdateBinding.getPrice());
        existing.setMaxDiscountPercent(productUpdateBinding.getMaxDiscountPercent());
        existing.setAdminDiscount(productUpdateBinding.getAdminDiscountPercent());
        existing.setLastUpdated(LocalDateTime.now());

        this.productRepository.save(existing);

    }

    public void increaseProductSales(String id) {

        Product product = this.productRepository.getOne(id);
        product.setNumberOfPurchases(product.getNumberOfPurchases() + 1);

        this.productRepository.save(product);
    }

    public ProductDto FindByTitle(String title) {

        return this.mapper.map(this.productRepository.findByTitle(title).orElse(null), ProductDto.class);
    }



}
