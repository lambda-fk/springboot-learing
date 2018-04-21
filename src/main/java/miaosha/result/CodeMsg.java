package miaosha.result;

/**
 * 项目结果码
 * 这里把这个类设计为  默认的修饰这样 这个类只能在当前包下面使用
 * 不会暴露给外面的包
 * 
 * @author kaifeng1
 *
 */
 class CodeMsg {
	private int code;
	private String msg;

	public static CodeMsg SUCESS = new CodeMsg(200, "sucess");
	public static CodeMsg ERROR = new CodeMsg(300, "error");

	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
