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
<title>Include指令的使用</title>
</head>
<body>
	<%@include file="baseXII_02.jsp" %>
	
	<h4>include的指令支持的文本格式为Unicode格式,操作系统格式</h4>
	<%@include file="baseXII_03_Unicode.txt" %>	
	<h4>include的动作支持的文本格式为UTF-8,项目创建格式</h4>	
	<jsp:include page="baseXII_03_UTF8.txt" flush="true"/>	<!-- 不使用缓冲 -->
	
	
	<hr>
	<h3>转发参数</h3>
	用户:<%=request.getParameter("username") %>
	密码:<%=request.getParameter("password") %>
	
	
</body>
</html>