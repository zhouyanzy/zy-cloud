package top.zhouy.bloghome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.zhouy.bloghome.bean.entity.BlogArticleCategory;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouy
 * @since 2020-03-18
 */
public interface BlogArticleCategoryMapper extends BaseMapper<BlogArticleCategory> {

    /**
     * 查找分类
     * @param id
     * @return
     */
    List<BlogArticleCategory> listCategories(@Param("id") Long id);

    /**
     * 查找标签
     * @param id
     * @return
     */
    List<BlogArticleCategory> listTags(@Param("id") Long id);
}
