package top.zhouy.bloghome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.zhouy.bloghome.bean.entity.BlogArticleCategory;
import top.zhouy.bloghome.bean.vo.BlogArticleCategoryVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouy
 * @since 2020-03-18
 */
@Component
public interface BlogArticleCategoryMapper extends BaseMapper<BlogArticleCategory> {

    /**
     * 查找分类
     * @param id
     * @return
     */
    List<BlogArticleCategoryVO> listCategories(@Param("id") Long id);

    /**
     * 查找标签
     * @param id
     * @return
     */
    List<BlogArticleCategoryVO> listTags(@Param("id") Long id);
}
