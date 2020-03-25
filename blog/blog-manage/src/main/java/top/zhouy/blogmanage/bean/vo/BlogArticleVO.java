package top.zhouy.blogmanage.bean.vo;

import lombok.Data;
import top.zhouy.blogmanage.bean.entity.BlogArticle;
import top.zhouy.blogmanage.bean.entity.BlogArticleCategory;
import top.zhouy.blogmanage.bean.entity.BlogCategory;

import java.util.List;

/**
 * @author zhouYan
 * @date 2020/3/25 13:13
 */
@Data
public class BlogArticleVO extends BlogArticle {

    private List<BlogArticleCategory> categoryList;

    private List<BlogArticleCategory> tagList;

}
