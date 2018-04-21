package miaosha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miaosha.dao.UserDao;
import miaosha.dao.domain.User;
import miaosha.service.UserService;

/**
 * service
 * @author kaifeng1
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao ;
	public User findUserById(int id) {
		return this.userDao.findUserById(id);
	}
	
	@Transactional
	public int insert(User user) {
		return this.userDao.insert(user);
	}

}
