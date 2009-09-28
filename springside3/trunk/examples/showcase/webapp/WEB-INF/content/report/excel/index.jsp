<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报表演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/report/swfobject.js"></script>
<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>Excel导出演示</h3>
<p>说明：演示基于POI的Excel操作。</p>
<ul>
<li><a href="excel-export.action">导出Excel文件</a><br/>
演示冻结/合并单元格, 单元格字体/边框/颜色, 单元格数值格式/公式等特性.</li>
<li>Excel文件读取<br/>见ExcelExportActionTest测试用例.</li>
</ul>
</div>
</div>
</body>
</html>