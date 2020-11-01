package top.zhouy.basicauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.zhouy.basicauth.service.impl.UserServiceImpl;

/**
 * SpringSecurity授权配置
 * @author zhouYan
 * @date 2020/3/10 16:43
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl userService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 可自定义AuthenticationProvider实例加入AuthenticationManager：auth.authenticationProvider(authenticationProvider)
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/init/**",
                        "/webjars/**",
                        "/actuator/**",
                        "/hystrix.stream").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }
}