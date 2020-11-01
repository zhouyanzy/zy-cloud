package top.zhouy.commonauthclient.provider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import top.zhouy.commonresponse.bean.model.R;

/**
 * @description: 鉴权服务调用
 * @author: zhouy
 * @create: 2020-10-28 14:09:00
 */
@Component
@FeignClient(name = "basic-auth", fallback = AuthProvider.AuthProviderFallback.class)
public interface AuthProvider {

    /**
     * 鉴权
     * @param authorization
     * @param url
     * @param method
     * @return
     */
    @GetMapping(value = "/auth/permission")
    R<Boolean> auth(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestParam("url") String url, @RequestParam("method") String method);

    /**
     * 解析token
     * @param authorization
     * @param jwt
     * @return
     */
    @GetMapping(value = "/auth/getToken")
    R<String> getToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestParam("jwt") String jwt);


    @Component
    class AuthProviderFallback implements AuthProvider {

        @Override
        public R<Boolean> auth(String authentication, String url, String method) {
            return R.fail();
        }

        @Override
        public R<String> getToken(String authorization, String jwt) {
            return R.fail();
        }
    }

}
