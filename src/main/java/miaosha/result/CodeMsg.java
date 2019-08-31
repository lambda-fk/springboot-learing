package miaosha.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目结果码 这里把这个类设计为 默认的修饰这样 这个类只能在当前包下面使用 不会暴露给外面的包
 * 
 * @author kaifeng1
 *
 */
class CodeMsg {
	private int code;
	private String msg;

	public static CodeMsg SUCESS = new CodeMsg(200, "sucess");
	public static CodeMsg ERROR = new CodeMsg(300, "error");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");

	// 登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
	public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
	public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
	//秒杀模块 5005XX
	public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
	public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501, "不能重复秒杀");
	
	public static Map<Integer,CodeMsg> table = new HashMap<Integer,CodeMsg>();
	static {
		table.put(500215, PASSWORD_ERROR);
		table.put(500210, SESSION_ERROR);
		table.put(500500, MIAO_SHA_OVER);
		table.put(500501, REPEATE_MIAOSHA);
	}
	public static CodeMsg findByCode(int code) {
		return table.get(code);
	}

	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public CodeMsg fillArguments(Object... args) {
		String msg = String.format(this.msg, args);
		return new CodeMsg(code, msg);
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
