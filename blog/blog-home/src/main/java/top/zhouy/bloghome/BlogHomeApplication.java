package top.zhouy.bloghome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.zhouy"})
public class BlogHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogHomeApplication.class, args);
	}

}
