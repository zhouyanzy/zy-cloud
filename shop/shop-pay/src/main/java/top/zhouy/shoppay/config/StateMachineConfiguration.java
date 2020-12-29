package top.zhouy.shoppay.config;

import io.seata.saga.engine.config.DbStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.rm.StateMachineEngineHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import top.zhouy.shoppay.util.ApplicationContextUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: saga状态机设置
 * @author: zhouy
 * @create: 2020-12-28 20:07:00
 */
@Configuration
public class StateMachineConfiguration {

    @Bean("threadExecutor")
    public ThreadPoolExecutorFactoryBean threadExecutor(){
        ThreadPoolExecutorFactoryBean threadExecutor = new ThreadPoolExecutorFactoryBean();
        threadExecutor.setThreadNamePrefix("SAGA_ASYNC_EXE_");
        threadExecutor.setCorePoolSize(1);
        threadExecutor.setMaxPoolSize(20);
        return threadExecutor;
    }

    @Bean
    public DbStateMachineConfig dbStateMachineConfig(ThreadPoolExecutorFactoryBean threadExecutor, DataSource druidDataSource) throws IOException {
        DbStateMachineConfig dbStateMachineConfig = new DbStateMachineConfig();
        dbStateMachineConfig.setDataSource(druidDataSource);
        dbStateMachineConfig.setThreadPoolExecutor((ThreadPoolExecutor) threadExecutor.getObject());
        dbStateMachineConfig.setResources(new PathMatchingResourcePatternResolver().getResources("classpath*:statelang/*.json"));
        dbStateMachineConfig.setEnableAsync(true);
        dbStateMachineConfig.setApplicationId(ApplicationContextUtils.getApplicationContext().getEnvironment().getProperty("spring.application.name"));
        dbStateMachineConfig.setTxServiceGroup(ApplicationContextUtils.getApplicationContext().getEnvironment().getProperty("spring.application.name") + "-seata");
        return dbStateMachineConfig;
    }

    @Bean
    public ProcessCtrlStateMachineEngine stateMachineEngine(DbStateMachineConfig dbStateMachineConfig){
        ProcessCtrlStateMachineEngine stateMachineEngine = new ProcessCtrlStateMachineEngine();
        stateMachineEngine.setStateMachineConfig(dbStateMachineConfig);
        return stateMachineEngine;
    }

    @Bean
    public StateMachineEngineHolder stateMachineEngineHolder(ProcessCtrlStateMachineEngine stateMachineEngine){
        StateMachineEngineHolder stateMachineEngineHolder = new StateMachineEngineHolder();
        stateMachineEngineHolder.setStateMachineEngine(stateMachineEngine);
        return stateMachineEngineHolder;
    }

}
