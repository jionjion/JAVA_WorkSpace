<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jspBase.bean.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JavaBean的使用01</title>
</head>
<body>

	<h1>使用普通方式调用</h1>
	<%
		User user = new User();
		user.setUsername("Jion");
		user.setPassword("123456");
	%>
	用户:<%=user.getUsername() %><br>
	密码:<%=user.getPassword() %><br>
	

	<h1>使用useBean动作标签完成</h1>
	<jsp:useBean id="myUser" type="jspBase.bean.User" class="jspBase.bean.User" scope="page"/>
	用户:<%=user.getUsername() %><br>
	密码:<%=user.getPassword() %><br>
</body>
</html>