package miaosha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miaosha.dao.domain.OrderInfo;
import miaosha.dao.domain.User;
import miaosha.service.GoodsService;
import miaosha.service.MiaoshaService;
import miaosha.service.OrderService;
import miaosha.vo.goods.GoodsVo;

/**
 * 秒杀服务
 * @author kaifeng1
 *
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {
	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	public OrderInfo miaosha(User user, GoodsVo goods) {
		// 减库存 下订单 写入秒杀订单
		goodsService.reduceStock(goods);
		// order_info maiosha_order
		return orderService.createOrder(user, goods);
	}

}
