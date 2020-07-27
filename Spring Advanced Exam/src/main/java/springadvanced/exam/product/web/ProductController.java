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
import springadvanced.exam.product.domain.ProductUserView;
import springadvanced.exam.product.service.ProductService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users/products")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper mapper;

    public ProductController(CategoryService categoryService, ProductService productService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("/details")
    public String detailsUser(@RequestParam("id") String id, Model model, Principal principal) {

        model.addAttribute("product", this.mapper.map(this.productService.findByIdView(id, principal.getName()),
                ProductUserView.class));

        return "product/details-product-user";
    }

}
