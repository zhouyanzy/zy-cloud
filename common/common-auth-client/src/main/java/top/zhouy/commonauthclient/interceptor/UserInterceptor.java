package top.zhouy.commonauthclient.interceptor;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.zhouy.commonauthclient.entity.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 用户拦截器，保存用户信息
 * @author: zhouy
 * @create: 2020-11-03 17:44:00
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    public static final ThreadLocal<UserVO> LOCAL_USER = new ThreadLocal<UserVO>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            UserVO userVO = JSONUtil.toBean(JSONUtil.parseObj(((Map)JSONUtil.parseObj(token).get("body")).get("user")), UserVO.class);
            LOCAL_USER.set(userVO);
        }
        return true;
    }
}
