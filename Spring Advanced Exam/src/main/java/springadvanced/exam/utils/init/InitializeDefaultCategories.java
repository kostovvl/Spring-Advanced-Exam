package springadvanced.exam.utils.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeDefaultCategories implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public InitializeDefaultCategories(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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

    }
}
