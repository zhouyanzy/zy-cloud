package top.zhouy.shopproduct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shopproduct.bean.entity.ShopProduct;
import top.zhouy.shopproduct.bean.entity.ShopSku;
import top.zhouy.shopproduct.service.ShopProductService;
import top.zhouy.shopproduct.service.ShopSkuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 商品规格 前端控制器
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/category")
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
        public R list(@ApiParam(value = "商品id") @RequestParam(value = "productId", required = true) String productId){
        QueryWrapper<ShopSku> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(productId).ifPresent(id -> queryWrapper.eq("productId", id));
        return R.okData(shopSkuService.list(queryWrapper));
    }

    /**
     * 保存sku信息
     * @param shopSku
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public R save(ShopSku shopSku){
        return R.okData(shopSkuService.saveOrUpdate(shopSku));
    }
}

