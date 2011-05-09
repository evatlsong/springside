package org.springside.examples.miniweb.functional.account;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springside.modules.test.utils.DataUtils;
import org.springside.modules.test.utils.SeleniumUtils;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseFunctionalTestCase {

	private static User testUser = null;

	/**
	 * 检验OverViewPage.
	 */
	@Test
	@Groups(DAILY)
	public void overviewPage() {
		driver.findElement(By.linkText(Gui.MENU_USER)).click();
		WebElement table = driver.findElement(By.xpath("//table[@id='contentTable']"));
		assertEquals("admin", SeleniumUtils.getTable(table, 1, UserColumn.LOGIN_NAME.ordinal()));
		assertEquals("Admin", SeleniumUtils.getTable(table, 1, UserColumn.NAME.ordinal()));
		assertEquals("管理员, 用户", SeleniumUtils.getTable(table, 1, UserColumn.GROUPS.ordinal()));
	}

	/**
	 * 创建公共测试用户.
	 */
	@Test
	@Groups(DAILY)
	public void createUser() {
		//打开新增用户页面
		driver.findElement(By.linkText(Gui.MENU_USER)).click();
		driver.findElement(By.linkText("增加新用户")).click();

		//生成待输入的测试用户数据
		User user = AccountData.getRandomUserWithGroup();

		//输入数据
		driver.findElement(By.id("loginName")).sendKeys(user.getLoginName());
		driver.findElement(By.id("name")).sendKeys(user.getName());
		driver.findElement(By.id("password")).sendKeys(user.getPassword());
		driver.findElement(By.id("passwordConfirm")).sendKeys(user.getPassword());
		for (Group group : user.getGroupList()) {
			driver.findElement(By.id("checkedGroupIds-" + group.getId())).setSelected();
		}
		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		//校验结果
		assertTrue(SeleniumUtils.isTextPresent(driver, "保存用户成功"));
		verifyUser(user);

		//设置公共测试用户
		testUser = user;
	}

	/**
	 * 修改公共测试用户.
	 */
	@Test
	@Groups(DAILY)
	public void editUser() {
		//确保已创建公共测试用户.
		ensureTestUserExist();

		//打开公共测试用户修改页面.
		driver.findElement(By.linkText(Gui.MENU_USER)).click();
		searchUser(testUser.getLoginName());
		driver.findElement(By.linkText("修改")).click();

		//更改用户名
		testUser.setName(DataUtils.randomName("User"));
		SeleniumUtils.type(driver.findElement(By.id("name")), testUser.getName());

		//取消所有權限組
		for (Group group : testUser.getGroupList()) {
			SeleniumUtils.uncheck(driver.findElement(By.id("checkedGroupIds-" + group.getId())));
		}
		testUser.getGroupList().clear();

		//增加一个權限組
		Group group = AccountData.getRandomDefaultGroup();
		driver.findElement(By.id("checkedGroupIds-" + group.getId())).setSelected();
		testUser.getGroupList().add(group);

		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		//校验结果
		assertTrue(SeleniumUtils.isTextPresent(driver, "保存用户成功"));
		verifyUser(testUser);
	}

	/**
	 * 删除公共用户.
	 */
	@Test
	@Groups(NIGHTLY)
	public void deleteUser() {
		//确保已创建公共测试用户.
		ensureTestUserExist();

		//查找公共测试用户
		driver.findElement(By.linkText(Gui.MENU_USER)).click();
		searchUser(testUser.getLoginName());

		//删除用户
		driver.findElement(By.linkText("删除")).click();

		//校验结果
		assertTrue(SeleniumUtils.isTextPresent(driver, "删除用户成功"));
		searchUser(testUser.getLoginName());
		assertFalse(StringUtils.contains(driver.findElement(By.id("contentTable")).getText(), testUser.getLoginName()));

		//清空公共测试用户
		testUser = null;
	}

	/**
	 * 创建用户时的输入校验测试. 
	 */
	@Test
	@Groups(NIGHTLY)
	public void inputValidateUser() {
		driver.findElement(By.linkText(Gui.MENU_USER)).click();
		driver.findElement(By.linkText("增加新用户")).click();

		driver.findElement(By.id("loginName")).sendKeys("admin");
		driver.findElement(By.id("name")).sendKeys("");
		driver.findElement(By.id("password")).sendKeys("a");
		driver.findElement(By.id("passwordConfirm")).sendKeys("abc");
		driver.findElement(By.id("email")).sendKeys("abc");

		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		WebElement table = driver.findElement(By.xpath("//form[@id='inputForm']/table"));
		assertEquals("用户登录名已存在", SeleniumUtils.getTable(table, 0, 1));
		assertEquals("必选字段", SeleniumUtils.getTable(table, 1, 1));
		assertEquals("请输入一个长度最少是 3 的字符串", SeleniumUtils.getTable(table, 2, 1));
		assertEquals("输入与上面相同的密码", SeleniumUtils.getTable(table, 3, 1));
		assertEquals("请输入正确格式的电子邮件", SeleniumUtils.getTable(table, 4, 1));
	}

	/**
	 * 根据用户登录名查找用户的工具函数. 
	 */
	private void searchUser(String loginName) {
		SeleniumUtils.type(driver.findElement(By.name("filter_EQS_loginName")), loginName);
		driver.findElement(By.xpath(Gui.BUTTON_SEARCH)).click();
	}

	/**
	 * 校验用户数据的工具函数.
	 */

	private void verifyUser(User user) {
		searchUser(user.getLoginName());
		driver.findElement(By.linkText("修改")).click();

		assertEquals(user.getLoginName(), driver.findElement(By.id("loginName")).getValue());
		assertEquals(user.getName(), driver.findElement(By.id("name")).getValue());

		for (Group group : user.getGroupList()) {
			assertTrue(driver.findElement(By.id("checkedGroupIds-" + group.getId())).isSelected());
		}

		List<Group> uncheckGroupList = ListUtils.subtract(AccountData.getDefaultGroupList(), user.getGroupList());
		for (Group group : uncheckGroupList) {
			assertFalse(driver.findElement(By.id("checkedGroupIds-" + group.getId())).isSelected());
		}
	}

	/**
	 * 确保公共测试用户已初始化的工具函数.
	 */
	private void ensureTestUserExist() {
		if (testUser == null) {
			createUser();
		}
	}
}
