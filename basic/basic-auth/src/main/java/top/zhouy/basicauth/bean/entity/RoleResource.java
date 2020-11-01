package top.zhouy.basicauth.bean.entity;

import lombok.Data;
import top.zhouy.commonresponse.bean.entity.BaseEntity;

/**
 * @description: 角色资源
 * @author: zhouy
 * @create: 2020-10-28 11:39:00
 */
@Data
public class RoleResource extends BaseEntity {

    private Long roleId;

    private Long resourceId;

}
