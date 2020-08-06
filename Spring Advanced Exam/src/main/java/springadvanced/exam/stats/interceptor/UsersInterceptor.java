package springadvanced.exam.stats.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import springadvanced.exam.stats.service.InterceptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UsersInterceptor implements HandlerInterceptor{

    private final InterceptionService interceptionService;

    public UsersInterceptor(InterceptionService interceptionService) {
        this.interceptionService = interceptionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        this.interceptionService.addHomePageVisit();

        return true;
    }
}
