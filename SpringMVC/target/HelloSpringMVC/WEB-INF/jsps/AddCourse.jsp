<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>添加课程</h1>
	<form action="<%=request.getContextPath()%>/course/save" method="post">
		课程编号:<input type="text" name="id"><br>
		课程名称:<input type="text" name="name"><br>
		<input type="submit" value="提交">
	</form> 
</body>
</html>