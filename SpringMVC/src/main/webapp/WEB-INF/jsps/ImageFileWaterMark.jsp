<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  isELIgnored="false"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加水印</title>
</head>
<body>
	<h1>水印图片页面</h1>
	<table>
		<thead>
			<tr>
				<th>原图</th>
				<th>水印</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><img src="${pageContext.request.contextPath}/${imageInfo.notWaterMarkUrl}"></td>
				<!-- 使用项目路径 + 相对路径,返回temp目录下的附件信息 -->
				<td><img src="${pageContext.request.contextPath}/${imageInfo.withWaterMarkUrl}"></td>
			</tr>
		</tbody>
	</table>
</body>
</html>