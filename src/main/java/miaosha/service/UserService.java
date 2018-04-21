package miaosha.service;

import miaosha.dao.domain.User;


public interface UserService {

	public User findUserById(int id) ;
	
	public int insert(User user);

}
