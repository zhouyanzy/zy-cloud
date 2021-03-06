package top.zhouy.shopproduct.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.zhouy.shopproduct.bean.entity.ShopSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商品规格 Mapper 接口
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@Component
public interface ShopSkuMapper extends BaseMapper<ShopSku> {

    /**
     * 增加库存
     * @param skuId 规格Id
     * @param amount 库存
     * @return
     */
    int addStock(@Param("skuId") Long skuId, @Param("amount") Long amount);

    /**
     * 增加销量
     * @param skuId 规格Id
     * @param amount 销量
     * @return
     */
    int addSales(@Param("skuId") Long skuId, @Param("amount") Long amount);
}
