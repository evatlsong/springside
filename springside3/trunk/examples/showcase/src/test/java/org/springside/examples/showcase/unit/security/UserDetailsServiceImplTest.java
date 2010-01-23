package org.springside.examples.showcase.unit.security;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springside.examples.showcase.common.entity.Role;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.examples.showcase.security.OperatorDetails;
import org.springside.examples.showcase.security.UserDetailsServiceImpl;
import org.springside.modules.utils.ReflectionUtils;

public class UserDetailsServiceImplTest extends Assert {

	private UserDetailsService userDetailsService;
	private UserManager mockUserManager;

	@Before
	public void setUp() {
		userDetailsService = new UserDetailsServiceImpl();
		mockUserManager = EasyMock.createMock(UserManager.class);
		ReflectionUtils.setFieldValue(userDetailsService, "userManager", mockUserManager);
	}

	@After
	public void tearDown() {
		EasyMock.verify(mockUserManager);
	}

	@Test
	public void loadUserExist() {

		//准备数据
		User user = new User();
		user.setLoginName("admin");
		user.setShaPassword(new ShaPasswordEncoder().encodePassword("admin", null));
		Role role1 = new Role();
		role1.setName("admin");
		Role role2 = new Role();
		role2.setName("user");
		user.getRoleList().add(role1);
		user.getRoleList().add(role2);

		//录制脚本
		EasyMock.expect(mockUserManager.findUserByLoginName("admin")).andReturn(user);
		EasyMock.replay(mockUserManager);

		//执行测试
		OperatorDetails operator = (OperatorDetails) userDetailsService.loadUserByUsername(user.getLoginName());

		//校验结果
		assertEquals(user.getLoginName(), operator.getUsername());
		assertEquals(new ShaPasswordEncoder().encodePassword("admin", null), operator.getPassword());
		assertEquals(2, operator.getAuthorities().size());
		assertEquals(new GrantedAuthorityImpl("ROLE_admin"), operator.getAuthorities().iterator().next());
		assertNotNull(operator.getLoginTime());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByWrongUserName() {
		//录制脚本
		EasyMock.expect(mockUserManager.findUserByLoginName("foo")).andReturn(null);
		EasyMock.replay(mockUserManager);

		assertNull(userDetailsService.loadUserByUsername("foo"));
	}
}
