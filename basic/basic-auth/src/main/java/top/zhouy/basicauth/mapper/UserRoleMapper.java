package top.zhouy.basicauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.zhouy.basicauth.bean.entity.UserRole;
import top.zhouy.basicauth.bean.vo.UserRoleVO;

import java.util.List;

/**
 * @description: 用户角色
 * @author: zhouy
 * @create: 2020-10-28 12:01:00
 */
@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 查找用户角色信息
     * @param userId
     * @return
     */
    List<UserRoleVO> listRoles(@Param("userId") Long userId);
}
