package top.zhouy.basicxxlexecutor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.zhouy.basicxxlexecutor.fallback.ShopOrderFeignFallback;
import top.zhouy.commonresponse.bean.model.R;


/**
 * 订单服务的feign
 * @author zhouYan
 * @date 2020/4/15 14:12
 */
@FeignClient(name = "shop-order", fallback = ShopOrderFeignFallback.class)
public interface ShopOrderFeign {

    @RequestMapping(value = "/shopOrder/listOrderNeedCanceled", method = RequestMethod.GET)
    R listOrderNeedCanceled();

    @RequestMapping(value = "/shopOrder/cancelOrder", method = RequestMethod.POST)
    R cancelOrder(@RequestParam(value = "orderId")Long orderId);
}
