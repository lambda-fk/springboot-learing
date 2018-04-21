package miaosha.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miaosha.redis.service.RedisService;
import miaosha.util.JsonCom;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis的操作类
 * 
 * @author kaifeng1
 *
 */
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	JedisPool jedisPool;

	public <T> T getBean(String key, Class<T> claz) {
		Jedis jedis = null;
		T r = null;
		try {
			jedis = this.jedisPool.getResource();
			String json = jedis.get(key);
			r = JsonCom.jsonToBean(json, claz);

		} finally {
			this.returnPool(jedis);
		}
		return r;
	}

	public <T> void setBean(String key, T bean) {

		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			String json = JsonCom.beanToJson(bean);
			jedis.set(key, json);
		} finally {
			this.returnPool(jedis);
		}

	}

	/**
	 * 连接回到连接池
	 * 
	 * @param jedis
	 */
	private void returnPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 删除指定的key
	 */
	public <T> void delBean(String key, Class<T> claz) {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (claz == null) {
				jedis.del(key);
				return;
			}
			T bean = this.getBean(key, claz);
			if (bean != null) {
				jedis.del(key);
			} else {
				System.out.println("key:" + key + " has not value!");
			}

		} finally {
			this.returnPool(jedis);
		}

	}
}
