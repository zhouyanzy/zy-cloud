package top.zhouy.shoppay;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDistributedTransaction
@ComponentScan(basePackages = {"top.zhouy"})
@MapperScan("top.zhouy.shoppay.mapper")
@EnableFeignClients("top.zhouy.shoppay.feign")
public class ShopPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopPayApplication.class, args);
	}

}
