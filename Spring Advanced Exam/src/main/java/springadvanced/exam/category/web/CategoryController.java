package springadvanced.exam.category.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springadvanced.exam.category.domain.CategoryAddBinding;
import springadvanced.exam.category.domain.CategoryDto;
import springadvanced.exam.category.service.CategoryService;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper mapper;

    public CategoryController(CategoryService categoryService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping("/create")
    public String create(Model model) {

        if (model.getAttribute("categoryCreate") == null) {
            model.addAttribute("categoryCreate", new CategoryAddBinding());
        }

        return "category/category-create";
    }

    @PostMapping("/create")
    public String createConfirm(@Valid @ModelAttribute("categoryCreate")
                                CategoryAddBinding categoryAddBinding, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryCreate", categoryAddBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryCreate", bindingResult);
            return "redirect:/categories/create";
        }

        if (this.categoryService.categoryExists(categoryAddBinding.getName())) {
            redirectAttributes.addFlashAttribute("categoryCreate", categoryAddBinding);
            redirectAttributes.addFlashAttribute("categoryExists", true);
            return "redirect:/categories/create";
        }

        this.categoryService.addCategory(this.mapper.map(categoryAddBinding, CategoryDto.class));

        return "redirect:/admin";
    }

    @GetMapping("/details")
    public String details(@RequestParam("id") String id, Model model) {

        model.addAttribute("category", this.categoryService.findById(id));

        return "category/category-details";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id) {

        this.categoryService.deleteById(id);

        return "redirect:/admin";
    }

}
