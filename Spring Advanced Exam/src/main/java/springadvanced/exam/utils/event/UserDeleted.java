package springadvanced.exam.utils.event;

import org.springframework.context.ApplicationEvent;

public class UserDeleted extends ApplicationEvent {

    private String message;

    public UserDeleted(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
