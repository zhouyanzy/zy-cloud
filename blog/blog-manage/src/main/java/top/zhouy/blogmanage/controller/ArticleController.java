package top.zhouy.blogmanage.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import top.zhouy.blogmanage.bean.entity.ArticleCategory;
import top.zhouy.blogmanage.service.ArticleCategoryService;
import top.zhouy.blogmanage.service.ArticleService;
import top.zhouy.commonresponse.bean.model.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.blogmanage.bean.entity.Article;
import top.zhouy.blogmanage.bean.vo.ArticleVO;
import top.zhouy.util.service.DozerService;

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
@RequestMapping("/article")
@Api(description = "文章接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private DozerService dozerService;

    @ApiOperation(value = "保存")
    @PostMapping("/saveArticle")
    public R saveArticle(ArticleVO articleVO){
        Article article = dozerService.convert(articleVO, Article.class);
        articleService.saveOrUpdate(article);
        Optional.ofNullable(articleVO.getArticleCategorys()).ifPresent(
            list -> list.forEach(articleCategoryVO -> {
                ArticleCategory articleCategory = dozerService.convert(articleCategoryVO, ArticleCategory.class);
                articleCategory.setArticleId(article.getId());
                articleCategoryService.saveOrUpdate(articleCategory);
                })
        );
        return R.success();
    }

    @ApiOperation(value = "查询")
    @GetMapping("/listArticle")
    public R listArticle(String title, Page page){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(title).ifPresent(t -> queryWrapper.like("title", "%" + t + "%"));
        IPage<Article> articleIPage = articleService.page(page, queryWrapper);
        Map map = new HashMap<>();
        Optional.ofNullable(articleIPage).ifPresent(p -> {
            map.put("record", p.getRecords());
            map.put("total", p.getTotal());
        });
        return R.success(map);
    }
}

