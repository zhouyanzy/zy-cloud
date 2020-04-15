package top.zhouy.shoppay.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
    public R onPay(@ApiParam("订单号") @RequestParam(value = "orderNo") String orderNo,
                   @ApiParam("支付单号") @RequestParam(value = "payNo") String payNo,
                   @ApiParam("支付方式") @RequestParam(value = "payType") PayType payType,
                   @ApiParam("分布式事务方式，'TCC'，'LCN'，'TXC'") @RequestParam(value = "lcnType") String lcnType){
        ShopPayRecord shopPayRecord = new ShopPayRecord();
        shopPayRecord.setPayNo(payNo);
        shopPayRecord.setPayType(payType);
        shopPayRecord.setOrderNo(orderNo);
        Boolean success = false;
        switch (lcnType) {
            case "TCC" :
                success = shopPayRecordService.addPayRecordTCC(shopPayRecord);
                break;
            case "TXC" :
                success = shopPayRecordService.addPayRecordTXC(shopPayRecord);
                break;
            default:
                success = shopPayRecordService.addPayRecordLCN(shopPayRecord);
        }
        return R.okData(success);
    }

}

