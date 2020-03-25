package top.zhouy.blogmanage.service;

import top.zhouy.blogmanage.bean.entity.BlogArticleCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zhouy.blogmanage.bean.entity.BlogCategory;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouy
 * @since 2020-03-18
 */
public interface BlogArticleCategoryService extends IService<BlogArticleCategory> {

    /**
     * 查找分类
     * @param id
     * @return
     */
    List<BlogArticleCategory> listCategories(Long id);

    /**
     * 查找标签
     * @param id
     * @return
     */
    List<BlogArticleCategory> listTags(Long id);
}
