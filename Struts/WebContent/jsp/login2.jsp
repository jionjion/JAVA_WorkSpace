<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1 style="text-align: center;">登录系统</h1>
	<form action="loginAction2.action" method="post"><br>
		用户:<input type="text" name="user.username"><s:fielderror name="username"/>
		密码:<input type="password" name="user.password">
		年龄:<input type="text" name="user.age">
		<input type="submit" value="提交">
	</form>  
</body>
</html>