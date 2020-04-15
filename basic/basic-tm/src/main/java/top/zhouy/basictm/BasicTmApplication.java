package top.zhouy.basictm;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagerServer
public class BasicTmApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicTmApplication.class, args);
	}

}
