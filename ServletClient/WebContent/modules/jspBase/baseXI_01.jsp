<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用Cookie实现记住密码01</title>
</head>
<body>
	<%
		//读取本地cookie,填写数据
		String username = "";
		String password = "";
		Cookie[] cookies = request.getCookies();
		if(cookies != null & cookies.length>0){
			//遍历cookie
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("username")){
					username = cookie.getValue();
					username = URLDecoder.decode(username, "UTF-8");
				}
				if(cookie.getName().equals("password")){
					password = cookie.getValue();
					password = URLDecoder.decode(password, "UTF-8");
				}
			}
		}
	%>
	<form action="<%=basePath%>modules/jspBase/baseXI_02.jsp">
		用户:<input type="text" name="username" value="<%=username %>"><br>
		密码:<input type="password" name="password" value="<%=password %>"><br>
		记住密码<input type="checkbox" name="remember"><br>
		<input type="submit" value="提交">
	</form>
</body>
</html>