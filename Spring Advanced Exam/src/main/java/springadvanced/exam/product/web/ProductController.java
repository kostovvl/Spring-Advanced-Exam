package springadvanced.exam.product.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springadvanced.exam.category.domain.CategoryAddBinding;
import springadvanced.exam.category.service.CategoryService;
import springadvanced.exam.product.domain.ProductAddBinding;
import springadvanced.exam.product.domain.ProductAdminView;
import springadvanced.exam.product.domain.ProductUpdateBinding;
import springadvanced.exam.product.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper mapper;

    public ProductController(CategoryService categoryService, ProductService productService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.mapper = mapper;
    }



}
