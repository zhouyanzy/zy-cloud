package top.zhouy.commonresponse.exception;

import top.zhouy.commonresponse.bean.enums.ErrorCode;

/**
 * 业务逻辑异常
 */
public class BsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	public BsException(ErrorCode errorCode) {
		this(errorCode, errorCode.getMsg(), null);
	}

	public BsException(String message) {
		super(message, null);
		errorCode = ErrorCode.UNKNOWN;
	}

	public BsException(ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public BsException(ErrorCode errorCode, String message) {
		this(errorCode, message, null);
	}

	public BsException(ErrorCode errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
