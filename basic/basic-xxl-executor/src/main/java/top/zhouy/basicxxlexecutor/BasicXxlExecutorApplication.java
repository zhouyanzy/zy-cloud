package top.zhouy.basicxxlexecutor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"top.zhouy"})
@EnableFeignClients("top.zhouy.basicxxlexecutor.feign")
@EnableHystrix
public class BasicXxlExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicXxlExecutorApplication.class, args);
	}

}
