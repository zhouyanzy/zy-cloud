package top.zhouy.basicauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.zhouy"})
@MapperScan("top.zhouy.basicauth.mapper")
public class BasicAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicAuthApplication.class, args);
	}

}
