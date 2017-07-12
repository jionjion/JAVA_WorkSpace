<%@page import="java.io.PrintWriter"%>
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
<title>response内置对象</title>
</head>
<body>
	<%
		response.setContentType("text/html;charset=utf-8");		//设置响应类型:返回text文本或者html文本,编码格式为UTF-8
		
		//请求重定向
		response.sendRedirect("baseIII_01.jsp");		//请求重定向
		//请求转发
		request.getRequestDispatcher("baseIII_01.jsp").forward(request, response);
		out.println("内置Out对象输出晚于PrintWrite,除非使用flush()强制刷新缓存");
		out.flush();		
		
		PrintWriter writer = response.getWriter();
		writer.println("response的输出");
		
	%>
	<br>
	
	
</body>
</html>