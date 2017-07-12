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
<title>请求表单</title>
</head>
<body>
	<form action="<%=basePath%>modules/jspBase/baseIII_02.jsp">
		用户:<input type="text" name="username"><br>
		密码:<input type="password" name="password"><br>
		<input type="checkbox" name="hobby" value="读书">读书
		<input type="checkbox" name="hobby" value="游戏">游戏
		<input type="checkbox" name="hobby" value="上网">上网<br>
		<input type="submit" value="提交">
	</form> 
	
	<a href="<%=basePath%>modules/jspBase/baseIII_02.jsp?username=李四&password=12345&hobby=读书">URL传参</a>
</body>
</html>