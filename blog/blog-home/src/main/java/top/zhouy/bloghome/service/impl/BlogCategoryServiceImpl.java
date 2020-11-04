package top.zhouy.bloghome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zhouy.bloghome.bean.entity.BlogCategory;
import top.zhouy.bloghome.mapper.BlogCategoryMapper;
import top.zhouy.bloghome.service.BlogCategoryService;


/**
 * <p>
 *  服务实现类
 * </p>
 * @author zhouy
 * @since 2020-03-18
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {

}
