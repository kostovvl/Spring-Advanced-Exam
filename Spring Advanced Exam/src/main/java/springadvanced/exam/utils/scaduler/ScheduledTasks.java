package springadvanced.exam.utils.scaduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springadvanced.exam.user.service.UserEntityService;

@Component
public class ScheduledTasks {

  private final UserEntityService userEntityService;

    public ScheduledTasks(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Scheduled(cron = "0 0 * * * ? *")
    public void refreshUserDiscounts() {
        this.userEntityService.refreshPersonalDiscount();
    }
}
