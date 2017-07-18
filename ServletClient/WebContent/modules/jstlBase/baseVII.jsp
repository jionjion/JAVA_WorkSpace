<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>forEach标签</h4>
	
	<% 
		List<String> lists = new ArrayList<String>();
		lists.add("元素一");
		lists.add("元素二");
		lists.add("元素三");
		lists.add("元素四");
		lists.add("元素五");
		request.setAttribute("lists", lists);
	%>
		
	<!-- 使用forEach遍历全部 -->
	<c:forEach var="list" items="${lists }">
		<c:out value="${list}"></c:out><br>
	</c:forEach>
	<c:out value="------------------"></c:out>
	<br>
	
	<!-- 使用forEach遍历部分,[0,2]计3个元素 -->
	<c:forEach var="list" items="${lists }" begin="0" end="2">
		<c:out value="${list}"></c:out><br>
	</c:forEach>
	<c:out value="------------------"></c:out>
	<br>
	
	<!-- 使用forEach,从0开始,每两个进行一次遍历. -->
	<c:forEach var="list" items="${lists }" step="2">
		<c:out value="${list}"></c:out><br>
	</c:forEach>
	<c:out value="------------------"></c:out>
	<br>
	
	<!-- 使用forEach遍历全部,并输出属性值 -->
	<c:forEach var="list" items="${lists }" varStatus="li">
		<c:out value="${list}的四个属性"></c:out>
		<c:out value="index属性:${li.index}"></c:out>		<!-- 从0开始 -->
		<c:out value="index属性:${li.count}"></c:out>		<!-- 计数,从1开始 -->
		<c:out value="first属性:${li.first}"></c:out>		<!-- 第一个返回为true -->
		<c:out value="last属性:${li.last}"></c:out>		<!-- 最后一个返回为true -->
		<br>
	</c:forEach>
	<c:out value="------------------"></c:out>
	<br>
	
</body>
</html>