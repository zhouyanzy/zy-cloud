package top.zhouy.shopproduct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shopproduct.bean.entity.ShopCategory;
import top.zhouy.shopproduct.service.ShopCategoryService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 分类 前端控制器
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/category")
/**
 * 指定默认缓存区
 * 缓存区：key的前缀，与指定的key构成redis的key，如 shop::category::10001
 */
@CacheConfig(cacheNames = "shop::category")
public class ShopCategoryController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 查找分类列表
     * @param categoryName
     * @param page
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
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
    @Cacheable(cacheNames = "shop::category::list", key = "#categoryName + '_' + #page.current + '_' + #page.size")
    public R list(@ApiParam(value = "分类名称") @RequestParam(value = "categoryName", required = false) String categoryName,
                  @ApiParam(value = "分页，当前页数'current'，每页条数'size'") Page page){
        // UserVO userVO = LOCAL_USER.get();
        // log.info(userVO.getUsername());
        QueryWrapper<ShopCategory> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(categoryName).ifPresent(t -> queryWrapper.like("categoryName", t));
        IPage<ShopCategory> shopCategoryIPage = shopCategoryService.page(page, queryWrapper);
        Map map = new HashMap<>();
        Optional.ofNullable(shopCategoryIPage).ifPresent(iPage -> {
            map.put("list", iPage.getRecords());
            map.put("total", iPage.getTotal());
        });
        return R.okData(map);
    }

    /**
     * 保存分类信息
     * @param shopCategory
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    /**
     * allEntries = true ：删除整个缓存区的所有值，此时指定的key无效
     * beforeInvocation = true ：默认false，表示调用方法之后删除缓存数据；true时，在调用之前删除缓存数据(如方法出现异常)
     */
    @CacheEvict(allEntries = true)
    public R save(ShopCategory shopCategory){
        return R.okData(shopCategoryService.saveOrUpdate(shopCategory));
    }
}

