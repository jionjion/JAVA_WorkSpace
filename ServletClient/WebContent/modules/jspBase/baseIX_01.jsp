<%@ page language="java" contentType="text/html; charset=UTF-8" errorPage="baseIX_02.jsp"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>exception内置对象</title>
</head>  
<body>
	<h1>创建异常</h1>
	<%=1/0 %>
</body>
</html>