package top.zhouy.shoppay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shoppay.bean.type.PayType;

import java.util.List;

/**
 * 订单服务的feign
 * @author zhouYan
 * @date 2020/4/15 14:12
 */
@FeignClient(name = "shop-order")
public interface ShopOrderFeign {

    @RequestMapping(value = "/shopOrder/onPay", method = RequestMethod.POST)
    R onPay(@RequestParam(value = "orderNo")String orderNo, @RequestParam(value = "payNo") String payNo, @RequestParam(value = "payType") PayType payType, @RequestParam(value = "lcnType") String lcnType);

}
