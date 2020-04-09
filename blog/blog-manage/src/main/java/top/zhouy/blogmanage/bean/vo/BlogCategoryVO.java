package top.zhouy.blogmanage.bean.vo;

import lombok.Data;
import top.zhouy.blogmanage.bean.entity.BlogCategory;

/**
 * 分类视图
 * @author zhouYan
 * @date 2020/3/25 11:53
 */
@Data
public class BlogCategoryVO extends BlogCategory{

    private String parentCategoryName;

}
