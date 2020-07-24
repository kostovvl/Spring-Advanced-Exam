package springadvanced.exam.message.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.message.domain.Message;
import springadvanced.exam.message.domain.MessageBinding;
import springadvanced.exam.message.domain.MessageDto;
import springadvanced.exam.message.domain.MessageView;
import springadvanced.exam.message.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper mapper;

    public MessageService(MessageRepository messageRepository, ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
    }

    public void addMessage(MessageBinding messageBinding) {
        Message message = this.mapper.map(messageBinding, Message.class);
        message.setCreatedOn(LocalDateTime.now());
        message.setNew(true);

        this.messageRepository.saveAndFlush(message);
    }

    public List<MessageDto> getAllMessages() {
        return this.messageRepository.findAllOrOrderByNew(true)
                .stream()
                .map(m -> this.mapper.map(m, MessageDto.class))
                .collect(Collectors.toList());
    }

    public MessageView findById(String id) {
        return this.messageRepository.findById(id)
                .map(m -> this.mapper.map(m, MessageView.class)).
                orElse(null);
    }
    
    public void markAsRead(String id) {
        Message message = this.messageRepository.getOne(id);
        message.setNew(false);
        this.messageRepository.save(message);
    }

    public void deleteById(String id) {

        this.messageRepository.deleteById(id);

    }
}
