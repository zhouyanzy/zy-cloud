package top.zhouy.util.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

import static top.zhouy.commonresponse.bean.constant.SysConstants.QUEUE_PRODUCT_SALES;
import static top.zhouy.commonresponse.bean.constant.SysConstants.QUEUE_PRODUCT_STOCK;

/**
 * rabbitMQ配置
 * @author zhouYan
 * @date 2020/4/17 16:18
 */
@Configuration
public class RabbitMQConfig {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${rabbitmq.addresses}")
    private String address;

    @Value("${rabbitmq.port}")
    private Integer port;

    @Value("${rabbitmq.username}")
    private String userName;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.virtual}")
    private String virtualHost;

    /**
     * 创建连接工厂
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setPort(port);
        connectionFactory.setAddresses(address);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setConnectionTimeout(1000);
        return connectionFactory;
    }

    /**
     * 创建连接工厂的一个ampg管理
     * @param connectionFactory
     * @return
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 创建一个模版，绑定的是connectionFactory这个工厂。
     * @param connectionFactory
     * @return
     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /**
     * 库存队列
     * @return
     */
    @Bean
    Queue queueStock() {
        return new Queue(QUEUE_PRODUCT_STOCK, true, true, true);
    }

    /**
     * 销量队列
     * @return
     */
    @Bean
    Queue queueSales() {
        return new Queue(QUEUE_PRODUCT_SALES, true, true, true);
    }
}
