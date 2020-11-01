package top.zhouy.basicauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.zhouy.basicauth.bean.entity.Resource;
import top.zhouy.basicauth.mapper.ResourceMapper;
import top.zhouy.basicauth.mapper.RoleResourceMapper;
import top.zhouy.basicauth.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

/**
 * @description: 鉴权
 * @author: zhouy
 * @create: 2020-10-28 13:07:00
 */
@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Boolean auth(HttpServletRequest request, String url, String method) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        Resource resource = resourceMapper.selectOne(queryWrapper);
        if (resource == null) {
            return true;
        }
        for (GrantedAuthority authority : authorities) {
            if (Optional.ofNullable(roleResourceMapper.matchByRoleName(authority.toString(), resource.getId())).isPresent()) {
                return true;
            }
        }
        return false;
    }
}
