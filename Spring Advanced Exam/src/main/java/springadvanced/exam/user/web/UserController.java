package springadvanced.exam.user.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springadvanced.exam.config.SecurityConfig;
import springadvanced.exam.user.domain.userEntity.UserEntityRegisterBinding;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.user.domain.userEntity.UserEntityUpdateBinding;
import springadvanced.exam.user.domain.userEntity.UserEntityView;
import springadvanced.exam.user.service.UserDetailsServiceImpl;
import springadvanced.exam.user.service.UserEntityService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserEntityService userEntityService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserController(UserEntityService userEntityService,
                           PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userEntityService = userEntityService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @GetMapping("/register")
    public String register(Model model) {

        if (model.getAttribute("registerUser") == null) {
            model.addAttribute("registerUser", new UserEntityRegisterBinding());
        }

        return "user/register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("registerUser")
                                              UserEntityRegisterBinding userEntityRegisterBinding, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityRegisterBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUser", bindingResult);
            return "redirect:/users/register";
        }

        if (!userEntityRegisterBinding.getPassword().equals(userEntityRegisterBinding.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityRegisterBinding);
            redirectAttributes.addFlashAttribute("confirmPassword", true);
            return "redirect:/users/register";
        }

        if (this.userEntityService.userExists(userEntityRegisterBinding.getUsername())) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityRegisterBinding);
            redirectAttributes.addFlashAttribute("userExists", true);
            return "redirect:/users/register";
        }

        if (this.userEntityService.emailExists(userEntityRegisterBinding.getEmail())) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityRegisterBinding);
            redirectAttributes.addFlashAttribute("emailExists", true);
            return "redirect:/users/register";
        }

        this.userEntityService.registerUser(this.mapper.map(userEntityRegisterBinding, UserEntityDto.class));

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
        UserEntityView userEntityView = this.mapper.map(this.userEntityService.findByUsername(principal.getName()),
                UserEntityView.class);

        model.addAttribute("user", userEntityView);
        return "user/details-user";

        //todo add roles and cart to the view
    }

    @GetMapping("/update")
    public String update(Model model, Principal principal) {

        //todo make it secure


        if (model.getAttribute("updateUser") == null) {

        UserEntityUpdateBinding userEntityUpdateBinding = this.mapper.map(
                this.userEntityService.findByUsername(principal.getName()), UserEntityUpdateBinding.class);
        model.addAttribute("updateUser", userEntityUpdateBinding);
        }

        return "user/update-user";
    }

    @PostMapping("/update")
    public String updateConfirm(@Valid @ModelAttribute("updateUser")
                                UserEntityUpdateBinding userEntityUpdateBinding, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal, HttpSession session) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateUser", userEntityUpdateBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateUser", bindingResult);
            return "redirect:/users/update";
        }

        if (this.userEntityService.userExists(userEntityUpdateBinding.getUsername())) {
            redirectAttributes.addFlashAttribute("updateUser", userEntityUpdateBinding);
            redirectAttributes.addFlashAttribute("userExists", true);
            return "redirect:/users/update";
        }

        if (this.userEntityService.emailExists(userEntityUpdateBinding.getEmail())) {
            redirectAttributes.addFlashAttribute("updateUser", userEntityUpdateBinding);
            redirectAttributes.addFlashAttribute("emailExists", true);
            return "redirect:/users/update";
        }



        if (!this.passwordEncoder.matches(userEntityUpdateBinding.getOldPassword(),
                this.userEntityService.getUserOldPassword(principal.getName()))) {
            redirectAttributes.addFlashAttribute("updateUser", userEntityUpdateBinding);
            redirectAttributes.addFlashAttribute("oldPasswordNoMatch", true);
            return "redirect:/users/update";
        }

        if (!userEntityUpdateBinding.getNewPassword().equals(userEntityUpdateBinding.getConfirmNewPassword())) {
            redirectAttributes.addFlashAttribute("registerUser", userEntityUpdateBinding);
            redirectAttributes.addFlashAttribute("confirmNewPassword", true);
            return "redirect:/users/update";
        }


        this.userEntityService.updateUser(userEntityUpdateBinding);
        session.invalidate();

        return "redirect:/users/login?updated=new_credentials";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id, HttpSession session) {

        this.userEntityService.deleteUser(id);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/details/admin")
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
}
