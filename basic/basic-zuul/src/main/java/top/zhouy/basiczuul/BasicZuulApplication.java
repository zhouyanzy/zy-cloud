package top.zhouy.basiczuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients("top.zhouy.commonauthclient.provider")
@EnableHystrix
@ComponentScan(basePackages = {"top.zhouy"})
public class BasicZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicZuulApplication.class, args);
	}

}
