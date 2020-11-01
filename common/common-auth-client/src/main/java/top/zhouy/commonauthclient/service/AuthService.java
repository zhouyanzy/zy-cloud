package top.zhouy.commonauthclient.service;

/**
 * @description: 鉴权接口
 * @author: zhouy
 * @create: 2020-10-28 14:21:00
 */
public interface AuthService {

    /**
     * 鉴权
     * @param authorization
     * @param url
     * @param method
     * @return
     */
    Boolean auth(String authorization, String url, String method);

    /**
     * 获取token
     * @param authorization
     * @return
     */
    String getToken(String authorization);
}
