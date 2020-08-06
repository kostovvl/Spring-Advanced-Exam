package springadvanced.exam.admin.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springadvanced.exam.admin.service.AdminService;
import springadvanced.exam.category.domain.CategoryAddBinding;
import springadvanced.exam.category.domain.CategoryDto;
import springadvanced.exam.category.domain.CategoryUpdateBinding;
import springadvanced.exam.category.domain.CategoryView;
import springadvanced.exam.category.service.CategoryService;
import springadvanced.exam.message.service.MessageService;
import springadvanced.exam.product.domain.ProductAddBinding;
import springadvanced.exam.product.domain.ProductAdminView;
import springadvanced.exam.product.domain.ProductUpdateBinding;
import springadvanced.exam.product.service.ProductService;
import springadvanced.exam.stats.service.InterceptionService;
import springadvanced.exam.user.domain.userEntity.UserEntityView;
import springadvanced.exam.user.service.UserEntityService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/admin", "/root-admin"})
public class AdminController {

    private final ModelMapper mapper;
    private final AdminService adminService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserEntityService userEntityService;
    private final MessageService messageService;
    private final InterceptionService interceptionService;

    public AdminController(ModelMapper mapper, AdminService adminService,
                           CategoryService categoryService, ProductService productService,
                           UserEntityService userEntityService, MessageService messageService,
                           InterceptionService interceptionService) {
        this.mapper = mapper;
        this.adminService = adminService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.userEntityService = userEntityService;
        this.messageService = messageService;
        this.interceptionService = interceptionService;
    }

    @GetMapping()
    public String adminPanel(Model model) {

        model.addAttribute("users", adminService.getAllUsers());
        model.addAttribute("products", adminService.getAllProducts());
        model.addAttribute("messages", adminService.getAllMessages());
        model.addAttribute("categories", adminService.getAllCategories());
        model.addAttribute("stats", this.interceptionService.getAllStats());

        System.out.println();

        return "admin/admin-panel";
    }

    // FROM HERE START METHODS FOR MANIPULATING CATEGORIES.

    @GetMapping("/categories/create")
    public String createCategory(Model model) {

        if (model.getAttribute("categoryCreate") == null) {
            model.addAttribute("categoryCreate", new CategoryAddBinding());
        }

        return "category/category-create";
    }

    @PostMapping("/categories/create")
    public String createCategoryConfirm(@Valid @ModelAttribute("categoryCreate")
                                        CategoryAddBinding categoryAddBinding, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryCreate", categoryAddBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryCreate", bindingResult);
            return "redirect:/admin/categories/create";
        }

        if (this.categoryService.categoryExists(categoryAddBinding.getName())) {
            redirectAttributes.addFlashAttribute("categoryCreate", categoryAddBinding);
            redirectAttributes.addFlashAttribute("categoryExists", true);
            return "redirect:/admin/categories/create";
        }

        this.categoryService.addCategory(this.mapper.map(categoryAddBinding, CategoryDto.class));

        return "redirect:/admin";
    }

    @GetMapping("/categories/details")
    public String detailsCategory(@RequestParam("id") String id, Model model) {

        model.addAttribute("category", this.mapper.map(this.categoryService.findById(id), CategoryView.class));

        return "category/category-details";
    }

    @GetMapping("/categories/delete")
    public String deleteCategory(@RequestParam("id") String id) {

        this.categoryService.deleteById(id);

        return "redirect:/admin";
    }
    @GetMapping("/categories/update")
    public String updateCategory(@RequestParam("id") String id, Model model) {

        if (model.getAttribute("categoryUpdate") == null) {

            model.addAttribute("categoryUpdate",
                    this.mapper.map(this.categoryService.findById(id), CategoryUpdateBinding.class));
        }

        return "category/category-update";
    }


    @PostMapping("/categories/update")
    public String updateCategoryConfirm(@Valid @ModelAttribute("categoryUpdate")
                                        CategoryUpdateBinding categoryUpdateBinding, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryUpdate", categoryUpdateBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryUpdate", bindingResult);

            return "redirect:/admin/categories/update?id=" + categoryUpdateBinding.getId();
        }



        this.categoryService.updateCategory(this.mapper.map(categoryUpdateBinding, CategoryDto.class));

        return "redirect:/admin";
    }

