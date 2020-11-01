package top.zhouy.basicauth.bean.type;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @description: 模块类型
 * @author: zhouy
 * @create: 2020-10-28 11:35:00
 */
public enum  ModelType implements IEnum<String> {

    AUTH,

    SHOP,

    BLOG;


    @Override
    public String getValue() {
        return this.name();
    }
}
