<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jspBase.bean.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>Set标签</h4>
	
	<!-- 第一种,存值到Scope中 -->
	<c:set value="我是Session中的变量" var="param1" scope="session"></c:set>
	<c:out value="${param1 }"></c:out>
	<br>
	
	<!-- 第二种,存值到Application中 -->
	<c:set var="param2" scope="application">我是Application中的变量</c:set>
	<c:out value="${param2 }"></c:out>
	<br>
	
	<!-- 第三种,存值到JavaBean中 -->
	<jsp:useBean id="user" class="jspBase.bean.User"></jsp:useBean>
	<c:set target="${user}" property="username" value="Jion"></c:set>
	<c:out value="${user.username}"></c:out>
	<c:set target="${user}" property="password">123456</c:set>
	<c:out value="${user.password}"></c:out>
</body>
</html>