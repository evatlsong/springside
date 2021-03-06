<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>日志高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>日志高级演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>Log4JMBean: 通过JMX动态查询与改变Logger的日志等级与Appender.</li>
			<li>QueueAppender/JdbcLogTask:轻量级的日志异步数据库写入框架, 可用于业务日志写入.</li>
			<li>MockAppender: 在测试用例中验证日志的输出.</li>
			<li>TraceUtils: 输出方便跟踪问题的业务系统运行信息.</li>
		</ul>
		
		<h2>用户故事：</h2>
		<ul>
			<li>使用JConsole动态修改log4j的日志等级.(路径service:jmx:rmi:///jndi/rmi://localhost:1099/showcase,名称SpringSide:type=Log4jManagement)</li>
			<li>Schedule测试用例使用MockAppender校验日志输出.</li>
			<li>UserWebService服务通过TraceLogAspect, 使用TraceUtils打印Trace信息.</li>
			<li>每次进入本页面, logger都会在数据库LOGS表中增加一条记录.</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>