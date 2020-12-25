package top.zhouy.shoppay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    @GlobalTransactional(name = "pay", rollbackFor = Exception.class)
    public Boolean addPayRecord(ShopPayRecord shopPayRecord) {
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
}
