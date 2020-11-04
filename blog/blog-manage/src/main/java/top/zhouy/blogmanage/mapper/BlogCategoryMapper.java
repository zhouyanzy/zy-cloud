package top.zhouy.blogmanage.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.zhouy.blogmanage.bean.entity.BlogArticleCategory;
import top.zhouy.blogmanage.bean.entity.BlogCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {

}
