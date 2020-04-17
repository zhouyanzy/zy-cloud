package top.zhouy.commonresponse.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品销售队列
 * @author zhouYan
 * @date 2020/4/17 16:46
 */
@Data
public class QueueProductSalesVO implements Serializable {

    private Long productId;

    private Long skuId;

    private Long amount;

    public QueueProductSalesVO() {
        super();
    }

    public QueueProductSalesVO(Long productId, Long skuId, Long amount) {
        this.productId = productId;
        this.skuId = skuId;
        this.amount = amount;
    }
}
