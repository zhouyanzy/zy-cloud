package top.zhouy.blogmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhouy.blogmanage.bean.entity.BlogCategory;
import top.zhouy.blogmanage.bean.type.CategoryType;
import top.zhouy.blogmanage.bean.vo.BlogCategoryVO;
import top.zhouy.blogmanage.service.BlogCategoryService;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.service.DozerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 分类Controller
 * @author zhouYan
 * @date 2020/3/17 14:21
 */
@RestController
@RequestMapping("/blogCategory")
@Api(description = "分类Controller")
public class BlogCategoryController {

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private DozerService dozerService;

    /**
     * 查询分类列表
     * @param categoryName
     * @param isListParent
     * @param categoryType
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public R list(@ApiParam(value = "分类名称") @RequestParam(value = "categoryName", required = false)String categoryName,
                  @ApiParam(value = "是否是查找对应的上级分类") @RequestParam(value = "isListParent", required = false)Boolean isListParent,
                  @ApiParam(value = "分类类型") @RequestParam(value = "categoryType", required = false)CategoryType categoryType){
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
     * 根据主键id查询
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "根据主键id查询")
    public R detail(@ApiParam(value = "主键id") @PathVariable("id") Long id){
        return R.ok().put("blogCategory", blogCategoryService.getById(id));
    }

    /**
     * 保存分类
     * @param blogCategory
     * @return
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
    public R delete(@ApiParam(value = "主键id") @PathVariable Long id){
        return R.okData(blogCategoryService.removeById(id));
    }
}

