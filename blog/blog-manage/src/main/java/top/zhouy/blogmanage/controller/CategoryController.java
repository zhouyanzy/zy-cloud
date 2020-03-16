package top.zhouy.blogmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.blogmanage.bean.entity.Article;
import top.zhouy.blogmanage.bean.entity.ArticleCategory;
import top.zhouy.blogmanage.bean.entity.Category;
import top.zhouy.blogmanage.bean.type.CategoryType;
import top.zhouy.blogmanage.bean.vo.ArticleVO;
import top.zhouy.blogmanage.service.CategoryService;
import top.zhouy.commonresponse.bean.model.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouy
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "保存")
    @PostMapping("/saveCategory")
    public R saveCategory(Category category){
        return R.success(categoryService.saveOrUpdate(category));
    }

    @ApiOperation(value = "查询")
    @GetMapping("/listCategory")
    public R listCategory(String name, String parentId, CategoryType categoryType){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(name).ifPresent(t -> queryWrapper.like("name", t + "%"));
        Optional.ofNullable(parentId).ifPresent(t -> queryWrapper.eq("parentId", parentId));
        Optional.ofNullable(categoryType).ifPresent(t -> queryWrapper.eq("categoryType", categoryType));
        return R.success(categoryService.list(queryWrapper));
    }

}

