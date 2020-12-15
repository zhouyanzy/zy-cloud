package top.zhouy.blogmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.zhouy"})
public class BlogManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogManageApplication.class, args);
	}

}
