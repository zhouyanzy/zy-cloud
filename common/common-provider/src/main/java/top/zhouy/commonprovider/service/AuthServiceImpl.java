package top.zhouy.commonprovider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhouy.commonprovider.provider.AuthProvider;
import top.zhouy.commonresponse.bean.model.R;

/**
 * @description: 鉴权实现类
 * @author: zhouy
 * @create: 2020-10-28 14:23:00
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthProvider authProvider;

    @Override
    public Boolean auth(String authorization, String url, String method) {
        R<Boolean> result = authProvider.auth(authorization, url, method);
        return (Boolean) result.get("data");
    }

    @Override
    public String getToken(String authorization) {
        R<String> result = authProvider.getToken(authorization, authorization);
        return (String) result.get("data");
    }
}
