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
     * 增加支付记录
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecord(ShopPayRecord shopPayRecord);

    /**
     * 增加支付记录，TCC模式
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecordTcc(ShopPayRecord shopPayRecord);

    /**
     * 增加支付记录，SAGA模式
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecordSaga(ShopPayRecord shopPayRecord);

    /**
     * 增加支付记录，SAGA模式，提交
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecordCommit(ShopPayRecord shopPayRecord);

    /**
     * 增加支付记录，SAGA模式，补偿
     * @param shopPayRecord
     * @return
     */
    Boolean addPayRecordCompensate(ShopPayRecord shopPayRecord);

    /**
     * 订单，SAGA模式，提交
     * @param shopPayRecord
     * @return
     */
    Boolean onPay(ShopPayRecord shopPayRecord);

    /**
     * 订单，SAGA模式，补偿
     * @param shopPayRecord
     * @return
     */
    Boolean onPayCompensate(ShopPayRecord shopPayRecord);
}
