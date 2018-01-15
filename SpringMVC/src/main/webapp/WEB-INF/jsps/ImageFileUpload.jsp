<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>
</head>
<body>
	<h1>图片上传页面</h1>
	<form action="<%=request.getContextPath()%>/file/waterMark" method="post" enctype="multipart/form-data">
		<input type="file" name="image"/>			<br>
		<select name="markType">
			<option value="text">文本水印</option> 
			<option value="texts">多文字水印</option> 
			<option value="logo">图片水印</option> 
			<option value="logos">多图片水印</option> 
		</select> 									<br>
		<input type="submit" value="上传图片加水印">	<br>
	</form>	
</body>
</html>