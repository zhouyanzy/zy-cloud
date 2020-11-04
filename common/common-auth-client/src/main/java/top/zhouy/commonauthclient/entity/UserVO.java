package top.zhouy.commonauthclient.entity;

import lombok.Data;
import top.zhouy.commonresponse.bean.entity.User;

/**
 * @description: 用户信息
 * @author: zhouy
 * @create: 2020-11-03 18:06:00
 */
@Data
public class UserVO extends User {

    private String phone;

    private String password;
}
