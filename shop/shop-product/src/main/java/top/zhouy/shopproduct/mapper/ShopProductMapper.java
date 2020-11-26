package top.zhouy.shopproduct.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.zhouy.shopproduct.bean.entity.ShopProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@Component
public interface ShopProductMapper extends BaseMapper<ShopProduct> {

    /**
     * 增加库存
     * @param productId 商品id
     * @param amount 库存
     * @return
     */
    int addStock(@Param("productId") Long productId, @Param("amount") Long amount);

    /**
     * 增加销量
     * @param productId 商品id
     * @param amount 销量
     * @return
     */
    int addSales(@Param("productId") Long productId, @Param("amount") Long amount);
}
