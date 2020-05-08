package top.zhouy.shoporder;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.zaxxer.hikari.HikariConfig;
import org.apache.shardingsphere.core.config.DataSourceConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient
@EnableDistributedTransaction
@ComponentScan(basePackages = {"top.zhouy"})
@MapperScan("top.zhouy.shoporder.mapper")
@EnableHystrix
public class ShopOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopOrderApplication.class, args);
	}

}
