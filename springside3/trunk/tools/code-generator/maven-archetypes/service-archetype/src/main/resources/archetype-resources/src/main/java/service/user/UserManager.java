#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.dao.UserDao;
import ${package}.entity.user.User;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户及其相关实体的所有业务管理函数.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager {
	@Autowired
	private UserDao userDao;

	/**
	 * 获取全部用户,并对用户的延迟加载关联进行初始化.
	 */
	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		List<User> userList = userDao.getAll("id", true);
		userDao.initAllProperties(userList);
		return userList;
	}

	public void saveUser(User user) {
		userDao.save(user);
	}

	/**
	 * 验证用户名密码. 
	 * 
	 * @return 验证通过时返回true.用户名或密码错误时返回false.
	 */
	@Transactional(readOnly = true)
	public boolean authenticate(String loginName, String password) {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			return false;
		}

		return ((Long) userDao.findUnique(UserDao.QUERY_BY_LNAME_PASSWD, loginName, password) == 1);
	}
}
