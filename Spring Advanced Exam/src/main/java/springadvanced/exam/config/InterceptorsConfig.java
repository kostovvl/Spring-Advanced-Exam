package springadvanced.exam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springadvanced.exam.stats.service.InterceptionService;
import springadvanced.exam.stats.interceptor.VisitorsInterceptor;


@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new VisitorsInterceptor(new InterceptionService())).addPathPatterns("/");
    }
}
