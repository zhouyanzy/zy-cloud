package top.zhouy.bloghome.bean.vo;

import lombok.Data;
import top.zhouy.bloghome.bean.entity.BlogCategory;

/**
 * 分类视图
 * @author zhouYan
 * @date 2020/3/25 11:53
 */
@Data
public class BlogCategoryVO extends BlogCategory {

    private String parentCategoryName;

}
