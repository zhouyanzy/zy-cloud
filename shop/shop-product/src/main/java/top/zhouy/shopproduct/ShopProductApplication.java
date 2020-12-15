package top.zhouy.shopproduct;

import com.alibaba.cloud.seata.GlobalTransactionAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {GlobalTransactionAutoConfiguration.class})
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
