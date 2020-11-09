package top.zhouy.bloghome.bean.vo;

import lombok.Data;
import top.zhouy.bloghome.bean.entity.BlogArticle;

import java.util.List;

/**
 * 博客视图
 * @author zhouYan
 * @date 2020/3/25 13:13
 */
@Data
public class BlogArticleVO extends BlogArticle {

    private List<BlogArticleCategoryVO> categoryList;

    private List<BlogArticleCategoryVO> tagList;

    private List<BlogMessageVO> messageList;

}
