package springadvanced.exam.message.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.message.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper mapper;

    public MessageService(MessageRepository messageRepository, ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
    }
}
