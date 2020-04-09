package top.zhouy.commonresponse.controller;

import top.zhouy.commonresponse.bean.entity.User;

/**
 * 公共基础类
 * @author zhouYan
 * @date 2020/3/17 14:21
 */
public class BaseController {

    protected User getUser(){
        User user = new User();
        user.setId(1L);
        user.setName("zy");
        user.setPhone("15655519356");
        return user;
    }

    protected Long getUserId(){
        return getUser().getId();
    }

}
