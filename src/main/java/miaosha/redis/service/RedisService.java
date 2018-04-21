package miaosha.redis.service;

/**
 * redis 操作服务
 * @author kaifeng1
 *
 */
public interface RedisService {

	/**
	 * 读取key对应的bean
	 * @param key
	 * @param claz
	 * @return
	 * @throws Exception 
	 */
	<T> T getBean(String key , Class<T> claz) throws Exception ;
	/**
	 * 将bean写进redis
	 * @param key
	 * @param bean
	 * @throws Exception 
	 */
	<T> void setBean(String key , T bean) throws Exception;
	
	/**
	 * 删除key
	 * @param key
	 * @throws Exception 
	 */
	<T> void delBean(String key,Class<T> claz) throws Exception;
}
