package top.zhouy.basicauth.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.zhouy.commonresponse.bean.model.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义异常
 * @author: zhouy
 * @create: 2020-11-24 10:24:00
 */
@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        R r = R.fail(HttpStatus.UNAUTHORIZED.value(), "client_id或client_secret错误");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSONString(r));
        response.getWriter().flush();
    }
}