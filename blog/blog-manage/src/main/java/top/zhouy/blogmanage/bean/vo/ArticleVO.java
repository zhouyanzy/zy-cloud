package top.zhouy.blogmanage.bean.vo;

import top.zhouy.blogmanage.bean.entity.Article;
import top.zhouy.blogmanage.bean.entity.ArticleCategory;

import java.util.List;

/**
 * @author zhouYan
 * @date 2020/3/13 14:55
 */
public class ArticleVO extends Article{

    List<ArticleCategoryVO> articleCategorys;

    public List<ArticleCategoryVO> getArticleCategorys() {
        return articleCategorys;
    }

    public void setArticleCategorys(List<ArticleCategoryVO> articleCategorys) {
        this.articleCategorys = articleCategorys;
    }
}
