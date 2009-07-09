package org.springside.modules.test.spring;

import java.lang.reflect.Method;

import org.junit.internal.runners.InitializationError;
import org.junit.runner.notification.RunNotifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springside.modules.test.groups.GroupsUtils;

/**
 * 实现TestNG Groups功能的Runner.
 * 
 * @author freeman
 * @author calvin
 */
public class SpringGroupsTestRunner extends SpringJUnit4ClassRunner {

	public SpringGroupsTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * 重载加入类级别过滤.
	 */
	@Override
	public void run(RunNotifier notifier) {
		if (!GroupsUtils.isTestClassInGroups(getTestClass().getJavaClass())) {
			notifier.fireTestIgnored(getDescription());
			return;
		}
		super.run(notifier);
	}

	/**
	 * 重载加入方法级别过滤.
	 */
	@Override
	protected void invokeTestMethod(Method method, RunNotifier notifier) {

		if (!GroupsUtils.isTestMethodInGroups(method)) {
			notifier.fireTestIgnored(getDescription());
			return;
		}
		super.invokeTestMethod(method, notifier);
	}
}
