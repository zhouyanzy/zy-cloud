package top.zhouy.blogmanage.bean.type;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * @author zhouYan
 * @date 2020/3/13 13:18
 */
public enum CategoryType implements IEnum<String> {

    CATEGORY_FIRST,

    CATEGORY_SECOND,

    CATEGORY_THIRD,

    CATEGORY_FOUR,

    BRAND_FIRST,

    BRAND_SECOND,

    BRAND_THIRD;

    @Override
    public String getValue() {
        return this.name();
    }
}
