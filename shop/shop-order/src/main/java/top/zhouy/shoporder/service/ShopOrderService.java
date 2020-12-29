package top.zhouy.shoporder.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
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
@LocalTCC
public interface ShopOrderService extends IService<ShopOrder> {

    /**
     * 生成订单
     * @param shopOrder
     * @return
     */
    Boolean createOrder(ShopOrder shopOrder);

    /**
     * 订单支付
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    Boolean onPay(String orderNo, String payNo, PayType payType);

    /**
     * 订单支付，saga补偿
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    Boolean onPayCompensate(String orderNo, String payNo, PayType payType);

    /**
     * 订单支付
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    @TwoPhaseBusinessAction(name = "onPayTCC", commitMethod = "onPayCommit", rollbackMethod = "onPayCancel")
    Boolean onPayTCC(@BusinessActionContextParameter(paramName = "orderNo")String orderNo,
                     @BusinessActionContextParameter(paramName = "payNo")String payNo,
                     @BusinessActionContextParameter(paramName = "payType")PayType payType);

    /**
     * 确认方法、可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param context 上下文
     * @return boolean
     */
    Boolean onPayCommit(BusinessActionContext context);

    /**
     * 二阶段取消方法
     *
     * @param context 上下文
     * @return boolean
     */
    Boolean onPayCancel(BusinessActionContext context);
}
