package miaosha.redis.key;

public interface KeyPrefix {

	/**
	 * 有效时间
	 * @return
	 */
	public int expireSeconds();
	
	/**
	 * 得到一个key的前缀
	 * @return
	 */
	public String getPrefix();
}
