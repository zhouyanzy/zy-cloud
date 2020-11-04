package top.zhouy.bloghome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zhouy.bloghome.bean.entity.BlogArticleCategory;
import top.zhouy.bloghome.mapper.BlogArticleCategoryMapper;
import top.zhouy.bloghome.service.BlogArticleCategoryService;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author zhouy
 * @since 2020-03-18
 */
@Service
public class BlogArticleCategoryServiceImpl extends ServiceImpl<BlogArticleCategoryMapper, BlogArticleCategory> implements BlogArticleCategoryService {

}
