package top.zhouy.blogmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 博客Controller
 * @author zhouYan
 * @date 2020/3/17 14:21
 */
@RestController
@RequestMapping("/blogArticle")
@Api(description = "博客Controller")
public class BlogArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private DozerService dozerService;

    @Autowired
    private BlogArticleCategoryService blogArticleCategoryService;

    /**
     * 查找博客列表
     * @param title
     * @param page
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public R list(@ApiParam(value = "标题") @RequestParam(value = "title", required = false) String title,
                  @ApiParam(value = "分页，当前页数'current'，每页条数'size'") Page page){
        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(title).ifPresent(t -> queryWrapper.like("title", t));
        IPage<BlogArticle> articleIPage = blogArticleService.page(page, queryWrapper);
        Map map = new HashMap<>();
        Optional.ofNullable(articleIPage).ifPresent(p -> {
            List<BlogArticleVO> list = new ArrayList<>();
            Optional.ofNullable(p.getRecords()).ifPresent(os -> {
                os.forEach(o -> {
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
     * 查找博客明细
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "根据主键id查询")
    public R detail(@ApiParam(value = "主键id") @PathVariable(value = "id") Long id){
        BlogArticleVO blogArticleVO = dozerService.convert(blogArticleService.getById(id), BlogArticleVO.class);
        blogArticleVO.setCategoryList(blogArticleCategoryService.listCategories(blogArticleVO.getId()));
        blogArticleVO.setTagList(blogArticleCategoryService.listTags(blogArticleVO.getId()));
        return R.ok().put("blogArticle", blogArticleVO);
    }

    /**
     * 保存博客信息
     * @param blogArticleVO
     * @return
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
     * @param articleIds
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除")
    public R delete(@ApiParam(value = "主键id") @RequestBody Long[] articleIds){
        for (Long id : articleIds){
            blogArticleService.removeById(id);
        }
        return R.okData(true);
    }
}

