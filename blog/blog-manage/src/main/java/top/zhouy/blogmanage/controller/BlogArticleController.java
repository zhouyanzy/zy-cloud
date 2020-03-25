package top.zhouy.blogmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import top.zhouy.blogmanage.bean.entity.BlogArticle;
import top.zhouy.blogmanage.bean.entity.BlogCategory;
import top.zhouy.blogmanage.bean.status.ArticleStatus;
import top.zhouy.blogmanage.bean.type.CategoryType;
import top.zhouy.blogmanage.bean.vo.BlogArticleVO;
import top.zhouy.blogmanage.bean.vo.BlogCategoryVO;
import top.zhouy.blogmanage.service.BlogArticleCategoryService;
import top.zhouy.blogmanage.service.BlogArticleService;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.service.DozerService;

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
@RequestMapping("/blogArticle")
@Api(description = "博客分类Controller")
public class BlogArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private DozerService dozerService;

    @Autowired
    private BlogArticleCategoryService blogArticleCategoryService;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public R list(String title, Page page){
        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(title).ifPresent(t -> queryWrapper.like("title", t));
        IPage<BlogArticle> articleIPage = blogArticleService.page(page, queryWrapper);
        Map map = new HashMap<>();
        Optional.ofNullable(articleIPage).ifPresent(p -> {
            List<BlogArticleVO> list = new ArrayList<>();
            Optional.ofNullable(p.getRecords()).ifPresent(os -> {
                os.forEach( o -> {
                    BlogArticleVO blogArticleVO = dozerService.convert(o, BlogArticleVO.class);
                    blogArticleVO.setCategoryList(blogArticleCategoryService.listCategories(o.getId()));
                    blogArticleVO.setTagList(blogArticleCategoryService.listTags(o.getId()));
                    list.add(blogArticleVO);
                });
            });
            map.put("list", list);
            map.put("total", p.getTotal());
        });
        return R.okData(map);
    }

    /**
     * 信息
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "根据主键id查询")
    public R detail(@PathVariable("id") Long id){
        BlogArticleVO blogArticleVO = dozerService.convert(blogArticleService.getById(id), BlogArticleVO.class);
        blogArticleVO.setCategoryList(blogArticleCategoryService.listCategories(blogArticleVO.getId()));
        blogArticleVO.setTagList(blogArticleCategoryService.listTags(blogArticleVO.getId()));
        return R.ok().put("blogArticle", blogArticleVO);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public R save(@RequestBody BlogArticleVO blogArticleVO){
        Boolean success = blogArticleService.saveOrUpdate(blogArticleVO);
        Optional.ofNullable(blogArticleVO.getCategoryList()).ifPresent(t -> t.forEach(o -> {o.setArticleId(blogArticleVO.getId());blogArticleCategoryService.saveOrUpdate(o);}));
        Optional.ofNullable(blogArticleVO.getTagList()).ifPresent(t -> t.forEach(o -> {o.setArticleId(blogArticleVO.getId());blogArticleCategoryService.saveOrUpdate(o);}));
        return R.okData(success);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除")
    public R delete(@RequestBody Long[] articleIds){
        for (Long id : articleIds){
            blogArticleService.removeById(id);
        }
        return R.okData(true);
    }
}

