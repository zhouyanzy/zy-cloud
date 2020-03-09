package top.zhouy.basiceureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BasicEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicEurekaApplication.class, args);
	}

}
