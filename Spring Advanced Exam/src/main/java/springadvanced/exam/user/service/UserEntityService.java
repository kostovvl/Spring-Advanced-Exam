package springadvanced.exam.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springadvanced.exam.user.repository.UserEntityRepository;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper mapper;

    public UserEntityService(UserEntityRepository userEntityRepository, ModelMapper mapper) {
        this.userEntityRepository = userEntityRepository;
        this.mapper = mapper;
    }
}
