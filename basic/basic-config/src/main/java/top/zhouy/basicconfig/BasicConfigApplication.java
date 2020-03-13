package top.zhouy.basicconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BasicConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicConfigApplication.class, args);
	}

}
