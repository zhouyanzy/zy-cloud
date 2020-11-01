package top.zhouy.basicauth.bean.type;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @description: 资源类型
 * @author: zhouy
 * @create: 2020-10-28 11:36:00
 */
public enum ResourceType implements IEnum<String> {

    READ,

    SAVE;

    @Override
    public String getValue() {
        return this.name();
    }
}
