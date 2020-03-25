package top.zhouy.blogmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhouy.blogmanage.bean.entity.BlogCategory;
import top.zhouy.blogmanage.bean.type.CategoryType;
import top.zhouy.blogmanage.bean.vo.BlogCategoryVO;
import top.zhouy.blogmanage.service.*;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.util.service.DozerService;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouy
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/blogCategory")
@Api(description = "博客分类Controller")
public class BlogCategoryController {

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private DozerService dozerService;

    @GetMapping("/getCategoryType")
    @ApiOperation(value = "获取分类类型")
    public R getCategoryType(){
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> type = new HashMap<>();
        type.put("id", "CATEGORY_FIRST");
        type.put("name", "CATEGORY_FIRST");
        data.add(type);
        type = new HashMap<>();
        type.put("id", "CATEGORY_SECOND");
        type.put("name", "CATEGORY_SECOND");
        data.add(type);
        type = new HashMap<>();
        type.put("id", "CATEGORY_THIRD");
        type.put("name", "CATEGORY_THIRD");
        data.add(type);
        return R.okData(data);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public R list(String categoryName, boolean isListParent, CategoryType categoryType){
        QueryWrapper<BlogCategory> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(categoryName).ifPresent(t -> queryWrapper.like("category_name", t));
        if (isListParent) {
            switch (categoryType){
                case CATEGORY_SECOND: categoryType = CategoryType.CATEGORY_FIRST; break;
                case CATEGORY_THIRD: categoryType = CategoryType.CATEGORY_SECOND; break;
            }
        }
        CategoryType real = categoryType;
        Optional.ofNullable(real).ifPresent(t -> queryWrapper.eq("category_type", real));
        List<BlogCategory> categories = blogCategoryService.list(queryWrapper);
        if (isListParent) {
            return R.okData(categories);
        }
        List<BlogCategoryVO> categoriesNew = new ArrayList<>();
        categories.forEach(v -> {
            BlogCategoryVO blogCategoryVO = dozerService.convert(v, BlogCategoryVO.class);
            Optional.ofNullable(blogCategoryVO.getParentId()).ifPresent(
                    id -> Optional.ofNullable(blogCategoryService.getById(id)).ifPresent(
                            a -> blogCategoryVO.setParentCategoryName(a.getCategoryName()))
            );
            categoriesNew.add(blogCategoryVO);
        });
        return R.okData(categoriesNew);
    }

    /**
     * 信息
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "根据主键id查询")
    public R detail(@PathVariable("id") Long id){
        return R.ok().put("blogCategory", blogCategoryService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public R save(@RequestBody BlogCategory blogCategory){
        return R.okData(blogCategoryService.saveOrUpdate(blogCategory));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public R delete(@PathVariable Long id){
        return R.okData(blogCategoryService.removeById(id));
    }
}

