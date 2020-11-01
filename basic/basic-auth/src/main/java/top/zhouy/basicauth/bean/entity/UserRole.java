package top.zhouy.basicauth.bean.entity;

import lombok.Data;
import top.zhouy.commonresponse.bean.entity.BaseEntity;

/**
 * @description: 用户角色
 * @author: zhouy
 * @create: 2020-10-28 11:39:00
 */
@Data
public class UserRole extends BaseEntity {

    private Long roleId;

    private Long userId;

}
