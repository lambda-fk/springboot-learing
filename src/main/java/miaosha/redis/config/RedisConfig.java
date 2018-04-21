package miaosha.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 使用 @ConfigurationProperties 读取  配置文件前缀为  redis的配置项目
 * 这个可以读取配置文件中的配置信息
 * @author kaifeng1
 *
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
	private String host;
	private int port;
	private int timeout;// 秒
	private String password;
	private int poolMaxTotal;
	private int poolMaxIdle;
	private int poolMaxWait;// 秒

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoolMaxTotal() {
		return poolMaxTotal;
	}

	public void setPoolMaxTotal(int poolMaxTotal) {
		this.poolMaxTotal = poolMaxTotal;
	}

	public int getPoolMaxIdle() {
		return poolMaxIdle;
	}

	public void setPoolMaxIdle(int poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}

	public int getPoolMaxWait() {
		return poolMaxWait;
	}

	public void setPoolMaxWait(int poolMaxWait) {
		this.poolMaxWait = poolMaxWait;
	}

}
