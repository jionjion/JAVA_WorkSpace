<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1 style="text-align: center;">登录管理界面</h1>
	<a href="url">点击跳转</a>
	
	<hr/>
	<form action="privilegeManagementAction" method="post">
		用户:<input type="text" name="user.username"><br>
		密码:<input type="password" name="user.password"><br>
		${loginError} 
		<input type="submit" value="登陆">
	</form>
</body>
</html>