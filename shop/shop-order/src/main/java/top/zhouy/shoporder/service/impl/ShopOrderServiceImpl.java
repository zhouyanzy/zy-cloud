package top.zhouy.shoporder.service.impl;

import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.shoporder.bean.entity.ShopOrder;
import top.zhouy.shoporder.bean.type.OrderStatus;
import top.zhouy.shoporder.bean.type.PayType;
import top.zhouy.shoporder.mapper.ShopOrderMapper;
import top.zhouy.shoporder.service.ShopOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zhouy.util.utils.RedisUtils;

import java.time.LocalDateTime;
import java.util.Date;
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

    @TccTransaction(propagation = DTXPropagation.SUPPORTS, cancelMethod = "onPayTCCCancel", confirmMethod = "onPayTCCConfirm" , executeClass = ShopOrderServiceImpl.class)
    @Transactional
    @Override
    public Boolean onPayTCC(String orderNo, String payNo, PayType payType) {
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
            shopOrder.setOrderNo(orderNo);
            shopOrder.setPayNo(payNo);
            shopOrder.setPayType(payType);
            shopOrder.setOrderStatus(OrderStatus.PAID);
            shopOrder.setPaidAt(LocalDateTime.now());
            shopOrder.setPaidFee(shopOrder.getTotalFee());
            return shopOrderMapper.insert(shopOrder) > 0;
        }
    }

    public Boolean onPayTCCCancel(String orderNo, String payNo, PayType payType) {
        System.out.println("支付更新订单失败");
        return false;
    }

    public Boolean onPayTCCConfirm(String orderNo, String payNo, PayType payType) {
        System.out.println("支付更新订单成功");
        return true;
    }

    @TxcTransaction
    @Transactional
    @Override
    public Boolean onPayTXC(String orderNo, String payNo, PayType payType) {
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
            shopOrder.setOrderNo(orderNo);
            shopOrder.setPayNo(payNo);
            shopOrder.setPayType(payType);
            shopOrder.setOrderStatus(OrderStatus.PAID);
            shopOrder.setPaidAt(LocalDateTime.now());
            shopOrder.setPaidFee(shopOrder.getTotalFee());
            return shopOrderMapper.insert(shopOrder) > 0;
        }
    }

    @LcnTransaction
    @Transactional
    @Override
    public Boolean onPayLCN(String orderNo, String payNo, PayType payType) {
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
            shopOrder.setOrderNo(orderNo);
            shopOrder.setPayNo(payNo);
            shopOrder.setPayType(payType);
            shopOrder.setOrderStatus(OrderStatus.PAID);
            shopOrder.setPaidAt(LocalDateTime.now());
            shopOrder.setPaidFee(shopOrder.getTotalFee());
            return shopOrderMapper.insert(shopOrder) > 0;
        }
    }

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
}
