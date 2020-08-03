package springadvanced.exam.utils.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;

@Component
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void userRegistered(UserEntityDto userEntityDto) {
        UserRegistered userRegistered = new UserRegistered(this, userEntityDto);
        applicationEventPublisher.publishEvent(userRegistered);
    }

    public void userDeleted(String message) {
        UserDeleted userDeleted = new UserDeleted(this, message);
        applicationEventPublisher.publishEvent(userDeleted);
    }

}
