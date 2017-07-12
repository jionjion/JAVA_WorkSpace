<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<title>session内置对象</title>
</head>  
<body>
	创建时间:<%=new SimpleDateFormat("yyyy年MM月dd日").format(new Date(session.getCreationTime())) %><br>
	SessionID:<%=session.getId() %><br>
	<% session.setAttribute("author", "Jion"); %>
	作者:<%=session.getAttribute("author") %><br>
	<%session.setMaxInactiveInterval(10);//设置销毁时间.单位秒 %><br>
	<%session.invalidate();		//销毁session %>
	<!-- 
		也可以在web.xml中配置,超时时间,单位分钟
		<session-config>
			<session-timeout>
				10
			</session-timeout>
		</session-config>
	 -->
	
</body>
</html>