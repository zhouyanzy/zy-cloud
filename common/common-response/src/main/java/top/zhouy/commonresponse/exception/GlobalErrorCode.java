package top.zhouy.commonresponse.exception;


import java.util.HashMap;
import java.util.Map;

public enum GlobalErrorCode {

	SUCESS(200, "成功"),
	REDIRECT(302, "重定向"),
	UNAUTHORIZED(401, "未授权"),
	UNKNOWN(-1, "未知异常");

	private static final Map<Integer, GlobalErrorCode> values = new HashMap<Integer, GlobalErrorCode>();
	static {
		for (GlobalErrorCode ec : GlobalErrorCode.values()) {
			values.put(ec.errorCode, ec);
		}
	}

	private int errorCode;
	private String error;

	private GlobalErrorCode(int errorCode, String error) {
		this.errorCode = errorCode;
		this.error = error;
	}

	public static GlobalErrorCode valueOf(int code) {
		GlobalErrorCode ec = values.get(code);
		if (ec != null) {
			return ec;
		}
		return UNKNOWN;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getError() {
		return error;
	}

	public String render() { return errorCode + ":" + error; }

}
