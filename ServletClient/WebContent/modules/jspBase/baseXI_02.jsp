<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@	page import="java.net.URLEncoder"%>
<%@ page import="javax.servlet.http.*" %>    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用Cookie实现记住密码02</title>
</head>
<body>
	<%
		//保存用户名
		String username = "";
		String password = "";
	%>

	<%
		String[] isRemember = request.getParameterValues("remember");
		if(isRemember != null && isRemember.length > 0 ){
			//保存用户名
			username = request.getParameter("username");
			password = request.getParameter("password");
			//编码中文
			String encodeUsername = URLEncoder.encode(username, "UTF-8");
			String encodePassword = URLEncoder.encode(password, "UTF-8");
			//生成cookie
			Cookie usernameCookie = new Cookie("username",encodeUsername);
			Cookie passwordCookie = new Cookie("password",encodePassword);
			//设置属性
			usernameCookie.setMaxAge(1000);		//单位秒,生命周期
			passwordCookie.setMaxAge(1000);		//单位秒,生命周期
			//保存cookie
			response.addCookie(usernameCookie);
			response.addCookie(passwordCookie);
		}else{
			//获取传入参数
			username = request.getParameter("username");
			password = request.getParameter("password");
			//将已经保存的cookies销毁
			Cookie[] cookies = request.getCookies();
			if(cookies != null & cookies.length>0){
				//遍历cookie
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("username") || cookie.getName().equals("password")){
						cookie.setMaxAge(0);		//销毁
						response.addCookie(cookie);	//重新保存
					}
				}
			}
		}
	%>
	<h1>登录成功</h1>
	用户:	<%=username %>				<br>
	密码:	<%=password %>				<br>
</body>
</html>