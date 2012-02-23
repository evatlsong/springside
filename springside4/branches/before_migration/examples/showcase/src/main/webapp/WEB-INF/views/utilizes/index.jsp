<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>邮件演示</title>
</head>

<body>
	<h2>XML/JSON操作演示</h2>

	<h3>技术说明：</h3>
	<ul>
		<li>基于JAXB2.0的Java-XML绑定, 演示根元素是List, 属性是List与Map等特殊情况.</li>
		<li>万能老倌Dom4j.</li>
		<li>性能最快功能也最丰富的JSON转换类库Jackson.</li>
	</ul>

	<h3>用户故事：</h3>
	<ul>
		<li>在JaxbDemo.java中演示XML与Java对象的转换及Dom4j的使用.</li>
		<li>在JsonDemo.java中演示Jackson远超同类JSON库的转化能力.</li>
	</ul>

	<h2>Excel演示</h2>

	<p>说明：演示基于POI的Excel操作。</p>
	<ul>
		<li><a href="/showcase/excel/export">导出Excel文件</a><br /> 演示冻结/合并单元格, 单元格字体/边框/颜色, 单元格数值格式/公式等特性.</li>
		<li>读取Excel文件<br />见ExcelExportActionTest测试用例.</li>
	</ul>
	
	<h2>Email演示</h2>

	<h3>技术说明:</h3>
	<ul>
		<li>简单文本邮件演示.</li>
		<li>带附件的MIME邮件演示, 使用Freemarker模板创建HTML内容.</li>
	</ul>

	<h3>用户故事：</h3>
	<p>
		在综合演示用例中保存用户时，发送两种邮件.<br /> 演示邮箱名为springside3.demo@gmail.com, 密码为demoforyou.
	</p>
	
	
	<h2>日志演示</h2>

	<h3>技术说明：</h3>
	<ul>
		<li>Log4JMBean: 通过JMX动态查询与改变Logger的日志等级与Appender.</li>
		<li>MockLog4jAppender: 在测试用例中验证日志的输出.</li>
	</ul>

	<h3>用户故事：</h3>
	<ul>
		<li>使用JConsole动态修改log4j的日志等级.(路径service:jmx:rmi:///jndi/rmi://localhost:2099/jmxrmi,名称Log4j:name=log4j)</li>
		<li>Schedule测试用例使用MockAppender校验日志输出.</li>
	</ul>
</body>
</html>