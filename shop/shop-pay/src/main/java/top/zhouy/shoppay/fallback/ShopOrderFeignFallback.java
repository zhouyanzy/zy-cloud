package top.zhouy.shoppay.fallback;

import org.springframework.stereotype.Component;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shoppay.bean.type.PayType;
import top.zhouy.shoppay.feign.ShopOrderFeign;

/**
 * @author zhouYan
 * @date 2020/4/26 14:27
 */
@Component
public class ShopOrderFeignFallback implements ShopOrderFeign {

    @Override
    public R onPay(String orderNo, String payNo, PayType payType, String lcnType) {
        return R.fail("网络错误，请稍候重试");
    }
}
