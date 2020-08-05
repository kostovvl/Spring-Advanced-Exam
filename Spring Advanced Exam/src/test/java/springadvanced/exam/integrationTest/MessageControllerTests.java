package springadvanced.exam.integrationTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springadvanced.exam.message.domain.Message;
import springadvanced.exam.message.repository.MessageRepository;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();

        Message message = new Message();
        message.setSenderName("Ignat");
        message.setSenderEmail("Ignat@ignat.com");
        message.setSubject("Test mock message");
        message.setMessageBody("Test mock message body");
        message.setCreatedOn(LocalDateTime.now());

        this.messageRepository.saveAndFlush(message);
    }

    @AfterEach
    public void setDown() {
        messageRepository.deleteAll();
    }

    @Test
    public void should_Return_Create_Message_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/messages/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("message/message-create"))
                .andExpect(model().attributeExists("newMessage"));
    }

    @Test
    public void should_Return_Message_Details_Page() throws Exception {
        //arrange
        String messageId = this.messageRepository.findBySubject("Test mock message").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/root-admin/messages/details").param("id", messageId)
        .with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("message/message-details"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void should_Delete_Message() throws Exception {
        //arrange
        String messageId = this.messageRepository.findBySubject("Test mock message").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/root-admin/messages/delete").param("id", messageId)
                .with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        //assert
        Assertions.assertEquals(0, this.messageRepository.count());
    }

}
