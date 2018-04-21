package miaosha.redis.key;

public abstract class AbstractKeyPrefix implements KeyPrefix{

	private int expireSeconds ;
	
	private String prefix ;
	
	public AbstractKeyPrefix(int expireSeconds , String prefix) {
		this.expireSeconds  = expireSeconds ;
		this.prefix = prefix ;
	}
	public AbstractKeyPrefix(String prefix) {
		//默认0 代表永不过期
		this(0,prefix);
	}
     public int expireSeconds() {
		return expireSeconds;
	}
	public String getPrefix() {
		String name = this.getClass().getSimpleName();
		return name+":"+prefix;
	}
	
	
}
