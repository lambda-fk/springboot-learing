package miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import miaosha.dao.domain.User;
import miaosha.redis.key.space.UserKey;
import miaosha.redis.service.RedisService;
import miaosha.redis.service.proxy.RedisServiceProxy;
import miaosha.result.Result;
import miaosha.service.UserService;
import miaosha.util.JsonCom;

@Controller
@RequestMapping("/jedis")
public class JedisSampleController {

	@Autowired
	private RedisService redisService ;
	
	@Autowired
	private UserService userService ;
	
	@RequestMapping("/saveUserRedisProxy")
	@ResponseBody
	Result<String> hello() throws Exception {
		User user = this.userService.findUserById(2L);
		RedisServiceProxy proxy = new RedisServiceProxy(UserKey.ID,redisService);
		proxy.setBean("user", user);
		User u = proxy.getBean("user", User.class);
		return Result.sucess(JsonCom.beanToJson(u));
	}
	
}
