package top.zhouy.basicauth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zhouy.commonresponse.bean.model.R;

/**
 * 全局异常处理
 */
@RestControllerAdvice
class OauthExceptionHandler {

	Logger log = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@ExceptionHandler(value = OAuth2Exception.class)
	public R handleOauth2(OAuth2Exception e) {
		log.error(e.getMessage());
		return R.exception(e);
	}
}