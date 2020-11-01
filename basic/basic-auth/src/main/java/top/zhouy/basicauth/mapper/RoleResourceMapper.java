package top.zhouy.basicauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.zhouy.basicauth.bean.entity.RoleResource;

/**
 * @description: 角色权限
 * @author: zhouy
 * @create: 2020-10-28 12:01:00
 */
@Component
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    /**
     * 判断角色是否有资源访问权限
     * @param name
     * @param resourceId
     * @return
     */
    Long matchByRoleName(@Param("name") String name, @Param("resourceId") Long resourceId);
}
