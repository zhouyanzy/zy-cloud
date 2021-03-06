package top.zhouy.shopproduct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.bean.vo.QueueProductStockVO;
import top.zhouy.shopproduct.bean.entity.ShopSku;
import top.zhouy.shopproduct.service.ShopSkuService;
import top.zhouy.util.utils.RedisUtils;

import java.util.Optional;

import static top.zhouy.commonresponse.bean.constant.SysConstants.QUEUE_PRODUCT_STOCK;

/**
 * 商品规格Controller
 *
 * @author zhouYan
 * @since 2020-04-09
 * CacheConfig指定默认缓存区，缓存区：key的前缀，与指定的key构成redis的key，如 shop::sku::10001
 */
@RestController
@RequestMapping("/sku")
@Api(description = "商品规格Controller")
@CacheConfig(cacheNames = "shop::sku")
public class ShopSkuController {

    @Autowired
    private ShopSkuService shopSkuService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/me")
    @ApiOperation(value = "判断有没有登录")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        return authentication;
    }

    /**
     * 查找商品sku
     * @param productId
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    @ApiOperation(value = "查询规格")
    /**
     * @Cacheable 缓存有数据时，从缓存获取；没有数据时，执行方法，并将返回值保存到缓存中
     * @Cacheable 一般在查询中使用
     * 1) cacheNames 指定缓存区，没有配置使用@CacheConfig指定的缓存区
     * 2) key 指定缓存区的key
     * 3) 注解的值使用SpEL表达式
     * eq ==
     * lt <
     * le <=
     * gt >
     * ge >=
     */
    @Cacheable(cacheNames = "shop::sku::list", key = "#productId")
    @HystrixCommand(
            commandKey = "list",
            commandProperties = {
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")},
            fallbackMethod = "listFallback")
    public R list(@ApiParam(value = "商品id") @RequestParam(value = "productId", required = true) String productId){
        QueryWrapper<ShopSku> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(productId).ifPresent(id -> queryWrapper.eq("product_id", id));
        return R.okData(shopSkuService.list(queryWrapper));
    }

    public R listFallback(@ApiParam(value = "商品id") @RequestParam(value = "productId", required = true) String productId){
        return R.fail("查询数据，请求超时，熔断处理");
    }

    /**
     * 保存sku信息
     * @param shopSku
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存，保存时库存字段为需要增加的库存")
    @CachePut(key = "#shopSku.id", condition = "#shopSku.archive eq 0")
    @Caching(evict={@CacheEvict(cacheNames = "shop::sku::list", key = "#shopSku.productId"),
                    @CacheEvict(key = "#shopSku.id", condition = "#shopSku.archive eq 1")})
    public R save(ShopSku shopSku){
        RedisUtils.redisLock(String.valueOf(shopSku.getId()), 3L);
        Long amount = shopSku.getStock();
        shopSku.setSales(null);
        shopSku.setStock(null);
        Boolean success = shopSkuService.saveOrUpdate(shopSku);
        if (success) {
            if (amount != null && amount > 0) {
                CorrelationData correlationData = new CorrelationData(shopSku.getId().toString());
                rabbitTemplate.convertAndSend(QUEUE_PRODUCT_STOCK, new QueueProductStockVO(shopSku.getProductId(), shopSku.getId(), amount), correlationData);
            }
        }
        return R.okData(success);
    }
}