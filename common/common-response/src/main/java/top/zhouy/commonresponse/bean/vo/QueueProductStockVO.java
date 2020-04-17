package top.zhouy.commonresponse.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品库存队列
 * @author zhouYan
 * @date 2020/4/17 16:45
 */
@Data
public class QueueProductStockVO implements Serializable{

    private Long productId;

    private Long skuId;

    private Long amount;

    public QueueProductStockVO() {
        super();
    }

    public QueueProductStockVO(Long productId, Long skuId, Long amount) {
        this.productId = productId;
        this.skuId = skuId;
        this.amount = amount;
    }

}
