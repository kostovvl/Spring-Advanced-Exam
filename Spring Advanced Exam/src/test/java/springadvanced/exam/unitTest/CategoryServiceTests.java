package springadvanced.exam.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.repository.CategoryRepository;
import springadvanced.exam.category.service.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    private CategoryService categoryService;
    private Category category;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        this.categoryService = new CategoryService(categoryRepository, new ModelMapper());
        category.setId("1");
        category.setName("Konservi");
        category.setDescription("Konservi s bob i kufteta!");
    }

    @Test
    


}
