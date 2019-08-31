package miaosha.service;

import miaosha.dao.domain.MiaoshaOrder;
import miaosha.dao.domain.OrderInfo;
import miaosha.dao.domain.User;
import miaosha.vo.goods.GoodsVo;

public interface OrderService {

	/**
	 * 获取秒杀订单
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId);
	
	/**
	 * 生成订单
	 * @param user
	 * @param goods
	 * @return
	 */
	public OrderInfo createOrder(User user, GoodsVo goods) ;
}
