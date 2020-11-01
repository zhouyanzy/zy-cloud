package top.zhouy.basicauth.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 权限接口
 * @author: zhouy
 * @create: 2020-10-28 13:07:00
 */
public interface AuthService {

    /**
     * 鉴权
     * @param request
     * @param url
     * @param method
     * @return
     */
    Boolean auth(HttpServletRequest request, String url, String method);

}
