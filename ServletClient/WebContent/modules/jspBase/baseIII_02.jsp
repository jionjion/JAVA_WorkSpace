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
<title>request内置对象</title>
</head>
<body>
	<h1>读取请求参数</h1>
	<% request.setCharacterEncoding("UTF-8");%>
	用名:<%=request.getParameter("username") %><br>		<!-- 传入为空,则抛出指针 -->
	密码:<%=request.getParameter("password") %><br>
	爱好:<% String[] hobbys = request.getParameterValues("hobby");
		   for(int i=0 ; i<hobbys.length ; i++){
				out.print(hobbys[i]);   
		   }%><br>

	<!-- 使用request设置参数 -->
	<% request.setAttribute("address", "河南"); %><br>
	地址:<%=request.getAttribute("address") %>	<br>	
	请求体的MIME类型:<%=request.getContentType() %><br>
	协议类型及版本号:<%=request.getProtocol() %><br>
	请求的方式:<%=request.getMethod() %><br>
	服务器主机名:<%=request.getServerName() %><br>
	服务器端口号:<%=request.getServerPort() %><br>
	请求文件的长度:<%=request.getContentLength() %><br>
	请求客户端的IP:<%=request.getRemoteAddr() %><br>
	请求客户端的端口:<%=request.getRemotePort() %><br>
	请求的真实路径:<%=request.getRealPath("baseIII_02.jsp") %><br>
	请求的上下文路径:<%=request.getContextPath() %><br>
</body>
</html>