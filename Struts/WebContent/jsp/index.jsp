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
<title>Insert title here</title>
</head>
<body>
	<h1>这是主页</h1>
	<hr>
	
	<h3>简单的演示</h3>
	<h5><a href="${basePath}addAction">新增</a></h5>
	<h5><a href="${basePath}updateAction">更新</a></h5>
	
	
	<h3>请求执行action的方法并跳转</h3>
	<h5><a href="${basePath}addAction">使用标签属性跳转:新增方法</a></h5>
	<h5><a href="${basePath}operateAction!add">使用感叹号调用:新增方法</a></h5>
	<h5><a href="${basePath}wildcardAction_add">使用通配符调用:新增方法</a></h5>
	
	
	<h3>传递参数</h3>
	<h5><a href="${basePath}jsp/login1.jsp">传递对象方式一</a></h5>
	<h5><a href="${basePath}jsp/login2.jsp">传递对象方式二</a></h5>
	<h5><a href="${basePath}jsp/login3.jsp">传递对象方式三</a></h5>
	<h5><a href="${basePath}jsp/login4.jsp">传递字符列表</a></h5>
	<h5><a href="${basePath}jsp/login5.jsp">传递对象列表</a></h5>
	
	
	
</body>
</html>