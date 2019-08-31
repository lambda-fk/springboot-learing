package miaosha.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String msg;
	
	public static ApplicationException SERVER_ERROR = new ApplicationException(205,"服务器处理出错");
	public static ApplicationException MOBILE_NOT_EXIST = new ApplicationException(206,"手机号不存在!");
	public static ApplicationException PASSWORD_ERROR = new ApplicationException(207,"密码错误!");
	public ApplicationException(int errorCode, String msg) {
		super(errorCode + ":" + msg);
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getMsg() {
		return msg;
	}

}
