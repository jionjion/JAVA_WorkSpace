<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>Out标签</h4>
	<!-- 直接输出 -->
	<c:out value="通过out标签实现输出"></c:out>
	<br>
	
	<% request.setAttribute("username", "Jion"); %>
	<!-- 使用EL对象输出变量 -->
	<c:out value="${username}"></c:out>
	<br>
	<!-- 当没有值的时候输出默认值 -->
	<c:out value="${nothing}" default="默认值"></c:out>
	<br>
	
	<!-- 使用转义字符,默认不支持.	escapeXml="false"开启转义功能 -->
	<c:out value="&lt;out标签&gt;" escapeXml="false"></c:out>
	
	<!-- EL运算符 -->
	<c:out value="是否为空或空串:${empty username }"></c:out>
	<br>
</body>
</html>