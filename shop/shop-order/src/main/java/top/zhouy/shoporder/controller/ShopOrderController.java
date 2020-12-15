package top.zhouy.shoporder.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
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
@Slf4j
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @ApiOperation("生成订单")
    @SentinelResource(value = "", blockHandler = "createOrderBlockHandler")
    @PostMapping("/createOrder")
    public R createOrder(ShopOrder shopOrder){
        return R.okData(shopOrderService.createOrder(shopOrder));
    }

    /**
     * 限流
     * @param shopOrder
     * @param e
     * @return
     */
    public R createOrderBlockHandler(ShopOrder shopOrder, BlockException e){
        log.error("生成订单被限流" + shopOrder);
        return R.fail(ErrorCode.SENTINEL.getCode(), e.getMessage());
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
        return R.okData(shopOrderService.onPay(orderNo, payNo, payType));
    }
}

