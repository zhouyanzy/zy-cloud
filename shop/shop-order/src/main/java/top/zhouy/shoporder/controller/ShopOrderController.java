package top.zhouy.shoporder.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shoporder.bean.entity.ShopOrder;
import top.zhouy.shoporder.bean.type.PayType;
import top.zhouy.shoporder.service.ShopOrderService;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/shopOrder")
@Api("订单接口")
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @ApiOperation("生成订单")
    @PostMapping("/createOrder")
    public R createOrder(ShopOrder shopOrder){
        return R.okData(shopOrderService.createOrder(shopOrder));
    }

    /**
     * 支付完成
     * @param orderNo
     * @param payNo
     * @param payType
     * @return
     */
    @ApiOperation("支付完成，修改订单信息")
    @PostMapping("/onPay")
    public R onPay(@ApiParam("订单号") @RequestParam(value = "orderNo") String orderNo,
                   @ApiParam("支付单号") @RequestParam(value = "payNo") String payNo,
                   @ApiParam("支付方式") @RequestParam(value = "payType") PayType payType,
                   @ApiParam("分布式事务方式，'TCC'，'LCN'，'TXC'") @RequestParam(value = "lcnType") String lcnType){
        Boolean success = false;
        switch (lcnType) {
            case "TXC" :
                success = shopOrderService.onPayTXC(orderNo, payNo, payType);
                break;
            case "TCC" :
                success = shopOrderService.onPayTCC(orderNo, payNo, payType);
                break;
            default:
                success = shopOrderService.onPayLCN(orderNo, payNo, payType);
                break;
        }
        return R.okData(success);
    }
}