//                 FROM HERE STARTS THE METHODS FOR MANIPULATING PRODUCTS.

    @GetMapping("/products/add")
    public String addProduct(Model model) {

        //TODO try to make the input form go wide, so it all fits on one screen and there will be no need to scroll



        if (model.getAttribute("productAdd") == null) {
            model.addAttribute("productAdd", new ProductAddBinding());
        }

        List<CategoryAddBinding> categories = this.categoryService.getAllCategories()
                .stream().map(c -> this.mapper.map(c, CategoryAddBinding.class)).collect(Collectors.toList());
        model.addAttribute("categories", categories);

        return "product/add-product";
    }

    @PostMapping("/products/add")
    public String addProductConfirm(@Valid @ModelAttribute("productAdd")
                                     ProductAddBinding productAddBinding, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("productAdd", productAddBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAdd", bindingResult);
            return "redirect:/admin/products/add";
        }

        if (this.productService.productExists(productAddBinding.getTitle())) {
            redirectAttributes.addFlashAttribute("productAdd", productAddBinding);
            redirectAttributes.addFlashAttribute("productExists", true);
            return "redirect:/admin/products/add";
        }

        this.productService.addProduct(productAddBinding);

        return "redirect:/admin";
    }

    @GetMapping("/products/details")
    public String detailsProduct(@RequestParam("id") String id, Model model) {

        System.out.println();
        model.addAttribute("product",this.mapper.map(this.productService.findById(id), ProductAdminView.class));

        return "product/details-product-admin";
    }


    @GetMapping("/products/delete")
    public String deleteProduct(@RequestParam("id") String id) {

        this.productService.deleteProduct(id);

        return "redirect:/admin";
    }

    @GetMapping("/products/update")
    public String updateProduct(@RequestParam("id") String id, Model model) {



        if (model.getAttribute("productUpdate") == null) {
            model.addAttribute("productUpdate", this.mapper.map(this.productService.findById(id),
                    ProductUpdateBinding.class));
        }

        List<CategoryAddBinding> categories = this.categoryService.getAllCategories()
                .stream().map(c -> this.mapper.map(c, CategoryAddBinding.class)).collect(Collectors.toList());

        model.addAttribute("categories", categories);

        return "product/update-product";
    }

    @PostMapping("/products/update")
    public String updateProductConfirm(@Valid @ModelAttribute("productUpdate")
                                        ProductUpdateBinding productUpdateBinding, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("productUpdate", productUpdateBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productUpdate",
                    bindingResult);
            return "redirect:/admin/products/update?id=" + productUpdateBinding.getId();

        }

        System.out.println();

        this.productService.updateProduct(productUpdateBinding);

        return "redirect:/admin";
    }


    // FROM HERE START METHODS FOR MANIPULATING USERS

    @GetMapping("/details/user")
    public String detailsAdmin(@RequestParam("id") String id, Model model) {

        UserEntityView userEntityView = this.mapper.map(this.userEntityService.findById(id),
                UserEntityView.class);
        userEntityView.setAdmin(userEntityView.getRoles().size() > 1);

        System.out.println();

        model.addAttribute("user", userEntityView);

        return "user/details-admin";
    }

    @GetMapping("/downgrade")
    public String downgrade(@RequestParam("id") String id) {

        System.out.println();
        this.userEntityService.changeRole("user", id);

        return "redirect:/admin";
    }

    @GetMapping("/upgrade")
    public String upgrade(@RequestParam("id") String id) {

        this.userEntityService.changeRole("admin", id);

        return "redirect:/admin";
    }

    // FROM HERE ARE METHODS FOR MANIPULATING MESSAGES

    @GetMapping("/messages/details")
    public String messageDetails(@RequestParam("id") String id, Model model) {

        model.addAttribute("message", this.messageService.findById(id));
        this.messageService.markAsRead(id);

        return "message/message-details";
    }

    @GetMapping("/messages/delete")
    public String delete(@RequestParam("id") String id) {

        this.messageService.deleteById(id);

        return "redirect:/admin";
    }

}
