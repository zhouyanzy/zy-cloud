package top.zhouy.basicauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @description: 自定义异常
 * @author: zhouy
 * @create: 2020-11-05 11:58:00
 */
@JsonSerialize(using = UserOauth2ExceptionSerializer.class)
public class UserOauth2Exception extends OAuth2Exception {

    private Integer status = 400;

    public UserOauth2Exception(String message, Throwable t) {
        super(message, t);
        status = ((OAuth2Exception)t).getHttpErrorCode();
    }

    public UserOauth2Exception(String message) {
        super(message);
    }

    @Override
    public int getHttpErrorCode() {
        return status;
    }
}
