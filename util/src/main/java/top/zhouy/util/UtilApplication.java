package top.zhouy.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"top.zhouy"})
public class UtilApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilApplication.class, args);
	}

}
