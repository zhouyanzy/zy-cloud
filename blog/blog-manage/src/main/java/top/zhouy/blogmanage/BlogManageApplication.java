package top.zhouy.blogmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.zhouy"})
@MapperScan("top.zhouy.blogmanage.mapper")  //配置mapper扫描
public class BlogManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogManageApplication.class, args);
	}

}
