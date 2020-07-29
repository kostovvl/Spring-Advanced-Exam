package springadvanced.exam.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springadvanced.exam.cart.service.CartService;
import springadvanced.exam.product.service.ProductService;
import springadvanced.exam.user.service.UserEntityService;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserEntityService userEntityService;
  private final ProductService productService;
  private final CartService cartService;

    public HomeController(UserEntityService userEntityService,
                          ProductService productService, CartService cartService) {
        this.userEntityService = userEntityService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String index() {

        return "home/index";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {

        System.out.println();

        model.addAttribute("products", this.productService.findAllProductsHomepage(principal.getName()));
        model.addAttribute("purchases", cartService.getTotalProductsInCart(principal.getName()));

        return "home/home";
    }

    @GetMapping("/unauthorised")
    public String unauthorised () {
        return "error/unauthorised";
    }

    @GetMapping("/thank-you")
    public String thankYou() {
        return "home/thank-you";
    }

}
