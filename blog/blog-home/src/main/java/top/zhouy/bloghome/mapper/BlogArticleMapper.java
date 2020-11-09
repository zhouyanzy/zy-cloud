package top.zhouy.bloghome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import top.zhouy.bloghome.bean.entity.BlogArticle;
import top.zhouy.bloghome.bean.vo.BlogArticleVO;

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
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    /**
     * 统计条数
     * @param categoryId
     * @return
     */
    Long countBlog(@Param("categoryId") Long categoryId);

    /**
     * 统计条数
     * @param categoryId
     * @param pageable
     * @return
     */
    List<BlogArticleVO> listBlog(@Param("categoryId") Long categoryId, @Param("pageable") Pageable pageable);

    /**
     * 点赞
     * @param id
     * @param amount
     * @return
     */
    int like(@Param("id") Long id, @Param("amount") Integer amount);

    /**
     * 浏览
     * @param id
     * @param amount
     * @return
     */
    int view(@Param("id") Long id, @Param("amount") Integer amount);
}
