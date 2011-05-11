package org.springside.examples.miniservice.functional;

import javax.sql.DataSource;

import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.springside.examples.miniservice.tools.Start;
import org.springside.modules.test.data.Fixtures;
import org.springside.modules.test.functional.JettyFactory;
import org.springside.modules.utils.spring.SpringContextHolder;

/**
 * 功能测试基类.
 * 
 * 在整个测试期间启动一次Jetty Server, 并在每个TestCase执行前重新载入默认数据.
 * 
 * @author calvin
 */
@Ignore
public class BaseFunctionalTestCase {

	protected static final String BASE_URL = Start.BASE_URL;

	protected static Server server;

	protected static DataSource dataSource;

	@BeforeClass
	public static void startAll() throws Exception {
		startJetty();
		loadDefaultData();
	}

	@AfterClass
	public static void stopAll() throws Exception {
		cleanDefaultData();
	}

	/**
	 * 启动Jetty服务器, 仅启动一次.
	 */
	protected static void startJetty() throws Exception {
		if (server == null) {
			server = JettyFactory.buildTestServer(Start.PORT, Start.CONTEXT);
			server.start();
			dataSource = SpringContextHolder.getBean("dataSource");
		}
	}

	/**
	 * 载入默认数据.
	 */
	protected static void loadDefaultData() throws Exception {
		Fixtures.loadData(dataSource, "/data/sample-data.xml");
	}

	/**
	 * 删除默认数据.
	 */
	public static void cleanDefaultData() throws Exception {
		Fixtures.removeData(dataSource, "/data/sample-data.xml");
	}
}
