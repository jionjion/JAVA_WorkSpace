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
	<h4>url标签</h4>
	<hr>
	
	<!-- 动态生成URL -->
	<c:if test="${1<2}">
		<!-- 成立,设置新的变量 -->
		<c:set var="changeParam">username=jion</c:set>
	</c:if>
	<!-- 在URL中添加新的URL -->
	<c:url var="newURL" value="http://localhost:8080/ServletClient?${changeParam}" scope="session"></c:url>
	<a href="${newURL}"	>新的URL</a>
</body>
</html>