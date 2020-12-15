package top.zhouy.commonmybatis.annotation;


import top.zhouy.commonmybatis.bean.SensitiveStrategy;

import java.lang.annotation.*;

/**
 * @description: 数据脱敏注解
 * @author: zhouy
 * @create: 2020-12-15 13:21:00
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {

    SensitiveStrategy strategy();

}
