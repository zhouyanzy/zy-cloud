package top.zhouy.shopproduct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shopproduct.bean.entity.ShopCategory;
import top.zhouy.shopproduct.service.ShopCategoryService;

import java.util.ArrayList;
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
public class ShopCategoryController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 查找分类列表
     * @param categoryName
     * @param page
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public R list(@ApiParam(value = "分类名称") @RequestParam(value = "categoryName", required = false) String categoryName,
                  @ApiParam(value = "分页，当前页数'current'，每页条数'size'") Page page){
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
    public R save(ShopCategory shopCategory){
        return R.okData(shopCategoryService.saveOrUpdate(shopCategory));
    }
}

