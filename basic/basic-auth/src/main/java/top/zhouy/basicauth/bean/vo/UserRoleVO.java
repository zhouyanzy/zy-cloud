package top.zhouy.basicauth.bean.vo;

import lombok.Data;
import top.zhouy.basicauth.bean.entity.UserRole;

/**
 * @description: 用户角色
 * @author: zhouy
 * @create: 2020-10-28 13:20:00
 */
@Data
public class UserRoleVO extends UserRole{

    private String roleName;

}
