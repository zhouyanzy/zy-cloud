package top.zhouy.blogmanage.controller;

import top.zhouy.blogmanage.bean.entity.SysUser;

/**
 * 公共Controller类
 * @author zhouYan
 * @date 2020/3/17 14:21
 */
public class BaseController {
    protected SysUser getUser(){
        SysUser sysUser = new SysUser();
        sysUser.setUserId(1);
        sysUser.setUsername("admin");
        return sysUser;
    }

    protected Integer getUserId(){
        return getUser().getUserId();
    }
}
