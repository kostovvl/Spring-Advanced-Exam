package springadvanced.exam.stats.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import springadvanced.exam.stats.service.InterceptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TotalClicksInterceptor implements HandlerInterceptor {

    private final InterceptionService interceptionService;

    public TotalClicksInterceptor(InterceptionService interceptionService) {
        this.interceptionService = interceptionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.interceptionService.addClick();
        return true;
    }
}
