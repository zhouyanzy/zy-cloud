package top.zhouy.shoppay.feign.fallback;

import org.springframework.stereotype.Component;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.shoppay.bean.type.PayType;
import top.zhouy.shoppay.feign.ShopOrderFeign;

/**
 * fallback
 * @author zhouYan
 * @date 2020/4/26 14:27
 */
@Component
public class ShopOrderFeignFallback implements ShopOrderFeign {

    @Override
    public R onPay(String orderNo, String payNo, PayType payType, String lcnType) {
        throw new BsException(ErrorCode.UNKNOWN, "支付调用失败");
    }

    @Override
    public R onPayCompensate(String orderNo, String payNo, PayType payType, String lcnType) {
        throw new BsException(ErrorCode.UNKNOWN, "支付补偿调用失败");
    }

}
