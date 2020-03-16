package top.zhouy.blogmanage.service.impl;

import top.zhouy.blogmanage.bean.entity.Article;
import top.zhouy.blogmanage.mapper.ArticleMapper;
import top.zhouy.blogmanage.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouy
 * @since 2020-03-13
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
