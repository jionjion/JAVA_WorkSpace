<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内置对象</title>
</head>
<body>
	<h1>Out内置对象</h1>
	<%
		out.println("使用Out对象打印");
		out.flush();		//刷新缓冲区
//		out.clear();		//清空缓冲区,在flush之后抛出异常
		out.clearBuffer();	//清空缓存区,不会抛出异常
	%>
	<br>
	缓冲区大小:<%=out.getBufferSize() %><br>
	缓冲区剩余大小:<%=out.getRemaining() %><br>
	是否自动清空缓冲区:<%=out.isAutoFlush() %><br>
</body>
</html>