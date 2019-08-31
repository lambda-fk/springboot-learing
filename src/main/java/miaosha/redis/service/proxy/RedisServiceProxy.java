package miaosha.redis.service.proxy;

import miaosha.redis.key.KeyPrefix;
import miaosha.redis.service.RedisService;

/**
 * 采用依赖的方式 代理了 原先的 RedisService
 * 
 * @author kaifeng1
 *
 */
public class RedisServiceProxy implements RedisService{
	
	private RedisService redisService ;
	
	private KeyPrefix prefix ;
	
	public RedisServiceProxy(KeyPrefix prefix,RedisService redisService) {
		this.redisService = redisService;
		this.prefix = prefix ;
	}
	
	public RedisServiceProxy proxy(RedisService redisService) {
		this.redisService = redisService ;
		return this ;
	}

	public <T> T getBean(String key, Class<T> claz) throws Exception {
		if(prefix == null) {
			System.out.println("please check RedisServiceProxy.table ！");
			throw new Exception("prefix is not valid! ");
		}
		System.out.println("getBean->prefix :"+prefix.getPrefix());
		return this.redisService.getBean(prefix.getPrefix()+"_"+key, claz);
	}

	public <T> void setBean(String key, T bean) throws Exception {
		if(prefix == null) {
			System.out.println("please check RedisServiceProxy.table ！");
			throw new Exception("prefix is not valid! ");
		}
		System.out.println("setBean-> prefix :"+prefix.getPrefix());
		this.redisService.setBean(prefix.getPrefix()+"_"+key, bean);
		
	}

	public <T> void delBean(String key, Class<T> claz) throws Exception {
		if(prefix == null) {
			System.out.println("please check RedisServiceProxy.table ！");
			throw new Exception("prefix is not valid! ");
		}
		System.out.println("delBean - > prefix :"+prefix.getPrefix());
		this.redisService.delBean(prefix.getPrefix()+"_"+key, claz);
	}

}
