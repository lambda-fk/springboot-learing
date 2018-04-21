package miaosha.redis.service.proxy;

import java.util.HashMap;
import java.util.Map;

import miaosha.dao.domain.User;
import miaosha.redis.key.KeyPrefix;
import miaosha.redis.key.space.UserKey;
import miaosha.redis.service.RedisService;

/**
 * 采用依赖的方式 代理了 原先的 RedisService
 * 
 * @author kaifeng1
 *
 */
public class RedisServiceProxy implements RedisService{
	
	private RedisService redisService ;
	
	public RedisServiceProxy(RedisService redisService) {
		this.redisService = redisService;
	}
	public RedisServiceProxy() {
		
	}
	
	public RedisServiceProxy proxy(RedisService redisService) {
		this.redisService = redisService ;
		return this ;
	}
	
	private static Map<Class<?>, KeyPrefix> table = new HashMap<Class<?>, KeyPrefix>();
	static {
		table.put(User.class, UserKey.ID);
	}

	public static KeyPrefix getTargetKeyPrefix(Class<?> claz) {
		return table.get(claz);
	}

	public <T> T getBean(String key, Class<T> claz) throws Exception {
		KeyPrefix fix = getTargetKeyPrefix(claz);
		if(fix == null) {
			System.out.println("please check RedisServiceProxy.table ！");
			throw new Exception("prefix is not valid! ");
		}
		System.out.println("getBean->prefix :"+fix.getPrefix());
		return this.redisService.getBean(fix.getPrefix()+"_"+key, claz);
	}

	public <T> void setBean(String key, T bean) throws Exception {
		KeyPrefix fix = getTargetKeyPrefix(bean.getClass());
		if(fix == null) {
			System.out.println("please check RedisServiceProxy.table ！");
			throw new Exception("prefix is not valid! ");
		}
		System.out.println("setBean-> prefix :"+fix.getPrefix());
		this.redisService.setBean(fix.getPrefix()+"_"+key, bean);
		
	}

	public <T> void delBean(String key, Class<T> claz) throws Exception {
		KeyPrefix fix = getTargetKeyPrefix(claz);
		if(fix == null) {
			System.out.println("please check RedisServiceProxy.table ！");
			throw new Exception("prefix is not valid! ");
		}
		System.out.println("delBean - > prefix :"+fix.getPrefix());
		this.redisService.delBean(fix.getPrefix()+"_"+key, claz);
	}

}
