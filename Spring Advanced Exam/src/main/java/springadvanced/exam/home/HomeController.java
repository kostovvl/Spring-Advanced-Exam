package springadvanced.exam.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springadvanced.exam.product.service.ProductService;

import java.security.Principal;

@Controller
public class HomeController {

  private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {

        model.addAttribute("products", this.productService.findAllProductsHomepage(principal.getName()));

        return "home/home";
    }

}
