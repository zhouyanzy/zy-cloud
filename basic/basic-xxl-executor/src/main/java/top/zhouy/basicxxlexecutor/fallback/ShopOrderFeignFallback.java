package top.zhouy.basicxxlexecutor.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.zhouy.basicxxlexecutor.feign.ShopOrderFeign;
import top.zhouy.basicxxlexecutor.handler.OrderJob;
import top.zhouy.commonresponse.bean.model.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhouYan
 * @date 2020/4/26 14:27
 */
@Component
public class ShopOrderFeignFallback implements ShopOrderFeign {

    private static Logger logger = LoggerFactory.getLogger(ShopOrderFeignFallback.class);

    @Override
    public R listOrderNeedCanceled() {
        logger.error("查询需要取消的订单失败");
        List data = new ArrayList();
        Map<String, Long> map = new HashMap<>();
        map.put("id", 1L);
        data.add(map);
        Map<String, Long> map1 = new HashMap<>();
        map1.put("id", 2L);
        data.add(map1);
        Map<String, Long> map2 = new HashMap<>();
        map2.put("id", 3L);
        data.add(map2);
        Map<String, Long> map3 = new HashMap<>();
        map3.put("id", 4L);
        data.add(map3);
        return R.okData(data);
    }

    @Override
    public R cancelOrder(Long orderId) {
        logger.error("取消订单失败" + orderId);
        return R.fail("网络异常");
    }
}
