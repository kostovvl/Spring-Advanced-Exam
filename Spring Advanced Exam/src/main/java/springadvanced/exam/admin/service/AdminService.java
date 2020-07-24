package springadvanced.exam.admin.service;

import org.springframework.stereotype.Service;
import springadvanced.exam.category.domain.CategoryDto;
import springadvanced.exam.category.service.CategoryService;
import springadvanced.exam.message.domain.MessageDto;
import springadvanced.exam.message.service.MessageService;
import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.product.service.ProductService;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.user.service.UserEntityService;

import java.util.List;

@Service
public class AdminService {

    private final UserEntityService userEntityService;
    private final ProductService productService;
    private final MessageService messageService;
    private final CategoryService categoryService;

    public AdminService(UserEntityService userEntityService, ProductService productService,
                        MessageService messageService, CategoryService categoryService) {
        this.userEntityService = userEntityService;
        this.productService = productService;
        this.messageService = messageService;
        this.categoryService = categoryService;
    }

    public List<UserEntityDto> getAllUsers() {
        return userEntityService.getAllUsers();
    }

    public List<ProductDto> getAllProducts() {
        return this.productService.findAllProductsAdmin();
    }

    public List<MessageDto> getAllMessages() {
        return this.messageService.getAllMessages();
    }

    public List<CategoryDto> getAllCategories() {
        return this.categoryService.getAllCategories();
    }
}
