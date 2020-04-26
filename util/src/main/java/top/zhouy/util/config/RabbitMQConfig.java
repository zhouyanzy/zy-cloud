package top.zhouy.util.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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

    /**
     * 创建一个模版，绑定的是connectionFactory这个工厂。
     * @param connectionFactory
     * @return
     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
                if (ack) {
                    log.info("消息发送成功" + correlationData.getId());
                } else {
                    log.error("消息发送失败" + correlationData);
                }
            }
        });
        return rabbitTemplate;
    }

    /**
     * 库存队列
     * @return
     */
    @Bean
    Queue queueStock() {
        return new Queue(QUEUE_PRODUCT_STOCK, true, false, true);
    }

    /**
     * 销量队列
     * @return
     */
    @Bean
    Queue queueSales() {
        return new Queue(QUEUE_PRODUCT_SALES, true, false, true);
    }
}
