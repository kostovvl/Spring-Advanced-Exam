package springadvanced.exam.unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import springadvanced.exam.message.domain.Message;
import springadvanced.exam.message.domain.MessageDto;
import springadvanced.exam.message.domain.MessageView;
import springadvanced.exam.message.repository.MessageRepository;
import springadvanced.exam.message.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {

    @Mock
    MessageRepository messageRepository;

    private MessageService messageService;
    private Message message;


    @BeforeEach
    public void setUp() {
        messageService = new MessageService(messageRepository, new ModelMapper());
        message = new Message();
        message.setId("1");
        message.setSenderName("Pesho");
        message.setSubject("Pesho's subject");
        message.setMessageBody("Pesho's message body");
        message.setSenderEmail("pesho@Pesho.com");
        message.setCreatedOn(LocalDateTime.now());
    }


    @Test
    public void should_Return_All_Messages() {
        //arrange
        when(messageRepository.findAll()).thenReturn(List.of(message));

        //act
        List<MessageDto> allMessages = this.messageService.getAllMessages();

        //assert
        Assertions.assertEquals(1, allMessages.size());
        MessageDto messageDto = allMessages.get(0);
        Assertions.assertEquals(messageDto.getId(), message.getId());
        Assertions.assertEquals(messageDto.getSenderName(), message.getSenderName());
        Assertions.assertEquals(messageDto.getSubject(), message.getSubject());
        Assertions.assertEquals(messageDto.getSenderEmail(), message.getSenderEmail());
    }

    @Test
    public void should_Find_Message_By_Id() {
        //arrange
        when(messageRepository.findById("1")).thenReturn(Optional.of(message));

        //act
        MessageView messageView = this.messageService.findById("1");

        //assert
        Assertions.assertEquals(messageView.getId(), message.getId());
        Assertions.assertEquals(messageView.getSenderName(), message.getSenderName());
        Assertions.assertEquals(messageView.getSubject(), message.getSubject());
        Assertions.assertEquals(messageView.getSenderEmail(), message.getSenderEmail());
    }



}
