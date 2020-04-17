package top.zhouy.shopproduct.service;

import cn.hutool.Hutool;
import com.alibaba.druid.util.H2Utils;
import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zhouy.commonresponse.bean.vo.QueueProductSalesVO;
import top.zhouy.commonresponse.bean.vo.QueueProductStockVO;
import top.zhouy.shopproduct.bean.entity.ShopProduct;
import top.zhouy.shopproduct.bean.entity.ShopSku;
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
    public void processStock(QueueProductStockVO queueProductStockVO) {
        shopSkuMapper.addStock(queueProductStockVO.getSkuId(), queueProductStockVO.getAmount());
        shopProductMapper.addStock(queueProductStockVO.getProductId(), queueProductStockVO.getAmount());
    }

    @RabbitListener(queues = QUEUE_PRODUCT_SALES)
    public void processSales(QueueProductSalesVO queueProductSalesVO) {
        shopSkuMapper.addSales(queueProductSalesVO.getSkuId(), queueProductSalesVO.getAmount());
        shopProductMapper.addSales(queueProductSalesVO.getProductId(), queueProductSalesVO.getAmount());
    }
}
