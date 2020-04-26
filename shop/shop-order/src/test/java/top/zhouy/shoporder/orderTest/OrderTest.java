package top.zhouy.shoporder.orderTest;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import top.zhouy.shoporder.ShopOrderApplication;
import top.zhouy.shoporder.bean.entity.ShopOrder;
import top.zhouy.shoporder.controller.ShopOrderController;
import top.zhouy.shoporder.service.ShopOrderService;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhouYan
 * @date 2020/4/26 15:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class OrderTest {

    @Autowired
    private ShopOrderService shopOrderService;

    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    AtomicLong atomicLong = new AtomicLong();

    @Test
    @PerfTest(invocations = 1000, threads = 10)
    public void testOrder() {
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setOrderNo("112" + atomicLong.getAndIncrement());
        System.out.println("订单" + shopOrder.getOrderNo() + "：" + shopOrderService.createOrder(shopOrder));
    }

}
