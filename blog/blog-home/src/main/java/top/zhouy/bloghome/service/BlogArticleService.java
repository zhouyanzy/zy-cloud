package top.zhouy.bloghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Pageable;
import top.zhouy.bloghome.bean.entity.BlogArticle;
import top.zhouy.bloghome.bean.vo.BlogArticleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouy
 * @since 2020-03-18
 */
public interface BlogArticleService extends IService<BlogArticle> {

    /**
     * 统计博客条数
     * @param categoryId
     * @return
     */
    Long countBlog(Long categoryId);

    /**
     * 查找博客
     * @param categoryId
     * @param pageable
     * @return
     */
    List<BlogArticleVO> listBlog(Long categoryId, Pageable pageable);
}
