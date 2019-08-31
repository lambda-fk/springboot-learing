package miaosha.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miaosha.dao.UserDao;
import miaosha.dao.domain.User;
import miaosha.exception.ApplicationException;
import miaosha.redis.key.space.UserKey;
import miaosha.redis.service.RedisService;
import miaosha.redis.service.proxy.RedisServiceProxy;
import miaosha.service.UserService;
import miaosha.util.ConstValue;
import miaosha.util.Md5Util;
import miaosha.util.UUIDUtil;
import miaosha.vo.login.LoginVo;
import org.springframework.util.StringUtils;

/**
 * service
 * 
 * @author kaifeng1
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	RedisService redisService;

	public User findUserById(Long id) {
		return this.userDao.findUserById(id);
	}

	@Transactional
	public int insert(User user) {
		return this.userDao.insert(user);
	}

	/**
	 * 登录系统
	 * @throws Exception 
	 */
	public boolean login(HttpServletResponse response, LoginVo loginVo) throws Exception {
		if (loginVo == null) {
			throw ApplicationException.SERVER_ERROR;
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		User user = this.findUserById(Long.parseLong(mobile));
		if (user == null) {
			throw ApplicationException.MOBILE_NOT_EXIST;
		}
		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = Md5Util.formPassToDBPass(formPass, saltDB);
		if (!calcPass.equals(dbPass)) {
			throw ApplicationException.PASSWORD_ERROR;
		}
		// 生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return true;
	}

	/**
	 * 将用户保存在redis中
	 */
	public void addCookie(HttpServletResponse response, String token, User user) throws Exception {
		RedisServiceProxy proxy = new RedisServiceProxy(UserKey.TOKEN,this.redisService);
		proxy.setBean(token, user);
		Cookie cookie = new Cookie(ConstValue.COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(UserKey.TOKEN.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public User getByToken(HttpServletResponse response, String token) throws Exception {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		RedisServiceProxy proxy = new RedisServiceProxy(UserKey.TOKEN,this.redisService);
		User user = proxy.getBean(token, User.class);
		if (user != null) {
			//延长cookie
			addCookie(response, token, user);
		}
		return user;
	}

}
