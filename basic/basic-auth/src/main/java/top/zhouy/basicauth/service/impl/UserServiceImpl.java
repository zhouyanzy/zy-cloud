package top.zhouy.basicauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.zhouy.basicauth.bean.entity.User;
import top.zhouy.basicauth.bean.vo.UserRoleVO;
import top.zhouy.basicauth.mapper.UserMapper;
import top.zhouy.basicauth.mapper.UserRoleMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户接口
 * @author zhouYan
 * @date 2020/3/10 16:57
 */
@Slf4j
@Component(value = "userService")
public class UserServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户名:" + username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        User user = userMapper.selectOne(queryWrapper);
        // 查询数据库操作
        if(user == null){
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }else{
            // 用户角色也应在数据库中获取
            // String role = "ROLE_ADMIN";
            List<UserRoleVO> roles = userRoleMapper.listRoles(user.getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
            // 线上环境应该通过用户名查询数据库获取加密后的密码
            String password = passwordEncoder.encode(user.getPassword());
            return new org.springframework.security.core.userdetails.User(username, password, authorities);
        }
    }
}
