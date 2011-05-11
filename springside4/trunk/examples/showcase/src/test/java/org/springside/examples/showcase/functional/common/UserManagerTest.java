package org.springside.examples.showcase.functional.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;

/**
 * 用户管理的功能测试.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseFunctionalTestCase {

	@Test
	public void editUser() {
		s.open("/");
		s.clickTo(By.linkText("综合演示"));
		s.clickTo(By.id("editLink-2"));

		//修改用户需要登录管理员权限
		s.type(By.name("username"), "admin");
		s.type(By.name("password"), "admin");
		s.clickTo(By.xpath("//input[@value='登录']"));

		//点击提交按钮
		s.type(By.name("name"), "user_foo");
		s.clickTo(By.xpath("//input[@value='提交']"));

		//重新进入用户修改页面, 检查最后修改者
		s.clickTo(By.id("editLink-2"));
		assertEquals("user_foo", s.getValue(By.name("name")));
	}
}
