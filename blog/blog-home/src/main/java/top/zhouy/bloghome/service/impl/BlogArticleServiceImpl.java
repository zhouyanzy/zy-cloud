package top.zhouy.bloghome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zhouy.bloghome.bean.entity.BlogArticle;
import top.zhouy.bloghome.mapper.BlogArticleMapper;
import top.zhouy.bloghome.service.BlogArticleService;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author zhouy
 * @since 2020-03-18
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

}
