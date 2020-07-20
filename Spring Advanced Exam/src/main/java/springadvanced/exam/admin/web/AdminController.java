package springadvanced.exam.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springadvanced.exam.admin.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping()
    public String adminPanel(Model model) {

        model.addAttribute("users", adminService.getAllUsers());
        model.addAttribute("products", adminService.getAllProducts());
        model.addAttribute("messages", adminService.getAllMessages());
        model.addAttribute("categories", adminService.getAllCategories());

        return "admin/admin-panel";
    }

}
