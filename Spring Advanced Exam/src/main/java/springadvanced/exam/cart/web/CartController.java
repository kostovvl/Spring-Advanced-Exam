package springadvanced.exam.cart.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springadvanced.exam.cart.service.CartService;
import springadvanced.exam.user.service.UserEntityService;
import java.security.Principal;


@Controller
@RequestMapping("/cart")
public class CartController {

    private final UserEntityService userEntityService;
    private final CartService cartService;

    public CartController(UserEntityService userEntityService, CartService cartService) {

        this.userEntityService = userEntityService;
        this.cartService = cartService;
    }

    @GetMapping("/add")
    public String add(@RequestParam("id") String id, Principal principal) {

        String username = this.userEntityService.findByUsername(principal.getName()).getUsername();
        this.cartService.addToCart(id, username);

        return "redirect:/home";
    }

    @GetMapping("/all")
    public String all(Model model, Principal principal) {
        String cartId = this.userEntityService.findByUsername(principal.getName()).getCart().getId();

        model.addAttribute("products", this.cartService.getAllProducts(cartId));
        model.addAttribute("price", this.cartService.totalCartPrice(cartId));

        return "cart/cart";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("title") String title, Principal principal) {
        String cartId = this.userEntityService.findByUsername(principal.getName()).getCart().getId();
        this.cartService.removeProduct(cartId, title);

        return "redirect:/cart/all";
    }

    @GetMapping("/buy/all")
    public String buyAll(Principal principal) {
        String cartId = this.userEntityService.findByUsername(principal.getName()).getCart().getId();

        this.cartService.buyAllProducts(cartId);

        return "redirect:/home";
    }
}
