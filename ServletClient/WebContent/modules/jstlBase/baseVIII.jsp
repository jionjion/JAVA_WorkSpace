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
	<h4>forTokens标签</h4>
	
	<!-- 对指定字符串进行拆分 -->
	<c:forTokens items="2017-7-18" delims="-" var="item">
		<c:out value="${item}"/><br>
	</c:forTokens>
</body>
</html>