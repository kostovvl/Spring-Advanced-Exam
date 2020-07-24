package springadvanced.exam.message.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springadvanced.exam.message.domain.MessageBinding;
import springadvanced.exam.message.service.MessageService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/create")
    public String create(Model model) {

        if (model.getAttribute("newMessage") == null) {
            model.addAttribute("newMessage", new MessageBinding());
        }

        return "message/message-create";
    }

    @PostMapping("/create")
    public String createConfirm(@Valid @ModelAttribute("newMessage")
                                            MessageBinding messageBinding, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newMessage", messageBinding);
            return  principal == null ? "redirect:/" : "redirect:/home";
        }

        this.messageService.addMessage(messageBinding);

        return  principal == null ? "redirect:/" : "redirect:/home";
    }

    @GetMapping("/details")
    public String messageDetails(@RequestParam("id") String id, Model model) {

        model.addAttribute("message", this.messageService.findById(id));
        this.messageService.markAsRead(id);

        return "message/message-details";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id) {

        this.messageService.deleteById(id);

        return "redirect:/admin";
    }

}
