package top.zhouy.basicgateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.commonresponse.bean.model.R;

/**
 * @description: 熔断降级Controller
 * @author: zhouy
 * @create: 2020-11-25 13:49:00
 */
@RestController
@Slf4j
public class DefaultFallbackController {

    @GetMapping("defaultFallback")
    public R defaultFallback(){
        log.error("网关执行请求失败，web方式记录");
        return R.fail(500, "服务出现异常，降级操作。");
    }

}
