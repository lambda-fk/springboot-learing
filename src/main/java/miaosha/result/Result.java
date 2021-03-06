package miaosha.result;

/**
 * 返回结果封装
 * 
 * @author kaifeng1
 *
 * @param <T>
 */
public final class Result<T> {

	private int code;
	private String msg;
	private T data;

	/**
	 * 成功
	 * @param data
	 * @return
	 */
	public static <T> Result<T> sucess(T data) {
		Result<T> r = new Result<T>(CodeMsg.SUCESS);
		r.data = data;
		return r;
	}

	/**
	 * 失败
	 * @param data
	 * @return
	 */
	public static <T> Result<T> error(T data) {
		Result<T> r = new Result<T>(CodeMsg.ERROR);
		r.data = data;
		return r;
	}
	
	/**
	 * 失败
	 * @param data
	 * @return
	 */
	public static <T> Result<T> error(int errorCode , T data) {
		
		Result<T> r = new Result<T>(CodeMsg.findByCode(errorCode));
		r.data = data;
		return r;
	}
	
	/**
	 * 绑定错误异常参数填写
	 * @param data
	 * @param errorArgus
	 * @return
	 */
	public static <T> Result<T> errorArgs(T data , Object...errorArgus) {
		Result<T> r = new Result<T>(CodeMsg.BIND_ERROR.fillArguments(errorArgus));
		r.data = data;
		return r;
	}

	private Result(T data) {
		this.data = data;
	}

	private Result(CodeMsg codeMsg) {
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

}
