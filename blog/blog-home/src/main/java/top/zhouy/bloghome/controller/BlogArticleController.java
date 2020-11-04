package top.zhouy.bloghome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.service.DozerService;
import top.zhouy.util.utils.ElasticSearchUtils;

import java.util.HashMap;

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

