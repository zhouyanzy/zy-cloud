package top.zhouy.bloghome.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据库配置
 * @author zhouYan
 * @date 2020/3/13 10:45
 */
@Configuration
public class DataSourceConfig {

    @Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    /**
     * 配置事物管理器
     * @return
     */
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
}
