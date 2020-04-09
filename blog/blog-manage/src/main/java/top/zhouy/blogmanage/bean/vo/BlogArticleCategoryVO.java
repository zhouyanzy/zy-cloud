package top.zhouy.blogmanage.bean.vo;

import lombok.Data;
import top.zhouy.blogmanage.bean.entity.BlogArticleCategory;

/**
 * 博客分类关系视图
 * @author zhouYan
 * @date 2020/3/25 14:04
 */
@Data
public class BlogArticleCategoryVO extends BlogArticleCategory {

    private String categoryName;

}
