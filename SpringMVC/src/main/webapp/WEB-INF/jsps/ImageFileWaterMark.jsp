<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				<td><img src="${imageInfo.notWaterMarkUrl}"></td>
				<td><img src="${imageInfo.withWaterMarkUrl}"></td>
			</tr>
		</tbody>
	</table>
</body>
</html>