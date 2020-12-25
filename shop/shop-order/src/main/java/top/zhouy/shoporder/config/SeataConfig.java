package top.zhouy.shoporder.config;

import com.alibaba.cloud.seata.SeataProperties;
import io.seata.spring.annotation.GlobalTransactionScanner;
import io.seata.spring.annotation.GlobalTransactionalInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: seata分布式事务配置
 * @author: zhouy
 * @create: 2020-12-14 17:07:00
 */
@Aspect
@Configuration
@EnableConfigurationProperties({SeataProperties.class})
public class SeataConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "@annotation(io.seata.spring.annotation.GlobalTransactional)";

    private ApplicationContext applicationContext;

    private SeataProperties seataProperties;

    public SeataConfig(SeataProperties seataProperties, ApplicationContext applicationContext){
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

    @Bean
    public GlobalTransactionalInterceptor globalTransactionalInterceptor(){
        System.out.println("globalTransactionalInterceptor");
        GlobalTransactionalInterceptor globalTransactionalInterceptor = new GlobalTransactionalInterceptor(null);
        return globalTransactionalInterceptor;
    }

    @Bean
    public Advisor seataAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut,globalTransactionalInterceptor());
    }
}