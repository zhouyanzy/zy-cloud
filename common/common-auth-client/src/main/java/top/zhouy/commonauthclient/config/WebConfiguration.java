package top.zhouy.commonauthclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.zhouy.commonauthclient.interceptor.UserInterceptor;

/**
 * @description: MVC配置
 * @author: zhouy
 * @create: 2020-11-03 17:50:00
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**");
    }

}
