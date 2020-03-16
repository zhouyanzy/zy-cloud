package top.zhouy.blogmanage.service.impl;

import top.zhouy.blogmanage.bean.entity.Category;
import top.zhouy.blogmanage.mapper.CategoryMapper;
import top.zhouy.blogmanage.service.CategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
