package top.zhouy.shopproduct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;
import top.zhouy.shopproduct.bean.entity.ShopProduct;
import top.zhouy.shopproduct.service.ShopProductService;
import top.zhouy.util.utils.ElasticSearchUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 商品Controller
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/product")
@Api(description = "商品Controller")
public class ShopProductController {

    @Autowired
    private ShopProductService shopProductService;

    /**
     * 查找商品
     * @param productName
     * @param page
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    public R list(@ApiParam(value = "商品名称") @RequestParam(value = "productName", required = false) String productName,
                  @ApiParam(value = "分页，当前页数'current'，每页条数'size'") Page page){
        QueryWrapper<ShopProduct> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(productName).ifPresent(t -> queryWrapper.like("productName", t));
        IPage<ShopProduct> shopProductIPage = shopProductService.page(page, queryWrapper);
        Map map = new HashMap<>();
        Optional.ofNullable(shopProductIPage).ifPresent(iPage -> {
            map.put("list", iPage.getRecords());
            map.put("total", iPage.getTotal());
        });
        return R.okData(map);
    }

    /**
     * 保存商品息
     * @param shopProduct
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public R save(ShopProduct shopProduct){
        Boolean success = shopProductService.saveOrUpdate(shopProduct);
        if (success) {
            ElasticSearchUtils.saveDocument("product", String.valueOf(shopProduct.getId()), shopProduct, shopProduct.getArchive());
        }
        return R.okData(success);
    }

    /**
     * 查找商品，从elasticSearch
     * @param productName
     * @return
     */
    @GetMapping("/searchByES")
    @ApiOperation(value = "查找商品，从elasticSearch")
    public R searchByES(String productName){
        // 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                .matchPhraseQuery("productName", productName);
        searchSourceBuilder.query(matchPhraseQueryBuilder);
        // 创建查询请求对象，将查询对象配置到其中
        SearchRequest searchRequest = new SearchRequest("product");
        searchRequest.source(searchSourceBuilder);
        return R.okData(ElasticSearchUtils.query(searchRequest));
    }

    /**
     * 创建商品索引
     * @return
     */
    @PostMapping("/createIndex")
    @ApiOperation(value = "创建商品索引")
    public R createIndex(){
        // 创建 Mapping
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", true)
                    .startObject("properties")
                    .startObject("productName")
                    .field("type","text")
                    .endObject()
                    .startObject("detail")
                    .field("type","text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type","keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("productImg")
                    .field("type","text")
                    .endObject()
                    .startObject("stock")
                    .field("type","integer")
                    .endObject()
                    .startObject("price")
                    .field("type","float")
                    .endObject()
                    .startObject("sales")
                    .field("type","integer")
                    .endObject()
                    .startObject("createdAt")
                    .field("type","date")
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            throw new BsException(ErrorCode.UNKNOWN, "创建商品索引失败");
        }
        // 创建索引配置信息，配置
        Settings settings = Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
                .build();
        // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
        CreateIndexRequest request = new CreateIndexRequest("product", settings);
        request.mapping("doc", mapping);
        ElasticSearchUtils.deleteIndex("product");
        return R.okData(ElasticSearchUtils.createIndex(request));
    }
}

