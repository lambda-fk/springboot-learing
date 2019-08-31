package miaosha.util;

import java.util.UUID;

/**
 * uuid的创建使用  java.util
 * @author kaifeng1
 *
 */
public class UUIDUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
