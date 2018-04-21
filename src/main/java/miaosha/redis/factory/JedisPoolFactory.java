package miaosha.redis.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import miaosha.redis.config.RedisConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 我们需要获取到一个redis的连接池对象，从而可以获取到Jredis来进行redis的操作
 * 
 * 使用@bean注解来生成一个 bean 这个和我们的xml的配置方式是一样的
 * 这样我们这个类将生成一个 bean 这个bean 是一个  JedisPool 的 对象
 * 我们可以使用类型的注入将这个bean注入到我们的其他类中
 * 
 * @author kaifeng1
 *
 */
@Component
public class JedisPoolFactory {

	@Autowired
	RedisConfig redisConfig;

	@Bean
	public JedisPool createJedisPool() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
		poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000); // 配置文件夹中配置的是秒
		JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);

		return jp;
	}
}
