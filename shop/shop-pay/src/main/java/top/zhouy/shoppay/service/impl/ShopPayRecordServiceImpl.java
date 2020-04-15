package top.zhouy.shoppay.service.impl;

import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.shoppay.bean.entity.ShopPayRecord;
import top.zhouy.shoppay.feign.ShopOrderFeign;
import top.zhouy.shoppay.mapper.ShopPayRecordMapper;
import top.zhouy.shoppay.service.ShopPayRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付记录 服务实现类
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@Service
public class ShopPayRecordServiceImpl extends ServiceImpl<ShopPayRecordMapper, ShopPayRecord> implements ShopPayRecordService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopPayRecordMapper shopPayRecordMapper;

    @Autowired
    private ShopOrderFeign shopOrderFeign;

    @TccTransaction(propagation = DTXPropagation.SUPPORTS, cancelMethod = "addPayRecordTTCCancel", confirmMethod = "addPayRecordTTCCConfirm", executeClass = ShopPayRecordServiceImpl.class)
    @Transactional
    @Override
    public Boolean addPayRecordTCC(ShopPayRecord shopPayRecord) {
        TracingContext.tracing().groupId();
        Boolean success = false;
        if (shopPayRecordMapper.insert(shopPayRecord) > 0){
            success = (Boolean) shopOrderFeign.onPay(shopPayRecord.getOrderNo(), shopPayRecord.getPayNo(), shopPayRecord.getPayType(), "TCC").get("data");
        } else {
            log.error("新增数据失败" + shopPayRecord);
            throw new BsException(ErrorCode.UNKNOWN, "新增数据失败");
        }
        int temp = 1/0;
        return success;
    }

    public Boolean addPayRecordTTCCancel(ShopPayRecord shopPayRecord) {
        log.error("增加支付记录失败");
        return false;
    }

    public Boolean addPayRecordTTCCConfirm(ShopPayRecord shopPayRecord) {
        log.error("增加支付记录成功");
        return true;
    }

    @TxcTransaction
    @Transactional
    @Override
    public Boolean addPayRecordTXC(ShopPayRecord shopPayRecord) {
        TracingContext.tracing().groupId();
        Boolean success = false;
        if (shopPayRecordMapper.insert(shopPayRecord) > 0){
            success = (Boolean) shopOrderFeign.onPay(shopPayRecord.getOrderNo(), shopPayRecord.getPayNo(), shopPayRecord.getPayType(), "TXC").get("data");
        } else {
            log.error("新增数据失败" + shopPayRecord);
            throw new BsException(ErrorCode.UNKNOWN, "新增数据失败");
        }
        int temp = 1/0;
        return success;
    }

    @LcnTransaction
    @Transactional
    @Override
    public Boolean addPayRecordLCN(ShopPayRecord shopPayRecord) {
        TracingContext.tracing().groupId();
        Boolean success = false;
        if (shopPayRecordMapper.insert(shopPayRecord) > 0){
            success = (Boolean) shopOrderFeign.onPay(shopPayRecord.getOrderNo(), shopPayRecord.getPayNo(), shopPayRecord.getPayType(), "LCN").get("data");
        } else {
            log.error("新增数据失败" + shopPayRecord);
            throw new BsException(ErrorCode.UNKNOWN, "新增数据失败");
        }
        int temp = 1/0;
        return success;
    }
}
