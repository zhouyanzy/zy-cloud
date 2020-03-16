package top.zhouy.commonresponse.exception;

/**
 * 业务逻辑异常
 */
public class BsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final GlobalErrorCode errorCode;

	public BsException(GlobalErrorCode ec) {
		this(ec, ec.getError(), null);
	}

	public BsException(String message) {
		super(message, null);
		errorCode = GlobalErrorCode.UNKNOWN;
	}

	public BsException(GlobalErrorCode ec, String message, Throwable cause) {
		super(message, cause);
		errorCode = ec;
	}
	
	public BsException(GlobalErrorCode ec, String message) {
		this(ec, message, null);
	}

	public BsException(GlobalErrorCode ec, Throwable cause) {
		this(ec, null, cause);
	}
	
	public GlobalErrorCode getErrorCode() {
		return errorCode;
	}
}
