package springadvanced.exam.utils.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springadvanced.exam.message.domain.Message;
import springadvanced.exam.message.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeDefaultMessages implements CommandLineRunner {

    private final MessageRepository messageRepository;

    public InitializeDefaultMessages(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.messageRepository.count() != 0) {
            return;
        }

        List<Message> messages = new ArrayList<>();

        for (int i = 1; i <=5 ; i++) {
            Message message = new Message();
            message.setNew(true);
            message.setSenderName("TestSender" + i);
            message.setSenderEmail("Test" + i + "@Sender.com");
            message.setSubject("Subject" + i);
            message.setMessageBody("Message body for test message" + i +".");
            message.setCreatedOn(LocalDateTime.now());
            messages.add(message);
        }
        this.messageRepository.saveAll(messages);
    }
}
