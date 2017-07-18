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
	<h4>import标签</h4>
	<hr>
	
	<!-- 使用catch标签结合out标签进行异常捕获 -->
	<c:catch var="error">	

		<!-- 导入网络上的绝对路径 -->
		<c:import url="http://www.baidu.com" charEncoding="UTF-8"/>
		
		<!-- 导入相对路径 -->
		<c:import url="baseIX.text" charEncoding="UTF-8"/>
		
		<!-- 导入其他项目的文件,注意先后运行顺序 -->
		<%-- <c:import url="baseIX.text" context="其他的" charEncoding="UTF-8"/> --%>
		
	</c:catch>
	<c:out value="${error}"/>
</body>
</html>