<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JavaBean的使用02</title>
</head>
<body>

	<h1>登录传参</h1>
	<form action="<%=basePath %>modules/jspBase/baseX_03.jsp" method="post">
		用户:<input type="text" name="username"><br>
		密码:<input type="password" name="password"><br>
		<input type="submit" value="提交">
	</form>
</body>
</html>