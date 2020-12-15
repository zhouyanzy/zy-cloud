package top.zhouy.blogmanage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: 资源服务器配置
 * @author: zhouy
 * @create: 2020-11-03 14:09:00
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer) {
        resourceServerSecurityConfigurer
                .resourceId("blog");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests()
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/init/**",
                        "/webjars/**",
                        "/actuator/**",
                        "/hystrix.stream").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
