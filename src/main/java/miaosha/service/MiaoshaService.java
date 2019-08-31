package miaosha.service;

import miaosha.dao.domain.OrderInfo;
import miaosha.dao.domain.User;
import miaosha.vo.goods.GoodsVo;

public interface MiaoshaService {

	/**
	 * 生成秒杀订单
	 * @param user
	 * @param goods
	 * @return
	 */
	public OrderInfo miaosha(User user, GoodsVo goods) ;
}
