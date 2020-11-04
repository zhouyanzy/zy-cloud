package top.zhouy.bloghome.bean.vo;

import lombok.Data;
import top.zhouy.bloghome.bean.entity.BlogArticleCategory;

/**
 * 博客分类关系视图
 * @author zhouYan
 * @date 2020/3/25 14:04
 */
@Data
public class BlogArticleCategoryVO extends BlogArticleCategory {

    private String categoryName;

}
