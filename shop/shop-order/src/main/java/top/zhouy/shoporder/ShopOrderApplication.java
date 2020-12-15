package top.zhouy.shoporder;

import com.alibaba.cloud.seata.GlobalTransactionAutoConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, GlobalTransactionAutoConfiguration.class})
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.zhouy"})
@MapperScan("top.zhouy.shoporder.mapper")
@EnableHystrix
public class ShopOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopOrderApplication.class, args);
	}

}
