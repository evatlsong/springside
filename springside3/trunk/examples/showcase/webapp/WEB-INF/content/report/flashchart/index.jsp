<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Flash Chart演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/report/swfobject.js"></script>
<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>Flash Chart演示</h3>
<h4>技术说明：</h4>
<p>使用AmCharts渲染Flash图表</p>
<ul>
<li>曲线图：演示精简版配置文件与CSV格式数据文件。</li>
<li>柱状图：演示完整版配置文件与XML格式数据文件，可点击条柱跳转到跳转到子报表页面。</li>
</ul>
	<div id="report1Content"></div>

	<script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("${ctx}/report/amline.swf", "report1", "640", "420", "8", "#FFFFFF");
		so.addVariable("settings_file", encodeURIComponent("${ctx}/report/report1-settings.xml"));
		so.addVariable("data_file", encodeURIComponent("${ctx}/report/flashchart/report1-data.action"));
		so.write("report1Content");
		// ]]>
	</script>
	
	<div id="report2Content"></div>

	<script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("${ctx}/report/amcolumn.swf", "report2", "640", "420", "8", "#FFFFFF");
		so.addVariable("settings_file", encodeURIComponent("${ctx}/report/report2-settings.xml"));
		so.addVariable("data_file", encodeURIComponent("${ctx}/report/flashchart/report2-data.action"));
		so.write("report2Content");
		// ]]>
	</script>
	
</div>
</div>
</body>
</html>