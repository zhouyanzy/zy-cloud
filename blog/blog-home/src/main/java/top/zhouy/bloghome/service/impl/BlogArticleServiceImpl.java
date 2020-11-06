package top.zhouy.bloghome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.zhouy.bloghome.bean.entity.BlogArticle;
import top.zhouy.bloghome.bean.vo.BlogArticleVO;
import top.zhouy.bloghome.mapper.BlogArticleMapper;
import top.zhouy.bloghome.service.BlogArticleService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author zhouy
 * @since 2020-03-18
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Override
    public Long countBlog(Long categoryId) {
        return blogArticleMapper.countBlog(categoryId);
    }

    @Override
    public List<BlogArticleVO> listBlog(Long categoryId, Pageable pageable) {
        return blogArticleMapper.listBlog(categoryId, pageable);
    }
}
