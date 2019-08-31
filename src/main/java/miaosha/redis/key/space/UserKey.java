package miaosha.redis.key.space;

import miaosha.redis.key.AbstractKeyPrefix;

public class UserKey extends AbstractKeyPrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private UserKey(String prefix) {
		super(prefix);
	}
	private UserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	
	public final static UserKey ID = new UserKey("id");
	public final static UserKey NAME = new UserKey("name");
	//token
	public final static UserKey TOKEN = new UserKey(TOKEN_EXPIRE, "tk");
}
