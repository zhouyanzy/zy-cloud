package top.zhouy.shoppay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shoppay.bean.type.PayType;
import top.zhouy.shoppay.fallback.ShopOrderFeignFallback;

/**
 * 订单服务的feign
 * @author zhouYan
 * @date 2020/4/15 14:12
 */
@FeignClient(name = "shop-order", fallback = ShopOrderFeignFallback.class)
public interface ShopOrderFeign {

    /**
     * 支付
     * @param orderNo
     * @param payNo
     * @param payType
     * @param lcnType
     * @return
     */
    @RequestMapping(value = "/shopOrder/onPay", method = RequestMethod.POST)
    R onPay(@RequestParam(value = "orderNo")String orderNo, @RequestParam(value = "payNo") String payNo, @RequestParam(value = "payType") PayType payType, @RequestParam(value = "lcnType") String lcnType);

}
