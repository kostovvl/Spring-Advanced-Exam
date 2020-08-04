package springadvanced.exam.utils.event;

import org.springframework.context.event.EventListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import springadvanced.exam.message.domain.MessageBinding;
import springadvanced.exam.message.service.MessageService;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class Listeners {

    private final MessageService messageService;

    public Listeners(MessageService messageService) {
        this.messageService = messageService;
    }

    @EventListener(UserRegistered.class)
    public void createMessageForUserRegistered(UserRegistered userRegistered) {
        UserEntityDto userEntityDto = userRegistered.getUserEntityDto();
        MessageBinding messageBinding = new MessageBinding();
        messageBinding.setSenderName("System");
        messageBinding.setSenderEmail("system@system.com");
        messageBinding.setSubject("New User Registered");
        messageBinding.setMessageBody(String.format("User with username: %S and email: %S " +
                " registered in the system at: %s", userEntityDto.getUsername(), userEntityDto.getEmail()
                ,LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));

        this.messageService.addMessage(messageBinding);
    }

    @EventListener(UserDeleted.class)
    public void createMessageForUserDeleted(UserDeleted userDeleted) {
        MessageBinding messageBinding = new MessageBinding();
        messageBinding.setSenderName("System");
        messageBinding.setSenderEmail("system@system.com");
        messageBinding.setSubject("User Deleted");
        messageBinding.setMessageBody(String.format("User with username: %S" +
                        " deleted in the system at: %s", userDeleted.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));

        this.messageService.addMessage(messageBinding);
    }
}
