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
import top.zhouy.shopproduct.service.ShopProductService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/product")
public class ShopProductController {

    @Autowired
    private ShopProductService shopProductService;

    /**
     * 查找商品
     * @param productName
     * @param page
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public R list(@ApiParam(value = "商品名称") @RequestParam(value = "productName", required = false) String productName,
                  @ApiParam(value = "分页，当前页数'current'，每页条数'size'") Page page){
        QueryWrapper<ShopProduct> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(productName).ifPresent(t -> queryWrapper.like("productName", t));
        IPage<ShopProduct> shopProductIPage = shopProductService.page(page, queryWrapper);
        Map map = new HashMap<>();
        Optional.ofNullable(shopProductIPage).ifPresent(iPage -> {
            map.put("list", iPage.getRecords());
            map.put("total", iPage.getTotal());
        });
        return R.okData(map);
    }

    /**
     * 保存商品息
     * @param shopProduct
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public R save(ShopProduct shopProduct){
        return R.okData(shopProductService.saveOrUpdate(shopProduct));
    }
}

