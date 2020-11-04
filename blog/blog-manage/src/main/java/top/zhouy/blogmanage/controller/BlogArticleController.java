package top.zhouy.blogmanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import top.zhouy.blogmanage.bean.entity.BlogArticle;
import top.zhouy.blogmanage.bean.vo.BlogArticleVO;
import top.zhouy.blogmanage.service.BlogArticleCategoryService;
import top.zhouy.blogmanage.service.BlogArticleService;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.util.service.DozerService;
import top.zhouy.util.utils.ElasticSearchUtils;

import java.io.IOException;
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
        if (success) {
            ElasticSearchUtils.saveDocument("blog", String.valueOf(blogArticleVO.getId()), blogArticleVO, blogArticleVO.getArchive());
        }
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

    /**
     * 创建博客索引
     * @return
     */
    @PostMapping("/createIndex")
    @ApiOperation(value = "创建博客索引")
    public R createIndex(){
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject().field("dynamic", true)
                        .startObject("properties")
                        .startObject("id").field("type", "integer").endObject()
                        .startObject("title").field("type", "text").endObject()
                        .startObject("content").field("type", "text").endObject()
                        .startObject("status").field("type", "text").endObject()
                        .startObject("createdAt").field("type", "date").endObject()
                        .startObject("updatedAt").field("type", "date").endObject()
                        .endObject()
                    .endObject();
        } catch (IOException e) {
            throw new BsException(ErrorCode.UNKNOWN, "创建博客索引失败");
        }
        // 创建索引配置信息，配置
        Settings settings = Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
                .build();
        // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
        CreateIndexRequest request = new CreateIndexRequest("blog", settings);
        request.mapping("doc", builder);
        ElasticSearchUtils.deleteIndex("blog");
        return R.okData(ElasticSearchUtils.createIndex(request));
    }
}

