package org.springside.examples.miniweb.functional.account;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.functional.BaseFunctionalTestCase;
import org.springside.examples.miniweb.functional.Gui;
import org.springside.examples.miniweb.functional.Gui.UserColumn;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.utils.ThreadUtils;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseFunctionalTestCase {

	/**
	 * 检验ListPage.
	 */
	@Test
	@Groups(DAILY)
	public void listPage() {
		selenium.clickTo(By.linkText(Gui.MENU_USER));
		WebElement table = selenium.findElement(By.xpath("//table[@id='contentTable']"));
		assertEquals("admin", selenium.getTable(table, 1, UserColumn.LOGIN_NAME.ordinal()));
		assertEquals("Admin", selenium.getTable(table, 1, UserColumn.NAME.ordinal()));
		assertEquals("管理员, 用户", selenium.getTable(table, 1, UserColumn.GROUPS.ordinal()));
	}

	/**
	 * 创建用户.
	 */
	@Test
	@Groups(DAILY)
	public void createUser() {
		//打开新增用户页面
		selenium.clickTo(By.linkText(Gui.MENU_USER));
		selenium.clickTo(By.linkText("增加新用户"));

		//生成待输入的测试用户数据
		User user = AccountData.getRandomUserWithGroup();

		//输入数据
		selenium.type(By.id("loginName"), user.getLoginName());
		selenium.type(By.id("name"), user.getName());
		selenium.type(By.id("password"), user.getPassword());
		selenium.type(By.id("passwordConfirm"), user.getPassword());
		for (Group group : user.getGroupList()) {
			selenium.check(By.id("checkedGroupIds-" + group.getId()));
		}
		selenium.clickTo(By.xpath(Gui.BUTTON_SUBMIT));

		//校验结果
		assertTrue(selenium.isTextPresent("保存用户成功"));
		verifyUser(user);
	}

	/**
	 * 校验用户数据的工具函数.
	 */
	private void verifyUser(User user) {
		selenium.type(By.name("filter_EQS_loginName"), user.getLoginName());
		selenium.clickTo(By.xpath(Gui.BUTTON_SEARCH));
		selenium.clickTo(By.linkText("修改"));

		assertEquals(user.getLoginName(), selenium.getValue(By.id("loginName")));
		assertEquals(user.getName(), selenium.getValue(By.id("name")));

		for (Group group : user.getGroupList()) {
			assertTrue(selenium.isChecked(By.id("checkedGroupIds-" + group.getId())));
		}

		List<Group> uncheckGroupList = ListUtils.subtract(AccountData.getDefaultGroupList(), user.getGroupList());
		for (Group group : uncheckGroupList) {
			assertFalse(selenium.isChecked(By.id("checkedGroupIds-" + group.getId())));
		}
	}

	/**
	 * 创建用户时的输入校验测试. 
	 */
	@Test
	@Groups(NIGHTLY)
	public void inputValidateUser() {
		selenium.clickTo(By.linkText(Gui.MENU_USER));
		selenium.clickTo(By.linkText("增加新用户"));

		selenium.type(By.id("loginName"), "admin");
		selenium.type(By.id("name"), "");
		selenium.type(By.id("password"), "a");
		selenium.type(By.id("passwordConfirm"), "abc");
		selenium.type(By.id("email"), "abc");

		selenium.clickTo(By.xpath(Gui.BUTTON_SUBMIT));

		ThreadUtils.sleep(2000);

		WebElement table = selenium.findElement(By.xpath("//form/table"));
		assertEquals("用户登录名已存在", selenium.getTable(table, 0, 1));
		assertEquals("必选字段", selenium.getTable(table, 1, 1));
		assertEquals("请输入一个长度最少是 3 的字符串", selenium.getTable(table, 2, 1));
		assertEquals("输入与上面相同的密码", selenium.getTable(table, 3, 1));
		assertEquals("请输入正确格式的电子邮件", selenium.getTable(table, 4, 1));
	}

}
