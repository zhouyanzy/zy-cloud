package top.zhouy.commonresponse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import top.zhouy.commonresponse.bean.model.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice
class ExceptionHandler {

	Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Handle exceptions thrown by handlers.
	 */
	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public R exception(HttpServletRequest req, Exception e) {
		log.error(req.getRequestURI() + " general error", e);
		BsException be = getBsException(e);
		if (be != null) {
			return R.fail(be.getErrorCode().getErrorCode(), e.getMessage());
		} else {
			return R.fail(GlobalErrorCode.UNKNOWN.getErrorCode(), e.getMessage());
		}
	}

	private BsException getBsException(Throwable e) {
		Throwable eTemp = e;
		do {
			if (eTemp instanceof BsException) {
				return (BsException) eTemp;
			}
			e = eTemp;
			eTemp = e.getCause();
		} while (eTemp != null && eTemp != e);
		return null;
	}
}