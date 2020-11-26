package top.zhouy.shopproduct.service;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.vo.QueueProductSalesVO;
import top.zhouy.commonresponse.bean.vo.QueueProductStockVO;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.shopproduct.mapper.ShopProductMapper;
import top.zhouy.shopproduct.mapper.ShopSkuMapper;

import static top.zhouy.commonresponse.bean.constant.SysConstants.QUEUE_PRODUCT_SALES;
import static top.zhouy.commonresponse.bean.constant.SysConstants.QUEUE_PRODUCT_STOCK;

/**
 * rabbitMQ消费类
 * @author zhouYan
 * @date 2020/4/17 16:58
 */
@Component
public class RabbitComsumeService {

    @Autowired
    private ShopProductMapper shopProductMapper;

    @Autowired
    private ShopSkuMapper shopSkuMapper;

    @RabbitListener(queues = QUEUE_PRODUCT_STOCK)
    @Transactional(rollbackFor = Exception.class)
    public void processStock(QueueProductStockVO queueProductStockVO, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        try {
            shopSkuMapper.addStock(queueProductStockVO.getSkuId(), queueProductStockVO.getAmount());
            shopProductMapper.addStock(queueProductStockVO.getProductId(), queueProductStockVO.getAmount());
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            throw new BsException(ErrorCode.UNKNOWN, "商品库存消息消费失败");
        }
    }

    @RabbitListener(queues = QUEUE_PRODUCT_SALES)
    @Transactional(rollbackFor = Exception.class)
    public void processSales(QueueProductSalesVO queueProductSalesVO, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        try {
            shopSkuMapper.addSales(queueProductSalesVO.getSkuId(), queueProductSalesVO.getAmount());
            shopProductMapper.addSales(queueProductSalesVO.getProductId(), queueProductSalesVO.getAmount());
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            throw new BsException(ErrorCode.UNKNOWN, "商品规格库存消息消费失败");
        }
    }
}
