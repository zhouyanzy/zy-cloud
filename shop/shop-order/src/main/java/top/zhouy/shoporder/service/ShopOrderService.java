package top.zhouy.shoporder.service;

import top.zhouy.shoporder.bean.entity.ShopOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zhouy.shoporder.bean.type.PayType;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
public interface ShopOrderService extends IService<ShopOrder> {

    /**
     * 订单支付
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    Boolean onPayTXC(String orderNo, String payNo, PayType payType);

    /**
     * 订单支付
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    Boolean onPayTCC(String orderNo, String payNo, PayType payType);

    /**
     * 订单支付
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    Boolean onPayLCN(String orderNo, String payNo, PayType payType);
}
