package springadvanced.exam.category.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.category.domain.CategoryDto;
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
}
