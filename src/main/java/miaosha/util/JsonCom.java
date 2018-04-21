package miaosha.util;

import com.alibaba.fastjson.JSON;

/**
 * json处理
 * @author kaifeng1
 *
 */
public final class JsonCom {

	public static <T> String beanToJson(T bean) {
		String value = null;
		if (bean == null) {
			return value;
		}
		Class<?> claz = bean.getClass();
		if (claz == int.class || claz == Integer.class) {
			return String.valueOf(bean);
		} else if (claz == float.class || claz == Float.class) {
			return String.valueOf(bean);
		} else if (claz == long.class || claz == Long.class) {
			return String.valueOf(bean);
		} else if (claz == boolean.class || claz == Boolean.class) {
			return String.valueOf(bean);
		} else if (claz == String.class) {
			return (String) bean;
		}
		return JSON.toJSONString(bean);

	}

	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String json, Class<T> claz) {
		T t = null;
		if (claz == int.class || claz == Integer.class) {
			t = (T) Integer.valueOf(json);
		} else if (claz == float.class || claz == Float.class) {
			t = (T) Float.valueOf(json);
		} else if (claz == long.class || claz == Long.class) {
			t = (T) Long.valueOf(json);
		} else if (claz == boolean.class || claz == Boolean.class) {
			t = (T) Boolean.valueOf(json);
		} else if (claz == String.class) {
			t = (T) json;
		} else {
			t = (T) JSON.toJavaObject(JSON.parseObject(json), claz);
		}
		return t;
	}

}
