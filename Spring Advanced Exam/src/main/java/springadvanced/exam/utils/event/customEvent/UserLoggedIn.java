package springadvanced.exam.utils.event.customEvent;

import org.springframework.context.ApplicationEvent;

import java.security.Principal;
import java.time.LocalDateTime;

public class UserLoggedIn extends ApplicationEvent {

    private String message;

    public UserLoggedIn(Object source, Principal principal) {
        super(source);
        this.message = "User: " + principal.getName() + " has logged in at " + LocalDateTime.now();
    }
}
