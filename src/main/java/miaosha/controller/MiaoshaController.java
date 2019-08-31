package miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import miaosha.dao.domain.MiaoshaOrder;
import miaosha.dao.domain.OrderInfo;
import miaosha.dao.domain.User;
import miaosha.redis.service.RedisService;
import miaosha.result.Result;
import miaosha.service.GoodsService;
import miaosha.service.MiaoshaService;
import miaosha.service.OrderService;
import miaosha.service.UserService;
import miaosha.vo.goods.GoodsVo;

/**
 * 秒杀功能
 * @author kaifeng1
 *
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

	@Autowired
	UserService userService;

	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	MiaoshaService miaoshaService;

	@RequestMapping("/do_miaosha")
	public String list(Model model, User user, @RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			//没有session，回到登录页面
			return "login";
		}
		// 判断库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if (stock <= 0) {
			model.addAttribute("errmsg", Result.error(500500, false));
			return "miaosha_fail";
		}
		// 判断是否有重复的秒杀商品
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			model.addAttribute("errmsg", Result.error(500501, false));
			return "miaosha_fail";
		}
		// 减库存 下订单 写入秒杀订单
		OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}
}
