package springadvanced.exam.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springadvanced.exam.category.repository.CategoryRepository;
import springadvanced.exam.message.repository.MessageRepository;
import springadvanced.exam.product.repository.ProductRepository;
import springadvanced.exam.user.repository.UserEntityRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void should_Return_Admin_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin")
                .with((user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-panel"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("messages"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("stats"));
    }

}
