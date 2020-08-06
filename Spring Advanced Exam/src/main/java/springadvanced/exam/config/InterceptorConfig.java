package springadvanced.exam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springadvanced.exam.stats.interceptor.GuestsInterceptor;
import springadvanced.exam.stats.interceptor.TotalClicksInterceptor;
import springadvanced.exam.stats.interceptor.UsersInterceptor;

import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final GuestsInterceptor guestsInterceptor;
    private final TotalClicksInterceptor totalClicksInterceptor;
    private final UsersInterceptor usersInterceptor;

    public InterceptorConfig(GuestsInterceptor guestsInterceptor, TotalClicksInterceptor totalClicksInterceptor,
                             UsersInterceptor usersInterceptor) {
        this.guestsInterceptor = guestsInterceptor;
        this.totalClicksInterceptor = totalClicksInterceptor;
        this.usersInterceptor = usersInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.guestsInterceptor).addPathPatterns("/");
        registry.addInterceptor(this.totalClicksInterceptor).addPathPatterns(List.of("/", "/home/**", "/admin/**",
                "/root-admin/**", "/cart/**", "/user/**"));
        registry.addInterceptor(this.usersInterceptor).addPathPatterns("/home");
    }
}
