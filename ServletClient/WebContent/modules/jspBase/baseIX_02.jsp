<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"
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
	<h1>异常处理页面设置为当前页面处理</h1>
	<p>	出错页面设置errorPage="处理页面"<br>
		处理页面设置isErrorPage="true"</p>
	异常消息是:<%=exception.getMessage() %><br>
	异常的描述:<%=exception.toString() %>
</body>
</html>