package top.zhouy.bloghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zhouy.bloghome.bean.entity.BlogArticleCategory;
import top.zhouy.bloghome.bean.vo.BlogArticleCategoryVO;

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
    List<BlogArticleCategoryVO> listCategories(Long id);

    /**
     * 查找标签
     * @param id
     * @return
     */
    List<BlogArticleCategoryVO> listTags(Long id);
}
