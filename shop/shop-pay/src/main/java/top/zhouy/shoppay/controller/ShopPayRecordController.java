package top.zhouy.shoppay.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shoppay.bean.entity.ShopPayRecord;
import top.zhouy.shoppay.bean.type.PayType;
import top.zhouy.shoppay.service.ShopPayRecordService;

/**
 * <p>
 * 支付记录 前端控制器
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/shopPayRecord")
@Api("支付记录Contoller")
public class ShopPayRecordController {

    @Autowired
    private ShopPayRecordService shopPayRecordService;

    /**
     * 支付完成
     * @param payNo
     * @return
     */
    @ApiOperation("支付完成，修改订单信息")
    @PostMapping("/onPay")
    @HystrixCommand(
            commandKey="onPay",
            commandProperties= {
                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
                    @HystrixProperty(name="execution.isolation.semaphore.maxConcurrentRequests", value="6")
            },
            fallbackMethod = "onPayFallback"
    )
    public R onPay(@ApiParam("订单号") @RequestParam(value = "orderNo") String orderNo,
                   @ApiParam("支付单号") @RequestParam(value = "payNo") String payNo,
                   @ApiParam("支付方式") @RequestParam(value = "payType") PayType payType,
                   @ApiParam("事务类型，默认AT") @RequestParam(value = "type", defaultValue = "AT") String type){
        ShopPayRecord shopPayRecord = new ShopPayRecord();
        shopPayRecord.setPayNo(payNo);
        shopPayRecord.setPayType(payType);
        shopPayRecord.setOrderNo(orderNo);
        Boolean success = false;
        if ("TCC".equals(type)) {
            success = shopPayRecordService.addPayRecordTcc(shopPayRecord);
        } else if ("SAGA".equals(type)) {
            success = shopPayRecordService.addPayRecordSaga(shopPayRecord);
        } else {
            success = shopPayRecordService.addPayRecord(shopPayRecord);
        }
        return R.okData(success);
    }

    public R onPayFallback(@ApiParam("订单号") @RequestParam(value = "orderNo") String orderNo,
                   @ApiParam("支付单号") @RequestParam(value = "payNo") String payNo,
                   @ApiParam("支付方式") @RequestParam(value = "payType") PayType payType,
                   @ApiParam("事务类型，默认AT") @RequestParam(value = "type", defaultValue = "AT") String type) {
        return R.fail("支付接口熔断处理--请稍候重试！");
    }

}

