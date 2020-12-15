package top.zhouy.shopproduct.config;

import com.alibaba.cloud.seata.SeataProperties;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: seata分布式事务配置
 * @author: zhouy
 * @create: 2020-12-14 17:07:00
 */
@Configuration
@EnableConfigurationProperties({SeataProperties.class})
public class SeataAutoConfig {

    private ApplicationContext applicationContext;

    private SeataProperties seataProperties;

    public SeataAutoConfig(SeataProperties seataProperties, ApplicationContext applicationContext){
        this.applicationContext=applicationContext;
        this.seataProperties=seataProperties;
    }

    @Bean
    public GlobalTransactionScanner globalTransactionScanner(){
        String applicationName = this.applicationContext.getEnvironment().getProperty("spring.application.name");
        String txServiceGroup = this.seataProperties.getTxServiceGroup();
        if (StringUtils.isEmpty(txServiceGroup)) {
            txServiceGroup = applicationName + "-seata";
            this.seataProperties.setTxServiceGroup(txServiceGroup);
        }
        return new GlobalTransactionScanner(applicationName, txServiceGroup);
    }
}
