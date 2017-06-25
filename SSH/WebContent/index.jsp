<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>员工管理系统</h1>
	<form action="${pageContext.request.contextPath}/employee_login" method="post">
		姓名:<input type="text" name="ename"><br>
		密码:<input type="password" name="epassword"><br>
		<input type="submit" value="提交"><br>
		<s:actionerror/>
	</form>
</body>
</html>