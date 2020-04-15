package top.zhouy.shoppay.service;

import top.zhouy.shoppay.bean.entity.ShopPayRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付记录 服务类
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
public interface ShopPayRecordService extends IService<ShopPayRecord> {

    /**
     * 增加支付记录，TTC模式
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecordTCC(ShopPayRecord shopPayRecord);

    /**
     * 增加支付记录，TXC模式
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecordTXC(ShopPayRecord shopPayRecord);

    /**
     * 增加支付记录，LCN模式
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecordLCN(ShopPayRecord shopPayRecord);
}
