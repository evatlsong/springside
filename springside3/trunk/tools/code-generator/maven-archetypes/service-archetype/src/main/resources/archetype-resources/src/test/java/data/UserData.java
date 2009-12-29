#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.data;

import ${package}.entity.user.Role;
import ${package}.entity.user.User;
import org.springside.modules.test.DataUtils;

/**
 * 用户测试数据生成.
 * 
 * @author calvin
 */
public class UserData {

	public static User getRandomUser() {
		String userName = DataUtils.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("123456");
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static User getRandomUserWithAdminRole() {
		User user = UserData.getRandomUser();
		Role adminRole = UserData.getAdminRole();
		user.getRoleList().add(adminRole);
		return user;
	}

	public static Role getAdminRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		return role;
	}
}
