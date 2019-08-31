package miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 进行md5加密
 * 2次md5加密的意义
 * @author kaifeng1
 *
 */
public class Md5Util {
	
	/**
	 * 加点“佐料”
	 * 当用户首次提供密码时（通常是注册时），由系统自动往这个密码里加一些“Salt值
	 * 这个值是由系统随机生成的，并且只有系统知道。然后再散列
	 */
	private static final String salt = "1a2b3c4d";
	
	/**
	 * md5加密
	 * @param src
	 * @return
	 */
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	/**
	 * 第一次加密
	 * @param inputPass
	 * @return
	 */
	public static String inputPassToFormPass(String inputPass) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
		System.out.println(str);
		return md5(str);
	}
	
	/**
	 * 第二次加密 将salt和密码存入到数据库中
	 * @param formPass
	 * @param salt
	 * @return
	 */
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	
	/**
	 * 合成的两次加密
	 * @param inputPass
	 * @param saltDB
	 * @return
	 */
	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass, saltDB);
		return dbPass;
	}
	

}
