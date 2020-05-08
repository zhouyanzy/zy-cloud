package top.zhouy.shopproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.zhouy"})
@MapperScan("top.zhouy.shopproduct.mapper")
@EnableCaching
@EnableHystrix
public class ShopProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopProductApplication.class, args);
	}

	/**
	 *  将OAuth2RestTemplate声明为spring bean，OAuth2ProtectedResourceDetails，OAuth2ClientContext springboot会自动帮我们注入
	 */
	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context){
		return new OAuth2RestTemplate(resource,context);
	}
}
