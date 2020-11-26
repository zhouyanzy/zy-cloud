package top.zhouy.basicgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients("top.zhouy.*.provider")
@EnableHystrix
@ComponentScan(basePackages = {"top.zhouy.basicgateway", "top.zhouy.commonprovider"})
public class BasicGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicGatewayApplication.class, args);
	}

}
