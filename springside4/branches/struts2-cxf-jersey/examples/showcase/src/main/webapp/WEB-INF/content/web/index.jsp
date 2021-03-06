<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="org.springside.modules.utils.EncodeUtils" %>

<%
   String remoteImageUrl = "http://"+request.getServerName()+":"+request.getServerPort()+"/showcase/img/logo.jpg";
   String encodedImageUrl = EncodeUtils.urlEncode(remoteImageUrl);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Web高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

	<link href="${ctx}/static-content?contentPath=css/style-min.css" type="text/css" rel="stylesheet"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->	
</head>
<body>
<div class="container">
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div class="span-18 last">
		<h2>Web高级演示</h2>

		<h3>技术说明：</h3>
		<ul>
			<li>高性能Web2.0网站:<br/>
			    1. 静态内容Servlet, 演示高效读取静态内容, 控制客户端缓存, 压缩传输, 弹出下载对话框.<br/>
			 	2. 远程内容Servlet, 演示使用Apache HttpClient多线程高效获取远程网站内容.<br/>
			    3. CacheControlHeaderFilter为静态内容添加缓存控制 Header<br/>
			    4. YUI Compressor 压缩js/css<br/>
			</li>
			<li><a href="${ctx}/web/mashup-client.action">跨域名Mashup演示</a> 演示基于JSONP Mashup 跨域名网站的内容.</li>
		</ul>
		
		<h3>用户故事：</h3>
		<ul>
			<li>静态内容Servlet:<img src="${ctx}/static-content?contentPath=img/logo.jpg"/> <a href="${ctx}/static-content?contentPath=img/logo.jpg&download=true">图片下载链接</a></li>
			<li>远程内容Servlet:<img src="${ctx}/remote-content?contentUrl=<%=encodedImageUrl%>"/></li>
			<li>CacheControlHeaderFilter使用见webapp中的web.xml</li>
			<li>YUI Compressor见bin/yuicompressor.bat命令及webapp中两个版本的js/css文件.</li>
		</ul>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>