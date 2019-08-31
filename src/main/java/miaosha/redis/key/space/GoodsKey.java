package miaosha.redis.key.space;

import miaosha.redis.key.AbstractKeyPrefix;

public class GoodsKey extends AbstractKeyPrefix{

	private GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static GoodsKey GOODS_LIST = new GoodsKey(60, "gl");
	public static GoodsKey GOODS_DETAIL = new GoodsKey(60, "gd");
}
