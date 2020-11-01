package top.zhouy.shopproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.zhouy"})
@MapperScan("top.zhouy.shopproduct.mapper")
@EnableCaching
@EnableHystrix
public class ShopProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopProductApplication.class, args);
	}
}
