package top.zhouy.shoporder.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import top.zhouy.shoporder.algorithm.DbAlgorithm;
import top.zhouy.shoporder.algorithm.TableAlgorithm;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 分表配置
 * @author zhouYan
 * @date 2020/3/13 10:45
 */
@Configuration
@MapperScan(basePackages = "top.zhouy.shoporder.mapper", sqlSessionFactoryRef = "shardSqlSessionFactory")
public class ShardDataSourceConfig {

    @Resource
    private DbAlgorithm dbAlgorithm;

    @Resource
    private TableAlgorithm tableAlgorithm;

    @Bean(name = "order00")
    @ConfigurationProperties(prefix = "spring.datasource.druid.order0")
    public DataSource order0() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "order0")
    public DataSourceProxy dataSource0(@Qualifier("order00") DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Bean(name = "order11")
    @ConfigurationProperties(prefix = "spring.datasource.druid.order1")
    public DataSource order1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "order1")
    public DataSourceProxy dataSource1(@Qualifier("order11") DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Bean(name = "dataSource1")
    public DataSource dataSource(@Qualifier("order0") DataSourceProxy order0, @Qualifier("order1") DataSourceProxy order1) throws SQLException {
        System.out.println("init shard datasource");
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
        dataSourceMap.put("order0", order0);
        dataSourceMap.put("order1", order1);
        ShardingRuleConfiguration shardRuleConfig = new ShardingRuleConfiguration();
        // 如果有多个数据表需要分表，依次添加到这里
        shardRuleConfig.getTableRuleConfigs().add(getOrderTableRule());
        shardRuleConfig.getTableRuleConfigs().add(getOrderItemTableRule());
        shardRuleConfig.getTableRuleConfigs().add(getOrderAddressTableRule());
        shardRuleConfig.getTableRuleConfigs().add(getOrderRefundTableRule());
        shardRuleConfig.getTableRuleConfigs().add(getOrderLogisticTableRule());
        Properties p = new Properties();
        p.setProperty("sql.show", Boolean.TRUE.toString());
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardRuleConfig, p);
        return dataSource;
    }

    @Bean(name = "shardSqlSessionFactory")
    public SqlSessionFactory shardSqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return factory.getObject();
    }

    @Bean("shardTransactionManger")
    public DataSourceTransactionManager shardTransactionManger(@Qualifier("dataSource1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "shardSqlSessionTemplate")
    public SqlSessionTemplate shardSqlSessionTemplate(@Qualifier("shardSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 订单表的分表规则配置
     * @return
     */
    private TableRuleConfiguration getOrderTableRule() {
        TableRuleConfiguration result = new TableRuleConfiguration("shop_order","order0.shop_order_$->{0..1},order1.shop_order_$->{0..1}");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", dbAlgorithm));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", tableAlgorithm));
        return result;
    }

    /**
     * 订单详情表的分表规则配置
     * @return
     */
    private TableRuleConfiguration getOrderItemTableRule() {
        TableRuleConfiguration result = new TableRuleConfiguration("shop_order_item","order0.shop_order_item_$->{0..1},order1.shop_order_item_$->{0..1}");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", dbAlgorithm));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", tableAlgorithm));
        return result;
    }

    /**
     * 订单地址表的分表规则配置
     * @return
     */
    private TableRuleConfiguration getOrderAddressTableRule() {
        TableRuleConfiguration result = new TableRuleConfiguration("shop_order_address","order0.shop_order_address_$->{0..1},order1.shop_order_address_$->{0..1}");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", dbAlgorithm));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", tableAlgorithm));
        return result;
    }

    /**
     * 订单物流表的分表规则配置
     * @return
     */
    private TableRuleConfiguration getOrderLogisticTableRule() {
        TableRuleConfiguration result = new TableRuleConfiguration("shop_order_logistic","order0.shop_order_logistic_$->{0..1},order1.shop_order_logistic_$->{0..1}");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", dbAlgorithm));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", tableAlgorithm));
        return result;
    }

    /**
     * 订单退款表的分表规则配置
     * @return
     */
    private TableRuleConfiguration getOrderRefundTableRule() {
        TableRuleConfiguration result = new TableRuleConfiguration("shop_order_refund","order0.shop_order_refund_$->{0..1},order1.shop_order_refund_$->{0..1}");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", dbAlgorithm));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", tableAlgorithm));
        return result;
    }
}