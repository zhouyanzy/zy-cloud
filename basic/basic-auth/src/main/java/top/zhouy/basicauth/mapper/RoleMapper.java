package top.zhouy.basicauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import top.zhouy.basicauth.bean.entity.Role;
import top.zhouy.basicauth.bean.entity.User;

/**
 * @description: 角色mapper
 * @author: zhouy
 * @create: 2020-10-28 11:45:00
 */
@Component
public interface RoleMapper extends BaseMapper<Role>{

}
