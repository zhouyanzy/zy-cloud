package top.zhouy.basicxxlexecutor.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zhouy.basicxxlexecutor.feign.ShopOrderFeign;
import top.zhouy.commonresponse.bean.model.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 订单定时任务
 * @author zhouYan
 * @date 2020/5/7 10:25
 */
@Component
public class OrderJob {

    private static Logger logger = LoggerFactory.getLogger(OrderJob.class);

    @Autowired
    private ShopOrderFeign shopOrderFeign;

    /**
     * 1、简单任务（Bean模式）
     */
    @XxlJob("cancelOrderHandler")
    public ReturnT<String> cancelOrderHandler(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, cancelOrderHandler.");
        R result = shopOrderFeign.listOrderNeedCanceled();
        if ("200".equals(String.valueOf(result.get("code")))) {
            List<Map<Object, Object>> list = (List<Map<Object,Object>>) result.get("data");
            list.forEach(map -> {
                if (map.containsKey("id")){
                    shopOrderFeign.cancelOrder(Long.parseLong(String.valueOf(map.get("id"))));
                }
            });
        }
        return ReturnT.SUCCESS;
    }


    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingCancelOrderHandler")
    public ReturnT<String> shardingCancelOrderHandler(String param) throws Exception {

        // 分片参数
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());

        // 业务逻辑
        for (int i = 0; i < shardingVO.getTotal(); i++) {
            if (i == shardingVO.getIndex()) {
                XxlJobLogger.log("第 {} 片, 命中分片开始处理", i);
            } else {
                XxlJobLogger.log("第 {} 片, 忽略", i);
            }
        }

        R result = shopOrderFeign.listOrderNeedCanceled();
        if ("200".equals(String.valueOf(result.get("code")))) {
            List<Map<Object, Object>> list = (List<Map<Object,Object>>) result.get("data");
            list.forEach(map -> {
                if (map.containsKey("id")){
                    Long orderId = Long.parseLong(String.valueOf(map.get("id")));
                    if (orderId/shardingVO.getTotal() == shardingVO.getIndex()) {
                        shopOrderFeign.cancelOrder(orderId);
                    }
                }
            });
        }

        return ReturnT.SUCCESS;
    }

}