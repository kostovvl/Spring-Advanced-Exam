package springadvanced.exam.cart.web;

import org.springframework.stereotype.Controller;
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

}
