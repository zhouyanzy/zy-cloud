package top.zhouy.shoporder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhouy.shoporder.bean.entity.ShopOrder;
import top.zhouy.shoporder.bean.type.OrderStatus;
import top.zhouy.shoporder.bean.type.PayType;
import top.zhouy.shoporder.mapper.ShopOrderMapper;
import top.zhouy.shoporder.service.ShopOrderService;
import top.zhouy.util.utils.RedisUtils;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@Service
public class ShopOrderServiceImpl extends ServiceImpl<ShopOrderMapper, ShopOrder> implements ShopOrderService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @HystrixCommand(
            commandKey = "createOrder",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD")
            },
            threadPoolKey = "createOrderThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "3"),
                    @HystrixProperty(name = "maxQueueSize", value = "5"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "7")
            },
            fallbackMethod = "createOrderFallback"
    )
    @Override
    public Boolean createOrder(ShopOrder shopOrder) {
        shopOrder.setId(RedisUtils.getId("SHOP_ORDER"));
        System.out.println(shopOrder.getId());
        return shopOrderMapper.insert(shopOrder) > 0;
    }

    public Boolean createOrderFallback(ShopOrder shopOrder){
        log.error("订单生成，熔断处理");
        return false;
    }

    @Override
    @ShardingTransactionType(TransactionType.BASE)
    @Transactional(rollbackFor = Exception.class)
    public Boolean onPay(String orderNo, String payNo, PayType payType) {
        return pay(orderNo, payNo, payType);
    }

    @Override
    public Boolean onPayCompensate(String orderNo, String payNo, PayType payType) {
        return cancel(orderNo, payNo, payType);
    }

    /**
     * 支付
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    private Boolean pay (String orderNo, String payNo, PayType payType) {
        ShopOrder shopOrder = shopOrderMapper.selectByOrderNo(orderNo);
        if (Optional.ofNullable(shopOrder).isPresent()){
            shopOrder.setPayNo(payNo);
            shopOrder.setPayType(payType);
            shopOrder.setOrderStatus(OrderStatus.PAID);
            shopOrder.setPaidAt(LocalDateTime.now());
            shopOrder.setPaidFee(shopOrder.getTotalFee());
            return shopOrderMapper.updateById(shopOrder) > 0;
        } else {
            shopOrder = new ShopOrder();
            shopOrder.setId(RedisUtils.getId("SHOP_ORDER"));
            shopOrder.setOrderNo(orderNo);
            shopOrder.setPayNo(payNo);
            shopOrder.setPayType(payType);
            shopOrder.setOrderStatus(OrderStatus.PAID);
            shopOrder.setPaidAt(LocalDateTime.now());
            shopOrder.setPaidFee(shopOrder.getTotalFee());
            return shopOrderMapper.insert(shopOrder) > 0;
        }
    }

    @Override
    public Boolean onPayTCC(String orderNo, String payNo, PayType payType) {
        return pay(orderNo, payNo, payType);
    }

    @Override
    public Boolean onPayCommit(BusinessActionContext context) {
        return true;
    }

    @Override
    public Boolean onPayCancel(BusinessActionContext context) {
        return cancel(String.valueOf(context.getActionContext("orderNo")), String.valueOf(context.getActionContext("payNo")), PayType.valueOf(String.valueOf(context.getActionContext("payType"))));
    }

    /**
     * 取消
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    private Boolean cancel(String orderNo, String payNo, PayType payType) {
        ShopOrder shopOrder = shopOrderMapper.selectByOrderNo(orderNo);
        if (Optional.ofNullable(shopOrder).isPresent()){
            shopOrder.setPayNo(payNo);
            shopOrder.setPayType(payType);
            shopOrder.setOrderStatus(OrderStatus.SUBMITTED);
            shopOrder.setPaidAt(LocalDateTime.now());
            shopOrder.setPaidFee(shopOrder.getTotalFee());
            return shopOrderMapper.updateById(shopOrder) > 0;
        } else {
            return true;
        }
    }

}
