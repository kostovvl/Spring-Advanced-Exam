package springadvanced.exam.utils.event.customEvent;

import org.springframework.context.ApplicationEvent;

import java.security.Principal;
import java.time.LocalDateTime;

public class UserLoggedOut extends ApplicationEvent {

    private String message;

    public UserLoggedOut(Object source, Principal principal) {
        super(source);
        this.message = "User: " + principal.getName() + " has logged out at " + LocalDateTime.now();
    }
}
