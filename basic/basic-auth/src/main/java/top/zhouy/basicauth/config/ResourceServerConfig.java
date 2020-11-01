package top.zhouy.basicauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务器配置
 * @author zhouYan
 * @date 2020/3/10 16:42
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource( name = "jwtTokenStore")
    private TokenStore jwtTokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer) {
        resourceServerSecurityConfigurer
                .tokenStore(jwtTokenStore)
                .resourceId("auth");
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
