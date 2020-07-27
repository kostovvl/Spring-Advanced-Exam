package springadvanced.exam.utils.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.repository.CategoryRepository;
import springadvanced.exam.product.domain.Product;
import springadvanced.exam.product.repository.ProductRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class InitializeDefaultProducts implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public InitializeDefaultProducts(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count() != 0) {
            return;
        }

        List<Category> categories = new ArrayList<>();

        for (int i = 1; i <= 5 ; i++) {
            Category category = new Category();
            category.setName("TestCategory" + i);
            category.setDescription("Test Description of category" + 1);
            categories.add(category);
        }

        this.categoryRepository.saveAll(categories);

        if (this.productRepository.count() != 0) {
            return;
        }

        List<Product> products = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <=10 ; i++) {
            Product product = new Product();
            product.setTitle("TestProduct" + i);
            product.setDescription("Description of TestProduct" + i);
            String categoryName = "TestCategory" + (random.nextInt(5) + 1);
            Category category = this.categoryRepository.findByName(categoryName)
                    .orElse(null);
            product.setCategory(category);
            product.setPictureUrl(String.format("/img/image%s.jpg", (random.nextInt(5) + 1)));
            product.setAddedOn(LocalDateTime.now());
            product.setLastUpdated(LocalDateTime.now());
            product.setPrice(new BigDecimal(random.nextInt(200)));
            product.setMaxDiscountPercent(random.nextInt(30));
            product.setAdminDiscount(random.nextInt(15));
            product.setNumberOfPurchases(random.nextInt(1000));

            products.add(product);
        }

        this.productRepository.saveAll(products);

    }
}
