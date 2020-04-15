package top.zhouy.shoporder.mapper;

import org.apache.ibatis.annotations.Param;
import top.zhouy.shoporder.bean.entity.ShopOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
public interface ShopOrderMapper extends BaseMapper<ShopOrder> {

    /**
     * 根据订单号查找订单
     * @param orderNo
     * @return
     */
    ShopOrder selectByOrderNo(@Param("orderNo") String orderNo);
}
