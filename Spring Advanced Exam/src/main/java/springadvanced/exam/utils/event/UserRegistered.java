package springadvanced.exam.utils.event;

import org.springframework.context.ApplicationEvent;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;

public class UserRegistered extends ApplicationEvent {

    private UserEntityDto userEntityDto;

    public UserRegistered(Object source, UserEntityDto userEntityDto) {
        super(source);
        this.userEntityDto = userEntityDto;
    }

    public UserEntityDto getUserEntityDto() {
        return userEntityDto;
    }

    public void setUserEntityDto(UserEntityDto userEntityDto) {
        this.userEntityDto = userEntityDto;
    }
}
