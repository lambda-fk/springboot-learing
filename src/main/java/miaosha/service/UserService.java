package miaosha.service;

import javax.servlet.http.HttpServletResponse;

import miaosha.dao.domain.User;
import miaosha.vo.login.LoginVo;


public interface UserService {

	public User findUserById(Long id) ;
	
	/**
	 * 根据session的token获取到user
	 * @param response
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	public User getByToken(HttpServletResponse response, String token) throws Exception ;
	
	public int insert(User user);
	
	public boolean login(HttpServletResponse response, LoginVo loginVo) throws Exception ;

	void addCookie(HttpServletResponse response, String token, User user) throws Exception;
}
