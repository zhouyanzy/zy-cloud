package top.zhouy.shoppay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.shoppay.bean.entity.ShopPayRecord;
import top.zhouy.shoppay.feign.ShopOrderFeign;
import top.zhouy.shoppay.mapper.ShopPayRecordMapper;
import top.zhouy.shoppay.service.ShopPayRecordService;
import top.zhouy.shoppay.util.ApplicationContextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付记录 服务实现类
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@Service("shopPayRecordService")
public class ShopPayRecordServiceImpl extends ServiceImpl<ShopPayRecordMapper, ShopPayRecord> implements ShopPayRecordService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopPayRecordMapper shopPayRecordMapper;

    @Autowired
    private ShopOrderFeign shopOrderFeign;

    @Override
    @GlobalTransactional(name = "payAT", rollbackFor = Exception.class)
    public Boolean addPayRecord(ShopPayRecord shopPayRecord) {
        Boolean success = false;
        if (shopPayRecordMapper.insert(shopPayRecord) > 0){
            success = (Boolean) shopOrderFeign.onPay(shopPayRecord.getOrderNo(), shopPayRecord.getPayNo(), shopPayRecord.getPayType(), "AT").get("data");
        } else {
            log.error("新增数据失败" + shopPayRecord);
            throw new BsException(ErrorCode.UNKNOWN, "新增数据失败");
        }
        int temp = 1/0;
        return success;
    }

    @Override
    @GlobalTransactional(name = "payTCC", rollbackFor = Exception.class)
    public Boolean addPayRecordTcc(ShopPayRecord shopPayRecord) {
        Boolean success = false;
        success = (Boolean) shopOrderFeign.onPay(shopPayRecord.getOrderNo(), shopPayRecord.getPayNo(), shopPayRecord.getPayType(), "TCC").get("data");
        if (success){
            success = shopPayRecordMapper.insert(shopPayRecord) > 0;
        } else {
            log.error("新增数据失败" + shopPayRecord);
            throw new BsException(ErrorCode.UNKNOWN, "新增数据失败");
        }
        int temp = 1/0;
        return success;
    }

    @Override
    public Boolean addPayRecordSaga(ShopPayRecord shopPayRecord) {
        log.info("------->开始下单");
        StateMachineEngine stateMachineEngine = (StateMachineEngine) ApplicationContextUtils.getApplicationContext().getBean("stateMachineEngine");
        Map<String, Object> startParams = new HashMap<>(3);
        String businessKey = String.valueOf(System.currentTimeMillis());
        startParams.put("shopPayRecord", shopPayRecord);
        // 这里状态机是同步的，seata也支持异步，可以参考官方示例
        StateMachineInstance inst = stateMachineEngine.startWithBusinessKey("pay", null, businessKey, startParams);
        log.info("执行状态：" + ExecutionStatus.SU.equals(inst.getStatus()) + "saga transaction XID: " + inst.getId());
        inst = stateMachineEngine.getStateMachineConfig().getStateLogStore().getStateMachineInstanceByBusinessKey(businessKey, null);
        log.info("执行状态：" + ExecutionStatus.SU.equals(inst.getStatus()) + "saga transaction XID: " + inst.getId());
        return true;
    }

    @Override
    public Boolean addPayRecordCommit(ShopPayRecord shopPayRecord) {
        return shopPayRecordMapper.insert(shopPayRecord) > 0;
    }

    @Override
    public Boolean addPayRecordCompensate(ShopPayRecord shopPayRecord) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_no", shopPayRecord.getOrderNo());
        queryWrapper.eq("pay_no", shopPayRecord.getPayNo());
        queryWrapper.orderByDesc("id");
        List<ShopPayRecord> shopPayRecordList = shopPayRecordMapper.selectList(queryWrapper);
        return shopPayRecordMapper.deleteById(shopPayRecordList.get(0).getId()) > 0;
    }

    @Override
    public Boolean onPay(ShopPayRecord shopPayRecord) {
        return (Boolean) shopOrderFeign.onPay(shopPayRecord.getOrderNo(), shopPayRecord.getPayNo(), shopPayRecord.getPayType(), "TCC").get("data");
    }

    @Override
    public Boolean onPayCompensate(ShopPayRecord shopPayRecord) {
        return (Boolean) shopOrderFeign.onPayCompensate(shopPayRecord.getOrderNo(), shopPayRecord.getPayNo(), shopPayRecord.getPayType(), "TCC").get("data");
    }
}
