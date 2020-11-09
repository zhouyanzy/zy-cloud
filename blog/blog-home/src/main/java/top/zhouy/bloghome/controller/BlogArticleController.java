package top.zhouy.bloghome.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.bloghome.bean.entity.BlogCategory;
import top.zhouy.bloghome.bean.type.CategoryType;
import top.zhouy.bloghome.bean.type.EventType;
import top.zhouy.bloghome.bean.type.MessageType;
import top.zhouy.bloghome.bean.vo.BlogArticleVO;
import top.zhouy.bloghome.event.BlogEvent;
import top.zhouy.bloghome.event.publisher.EventPublisher;
import top.zhouy.bloghome.service.BlogArticleCategoryService;
import top.zhouy.bloghome.service.BlogArticleService;
import top.zhouy.bloghome.service.BlogCategoryService;
import top.zhouy.bloghome.service.BlogMessageService;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.service.DozerService;
import top.zhouy.util.utils.ElasticSearchUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客Controller
 * @author zhouYan
 * @date 2020/11/03 14:21
 */
@RestController
@RequestMapping("/blogArticle")
@Api(description = "博客Controller")
public class BlogArticleController {

    @Autowired
    private DozerService dozerService;

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private BlogArticleCategoryService blogArticleCategoryService;

    @Autowired
    private BlogMessageService blogMessageService;

    /**
     * 查找博客
     * @return
     */
    @GetMapping("/listBlog")
    @ApiOperation(value = "查找博客")
    public R listBlog(Long categoryId, Pageable pageable){
        Map map = new HashMap();
        map.put("count", blogArticleService.countBlog(categoryId));
        map.put("list", blogArticleService.listBlog(categoryId, pageable));
        return R.okData(map);
    }

    /**
     * 查找博客
     * @return
     */
    @GetMapping("/detailBlog")
    @ApiOperation(value = "查找博客")
    public R detailBlog(Long id){
        BlogArticleVO blogArticleVO = dozerService.convert(blogArticleService.getById(id), BlogArticleVO.class);
        blogArticleVO.setCategoryList(blogArticleCategoryService.listCategories(blogArticleVO.getId()));
        blogArticleVO.setTagList(blogArticleCategoryService.listTags(blogArticleVO.getId()));
        blogArticleVO.setMessageList(blogMessageService.listComments(MessageType.BLOG, blogArticleVO.getId()));
        EventPublisher.publishEvent(new BlogEvent(this, EventType.VIEW, id, null));
        return R.ok().put("data", blogArticleVO);
    }

    /**
     * 查找类型
     * @return
     */
    @GetMapping("/listCategory")
    @ApiOperation(value = "查找类型")
    public R listCategory(CategoryType categoryType){
        Map map = new HashMap(2);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_type", categoryType);
        List<BlogCategory> categories = blogCategoryService.list(queryWrapper);
        map.put("count", categories.size());
        map.put("list", categories);
        return R.okData(map);
    }

    /**
     * 喜爱
     * @return
     */
    @PostMapping("/likeBlog")
    @ApiOperation(value = "喜爱")
    public R likeBlog(Long id){
        EventPublisher.publishEvent(new BlogEvent(this, EventType.LIKE, id, null));
        return R.ok();
    }

    /**
     * 查找博客，从elasticSearch
     * @param text
     * @return
     */
    @GetMapping("/searchByES")
    @ApiOperation(value = "查找博客，从elasticSearch")
    public R searchByES(String text, Pageable pageable){
        HashMap map = new HashMap(1);
        map.put("title", text);
        map.put("content", text);
        return R.okData(ElasticSearchUtils.query("blog", map, null, pageable));
    }
}

