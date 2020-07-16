package springadvanced.exam.category.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.category.repository.CategoryRepository;

@Service
public class CategoryService  {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }
}
