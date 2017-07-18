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
	<h4>choose,when,otherwise标签</h4>
	
	<form action="baseVI.jsp" method="post">
												<!-- 将值压入变量map中 -->
		用户:<input type="text" name="username" value="${param.username}"/>
		成绩:<input type="text" name="score" value="${param.score}">
		<input type="submit"/>
	</form>
	
	<!-- if进行成绩判断 -->
	<c:choose>
		<c:when test="${param.score >= 90}">
			<c:out value="恭喜,成绩优秀"></c:out>
		</c:when>
		
		<c:when test="${param.score >= 60 && param.score < 90}">
			<c:out value="你还好...."></c:out>
		</c:when>
		
		<c:otherwise>
			<c:out value="滚去学习.."></c:out>
		</c:otherwise>
	</c:choose>
	
</body>
</html>