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
import top.zhouy.shopproduct.bean.entity.ShopCategory;
import top.zhouy.shopproduct.bean.entity.ShopProductCategory;
import top.zhouy.shopproduct.service.ShopProductCategoryService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/productCategory")
public class ShopProductCategoryController {

    @Autowired
    private ShopProductCategoryService shopProductCategoryService;

    /**
     * 保存商品分类信息
     * @param shopCategory
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public R save(ShopProductCategory shopProductCategory){
        return R.okData(shopProductCategoryService.saveOrUpdate(shopProductCategory));
    }
}

