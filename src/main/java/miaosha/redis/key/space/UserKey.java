package miaosha.redis.key.space;

import miaosha.redis.key.AbstractKeyPrefix;

public class UserKey extends AbstractKeyPrefix{

	public UserKey(String prefix) {
		super(prefix);
	}
	
	public final static UserKey ID = new UserKey("id");
	public final static UserKey NAME = new UserKey("name");
}
