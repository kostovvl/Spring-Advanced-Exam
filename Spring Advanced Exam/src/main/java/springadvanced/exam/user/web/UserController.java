package springadvanced.exam.user.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springadvanced.exam.user.domain.userEntity.UserEntityBinding;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.user.service.UserEntityService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserEntityService userEntityService;
    private final ModelMapper mapper;

    public UserController(UserEntityService userEntityService, ModelMapper mapper) {
        this.userEntityService = userEntityService;
        this.mapper = mapper;
    }

    @GetMapping("/register")
    public String register(Model model) {

        if (model.getAttribute("registerUser") == null) {
            model.addAttribute("registerUser", new UserEntityBinding());
        }

        return "user/register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("registerUser")
                                          UserEntityBinding userEntityBinding, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUser", bindingResult);
            return "redirect:/users/register";
        }

        if (!userEntityBinding.getPassword().equals(userEntityBinding.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityBinding);
            redirectAttributes.addFlashAttribute("confirmPassword", true);
            return "redirect:/users/register";
        }

        if (this.userEntityService.userExists(userEntityBinding.getUsername())) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityBinding);
            redirectAttributes.addFlashAttribute("userExists", true);
            return "redirect:/users/register";
        }

        if (this.userEntityService.emailExists(userEntityBinding.getEmail())) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityBinding);
            redirectAttributes.addFlashAttribute("emailExists", true);
            return "redirect:/users/register";
        }

        this.userEntityService.registerUser(this.mapper.map(userEntityBinding, UserEntityDto.class));

        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginFail() {
        return "user/login";
    }

    @GetMapping("/details")
    public String details(Model model, Principal principal) {
        model.addAttribute("user", principal);
        return "user/details-user";
    }

}
