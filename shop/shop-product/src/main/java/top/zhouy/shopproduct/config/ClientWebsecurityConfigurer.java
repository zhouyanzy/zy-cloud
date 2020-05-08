package top.zhouy.shopproduct.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouYan
 * @date 2020/3/10 16:42
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
public class ClientWebsecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                /*.antMatchers(
                        "/swagger-ui.html",
                        "/oauth/authorize",
                        "/sku/me",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/init/**",
                        "/webjars/**",
                        "/actuator/**",
                        "/hystrix.stream").permitAll()*/
                .anyRequest().permitAll();
    }
}
