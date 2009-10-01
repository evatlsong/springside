package org.springside.modules.integration.log;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.jmx.JmxClientTemplate;
import org.springside.modules.log.Log4jMBean;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * sprinside-jee中Log4jMBean的测试用例.
 * 
 * JMX相关用例使用相同的@ContextConfiguration以保证使用同一个ApplicationContext,避免JMX端口重复注册.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/jmx/applicationContext-jmx-server.xml", "/jmx/applicationContext-jmx-client.xml",
		"/log/applicationContext-log.xml" })
public class Log4jMBeanTest extends SpringContextTestCase {

	private JmxClientTemplate jmxClientTemplate;

	@Before
	public void setUp() throws Exception {
		jmxClientTemplate = new JmxClientTemplate("service:jmx:rmi:///jndi/rmi://localhost:1098/showcase");
	}

	@After
	public void tearDown() throws Exception {
		jmxClientTemplate.close();
	}

	@Test
	public void accessRootLoggerLevel() {
		String oldLevel = "WARN";
		String newLevel = "ERROR";

		//判断原级别
		assertEquals(oldLevel, jmxClientTemplate.getAttribute(Log4jMBean.LOG4J_MBEAN_NAME, "RootLoggerLevel"));
		//设定新级别
		jmxClientTemplate.setAttribute(Log4jMBean.LOG4J_MBEAN_NAME, "RootLoggerLevel", newLevel);
		assertEquals(newLevel, Logger.getRootLogger().getLevel().toString());
		//恢复原级别
		jmxClientTemplate.setAttribute(Log4jMBean.LOG4J_MBEAN_NAME, "RootLoggerLevel", oldLevel);
	}

	@Test
	public void accessLoggerLevel() {
		String loggerName = "foo";
		String oldLevel = "WARN";
		String newLevel = "ERROR";

		//判断原级别
		assertEquals(oldLevel, jmxClientTemplate.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "getLoggerLevel",
				new Class[] { String.class }, new Object[] { loggerName }));
		//设定新级别
		jmxClientTemplate.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "setLoggerLevel", new Class[] { String.class, String.class },
				new Object[] { loggerName, newLevel });
		assertEquals(newLevel, Logger.getLogger(loggerName).getEffectiveLevel().toString());
		//恢复原级别
		jmxClientTemplate.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "setLoggerLevel", new Class[] { String.class, String.class },
				new Object[] { loggerName, oldLevel });
	}

	@Test
	public void getLoggerAppenders() {
		List<String> list = jmxClientTemplate.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "getLoggerAppenders",
				new Class[] { String.class }, new Object[] { "org.springside" });

		assertEquals(2, list.size());
		assertEquals("stdout(parent)", list.get(0));
		assertEquals("logfile(parent)", list.get(1));

		list = jmxClientTemplate.invoke(Log4jMBean.LOG4J_MBEAN_NAME, "getLoggerAppenders", new Class[] { String.class },
				new Object[] { "DBLogExample" });

		assertEquals(2, list.size());
		assertEquals("stdout", list.get(0));
		assertEquals("dblog", list.get(1));
	}
}
