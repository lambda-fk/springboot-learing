package miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import miaosha.dao.domain.User;

/**
 * mybatis的dao的设置
 * @author kaifeng1
 *
 */
@Mapper
public interface UserDao {

	@Select("select * from user where id=#{id}")
	public User findUserById(@Param("id") Long id);
	
	/**
	 * 使用输入bean的属性作为输入参数
	 * @param user
	 * @return
	 */
	@Insert("insert into user(id,name) values(#{id} , #{name})")
	public int insert(User user);
}
