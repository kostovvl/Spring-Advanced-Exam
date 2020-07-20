package springadvanced.exam.category.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.domain.CategoryDto;
import springadvanced.exam.category.domain.CategoryView;
import springadvanced.exam.category.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService  {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<CategoryDto> getAllCategoriesAdmin() {
        return this.categoryRepository.findAll().stream()
                .map(c -> this.mapper.map(c, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public boolean categoryExists(String name) {
        return this.categoryRepository.findByName(name).isPresent();
    }

    public void addCategory(CategoryDto categoryDto) {
        this.categoryRepository.saveAndFlush(this.mapper.map(categoryDto, Category.class));
    }

    public CategoryView findById(String id) {
        Category category = this.categoryRepository.findById(id).orElse(null);
        CategoryView categoryView = this.mapper.map(category, CategoryView.class);
        categoryView.setNumberOfProducts(category.getProducts().size());

        return categoryView;
    }

    public void deleteById(String id) {
        this.categoryRepository.deleteById(id);
    }
}
