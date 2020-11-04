package top.zhouy.bloghome.bean.vo;

import lombok.Data;
import top.zhouy.bloghome.bean.entity.BlogArticle;
import top.zhouy.bloghome.bean.entity.BlogArticleCategory;

import java.util.List;

/**
 * 博客视图
 * @author zhouYan
 * @date 2020/3/25 13:13
 */
@Data
public class BlogArticleVO extends BlogArticle {

    private List<BlogArticleCategory> categoryList;

    private List<BlogArticleCategory> tagList;

}
