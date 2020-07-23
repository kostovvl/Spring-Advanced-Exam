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

    @GetMapping("/add")
    public String add(Model model) {

        //TODO try to make the input form go wide, so it all fits on one screen and the will be no need to scroll

        List<CategoryAddBinding> categories = this.categoryService.getAllCategories()
                .stream().map(c -> this.mapper.map(c, CategoryAddBinding.class)).collect(Collectors.toList());

        if (model.getAttribute("productAdd") == null) {
            model.addAttribute("productAdd", new ProductAddBinding());
        }
        model.addAttribute("categories", categories); //Todo check why the category resets

        return "product/add-product";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("productAdd")
                             ProductAddBinding productAddBinding, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("productAdd", productAddBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAdd", bindingResult);
            return "redirect:/products/add";
        }

        if (this.productService.productExists(productAddBinding.getTitle())) {
            redirectAttributes.addFlashAttribute("productAdd", productAddBinding);
            redirectAttributes.addFlashAttribute("productExists", true);
            return "redirect:/products/add";
        }

        this.productService.addProduct(productAddBinding);

        return "redirect:/admin";
    }

    @GetMapping("/details")
    public String derails(@RequestParam("id") String id, Model model) {

        System.out.println();
        model.addAttribute("product", this.productService.findById(id));

        return "product/details-product";
    }



}
