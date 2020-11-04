package top.zhouy.basicauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.basicauth.service.AuthService;
import top.zhouy.commonresponse.bean.model.R;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 权限接口
 * @author: zhouy
 * @create: 2020-10-28 13:28:00
 */
@RestController
@RequestMapping("/auth")
@Api(description = "权限接口")
public class AuthController {

    /**
     * jwt 对称加密密钥
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "鉴权", notes = "根据token判断url和method是否有权限访问")
    @GetMapping("/permission")
    public R<Boolean> getCurrentUser(HttpServletRequest request,
                            @ApiParam(value = "请求路径") @RequestParam(value = "url", required = true) String url,
                            @ApiParam(value = "请求方法") @RequestParam(value = "method", required = false) String method) {
        return R.okData(authService.auth(request, url, method));
    }

    @SneakyThrows
    @ApiOperation(value = "解析token", notes = "解析token")
    @GetMapping("/getToken")
    public R<String> getToken(String jwt) {
        if (jwt.startsWith("bearer ")) {
            jwt = StringUtils.substring(jwt, "bearer ".length());
        }
        return R.okData(new ObjectMapper().writeValueAsString(Jwts.parser().setSigningKey(signingKey.getBytes()).parseClaimsJws(jwt)));
    }
}
