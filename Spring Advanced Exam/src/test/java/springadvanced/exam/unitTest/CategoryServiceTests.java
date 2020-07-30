package springadvanced.exam.unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.domain.CategoryDto;
import springadvanced.exam.category.repository.CategoryRepository;
import springadvanced.exam.category.service.CategoryService;
import springadvanced.exam.product.domain.Product;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    private CategoryService categoryService;
    private Category category;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        this.categoryService = new CategoryService(categoryRepository, new ModelMapper());
        category = new Category();
        category.setId("1");
        category.setName("Konservi");
        category.setDescription("Konservi s bob i kufteta!");
        category.setProducts(List.of(new Product()));
    }

    @Test
    public void should_Return_All_Categories() {
        //arrange
        when(this.categoryRepository.findAll()).thenReturn(List.of(category));

        //act
        List<CategoryDto> result = this.categoryService.getAllCategories();

        //assert
        Assertions.assertEquals(1, result.size());
        CategoryDto expected = result.get(0);
        Assertions.assertEquals(expected.getId(), category.getId());
        Assertions.assertEquals(expected.getName(), category.getName());
        Assertions.assertEquals(expected.getDescription(), category.getDescription());
    }

    @Test
    public void should_Return_True_If_Category_Exists() {
        //arrange
        when(this.categoryRepository.findByName("Konservi")).thenReturn(Optional.of(category));

        //act
        boolean result = this.categoryService.categoryExists("Konservi");

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void should_Return_Category_By_Id() {
        //arrange
        when(this.categoryRepository.findById("1")).thenReturn(Optional.of(category));

        //act
        CategoryDto result = this.categoryService.findById("1");

        //assert
        Assertions.assertEquals(result.getId(), category.getId());
        Assertions.assertEquals(result.getName(), category.getName());
        Assertions.assertEquals(result.getDescription(), category.getDescription());
    }

    @Test
    public void should_Return_Category_By_Name() {
        //arrange
        when(this.categoryRepository.findByName("Konservi")).thenReturn(Optional.of(category));

        //act
        CategoryDto result = this.categoryService.findByName("Konservi");

        //assert
        Assertions.assertEquals(result.getId(), category.getId());
        Assertions.assertEquals(result.getName(), category.getName());
        Assertions.assertEquals(result.getDescription(), category.getDescription());
    }


}
