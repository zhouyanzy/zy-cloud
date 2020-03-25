package top.zhouy.commonresponse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;

/**
 * 全局异常处理
 */
@RestControllerAdvice
class MyExceptionHandler {

	Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BsException.class)
	public R handleMyException(BsException e){
		return R.fail(e.getErrorCode().getCode(), e.getErrorCode().getMsg());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e){
		log.error(e.getMessage(), e);
		return R.exception(ErrorCode.PATH_NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		log.error(e.getMessage(), e);
		return R.exception();
	}
}