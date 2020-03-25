package top.zhouy.blogmanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.zhouy.blogmanage.bean.entity.BlogArticle;
import top.zhouy.blogmanage.bean.entity.BlogArticleCategory;
import top.zhouy.blogmanage.bean.entity.BlogCategory;
import top.zhouy.blogmanage.mapper.BlogArticleCategoryMapper;
import top.zhouy.blogmanage.mapper.BlogArticleMapper;
import top.zhouy.blogmanage.mapper.BlogCategoryMapper;
import top.zhouy.blogmanage.service.BlogArticleCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouy
 * @since 2020-03-18
 */
@Service
public class BlogArticleCategoryServiceImpl extends ServiceImpl<BlogArticleCategoryMapper, BlogArticleCategory> implements BlogArticleCategoryService {

    @Autowired
    private BlogArticleCategoryMapper blogArticleCategoryMapper;

    @Override
    public List<BlogArticleCategory> listCategories(Long id) {
        return blogArticleCategoryMapper.listCategories(id);
    }

    @Override
    public List<BlogArticleCategory> listTags(Long id) {
        return blogArticleCategoryMapper.listTags(id);
    }
}
