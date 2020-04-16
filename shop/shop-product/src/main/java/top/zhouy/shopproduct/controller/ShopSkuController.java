package top.zhouy.shopproduct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.cache.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shopproduct.bean.entity.ShopProduct;
import top.zhouy.shopproduct.bean.entity.ShopSku;
import top.zhouy.shopproduct.service.ShopProductService;
import top.zhouy.shopproduct.service.ShopSkuService;
import top.zhouy.util.utils.RedisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 商品规格Controller
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/sku")
@Api(description = "商品规格Controller")
/**
 * 指定默认缓存区
 * 缓存区：key的前缀，与指定的key构成redis的key，如 shop::sku::10001
 */
@CacheConfig(cacheNames = "shop::sku")
public class ShopSkuController {

    @Autowired
    private ShopSkuService shopSkuService;

    /**
     * 查找商品sku
     * @param productId
     * @return
     */
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
    public R list(@ApiParam(value = "商品id") @RequestParam(value = "productId", required = true) String productId){
        QueryWrapper<ShopSku> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(productId).ifPresent(id -> queryWrapper.eq("product_id", id));
        return R.okData(shopSkuService.list(queryWrapper));
    }

    /**
     * 保存sku信息
     * @param shopSku
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    @CachePut(key = "#shopSku.id", condition = "#shopSku.archive eq 0")
    @Caching(evict={@CacheEvict(cacheNames = "shop::sku::list", key = "#shopSku.productId"),
                    @CacheEvict(key = "#shopSku.id", condition = "#shopSku.archive eq 1")})
    public R save(ShopSku shopSku){
        RedisUtils.redisLock(String.valueOf(shopSku.getId()), 3L);
        return R.okData(shopSkuService.saveOrUpdate(shopSku));
    }
}

