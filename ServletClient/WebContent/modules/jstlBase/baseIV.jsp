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
	<h4>catch标签</h4>
	
	<!-- 使用默认的error关键字,保存错误信息 -->
	<c:catch var="error">
		<c:set target="语法错误.抛出异常..."/>
	</c:catch>
	<!-- 输出错误信息 -->
	<c:out value="${error }"/>
	<br>
	
</body>
</html>