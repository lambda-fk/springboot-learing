package miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import miaosha.dao.domain.User;
import miaosha.redis.key.space.GoodsKey;
import miaosha.redis.service.RedisService;
import miaosha.redis.service.proxy.RedisServiceProxy;
import miaosha.result.Result;
import miaosha.service.GoodsService;
import miaosha.service.UserService;
import miaosha.util.ConstValue;
import miaosha.vo.goods.GoodsDetailVo;
import miaosha.vo.goods.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private UserService userService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	RedisService redisService;

	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 不用WebConfig 进行的参数的获取 User，缺点每一个请求都要这么写
	 * 
	 * @param response
	 * @param model
	 * @param cookieUUid
	 * @param paramUuid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toListOldSample")
	public String toListOldSample(HttpServletResponse response, Model model,
			@CookieValue(value = ConstValue.COOKI_NAME_TOKEN, required = false) String cookieUUid,
			@RequestParam(value = ConstValue.COOKI_NAME_TOKEN, required = false) String paramUuid) throws Exception {
		String uuid = null;
		if (cookieUUid != null) {
			uuid = cookieUUid;
		} else if (paramUuid != null) {
			uuid = paramUuid;
		} else {
			// uuid丢失了重新到登录页
			return "login";
		}
		User user = this.userService.getByToken(response, uuid);
		model.addAttribute("user", user);
		return "goods_list";
	}

	/**
	 * 使用WebConfig,我们做了这个UserArgumentResolver之后的简洁处理,user可以像Model一样传入进去
	 * 
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("/toList")
	public String toList(Model model, User user) {
		if (user == null) {
			return "login";
		}
		model.addAttribute("user", user);
		// 查询商品信息
		List<GoodsVo> goodsList = goodsService.listGoods();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";

	}

	/**
	 * 使用页面缓存技术
	 * 
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toListCache", produces = "text/html")
	@ResponseBody
	public String toListWithPageCache(HttpServletRequest request, HttpServletResponse response, Model model, User user)
			throws Exception {
		if (user == null) {
			return "login";
		}
		model.addAttribute("user", user);
		// 获取页面缓存
		RedisServiceProxy proxy = new RedisServiceProxy(GoodsKey.GOODS_LIST, redisService);
		String html = proxy.getBean("", String.class);
		if (!StringUtils.isEmpty(html)) {
			return html;
		}
		// 查询商品信息
		List<GoodsVo> goodsList = goodsService.listGoods();
		model.addAttribute("goodsList", goodsList);
		// 页面缓存的核心东西,渲染页面
		SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(),
				model.asMap(), applicationContext);
		// 手动渲染
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
		if (!StringUtils.isEmpty(html)) {
			proxy.setBean("", html);
		}

		return html;

	}

	/**
	 * 没有使用缓存的例子
	 * 
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/to_detail/{goodsId}")
	public String detail(Model model, User user, @PathVariable("goodsId") Long goodsId) {

		model.addAttribute("user", user);
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if (now < startAt) {// 秒杀还没开始，倒计时
			miaoshaStatus = 0;
			// 单位是毫秒
			remainSeconds = (int) ((startAt - now) / 1000);
		} else if (now > endAt) {// 秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		} else {// 秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
	}

	/**
	 * 使用缓存的例子
	 * 
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/to_detailCache/{goodsId}", produces = "text/html")
	@ResponseBody
	public String detailWithCache(HttpServletRequest request, HttpServletResponse response, Model model, User user,
			@PathVariable("goodsId") Long goodsId) throws Exception {

		model.addAttribute("user", user);
		// 获取页面缓存
		RedisServiceProxy proxy = new RedisServiceProxy(GoodsKey.GOODS_DETAIL, redisService);
		String html = proxy.getBean("" + goodsId, String.class);
		if (!StringUtils.isEmpty(html)) {
			return html;
		}

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if (now < startAt) {// 秒杀还没开始，倒计时
			miaoshaStatus = 0;
			// 单位是毫秒
			remainSeconds = (int) ((startAt - now) / 1000);
		} else if (now > endAt) {// 秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		} else {// 秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(),
				model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);

		if (!StringUtils.isEmpty(html)) {
			proxy.setBean("" + goodsId, html);
		}

		return html;
	}

	/**
	 * 页面静态化的例子使用
	 * 
	 * @param
	 * @param user
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailStatic/{goodsId}")
	@ResponseBody
	public Result<GoodsDetailVo> detailWithCache(HttpServletRequest request, HttpServletResponse response, User user,
			@PathVariable("goodsId") Long goodsId) throws Exception {

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if (now < startAt) {// 秒杀还没开始，倒计时
			miaoshaStatus = 0;
			// 单位是毫秒
			remainSeconds = (int) ((startAt - now) / 1000);
		} else if (now > endAt) {// 秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		} else {// 秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}

		GoodsDetailVo vo = new GoodsDetailVo();
		vo.setGoods(goods);
		vo.setUser(user);
		vo.setRemainSeconds(remainSeconds);
		vo.setMiaoshaStatus(miaoshaStatus);
		return Result.sucess(vo);
	}

}
