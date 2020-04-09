package top.zhouy.shoporder.service.impl;

import top.zhouy.shoporder.bean.entity.ShopOrderItem;
import top.zhouy.shoporder.mapper.ShopOrderItemMapper;
import top.zhouy.shoporder.service.ShopOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细 服务实现类
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@Service
public class ShopOrderItemServiceImpl extends ServiceImpl<ShopOrderItemMapper, ShopOrderItem> implements ShopOrderItemService {

}
