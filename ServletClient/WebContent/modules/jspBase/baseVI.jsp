<%@page import="java.util.Enumeration"%>
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
<title>application内置对象</title>
</head>  
<body>
	<%
		application.setAttribute("author", "Jion");
		application.setAttribute("city", "上海");			//保存属性
	%>
	
	作者:<%=application.getAttribute("author")%>
	引擎版本号:<%=application.getServerInfo() %>
	<%
		//获得属性集合
		Enumeration enumeration = application.getAttributeNames();
		while(enumeration.hasMoreElements()){
			out.println(enumeration.nextElement()+"&nbsp;");
		}
	%>
</body>
</html>