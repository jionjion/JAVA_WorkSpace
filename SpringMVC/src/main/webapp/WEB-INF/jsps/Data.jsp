<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>各种数据绑定页面</h1>
	<hr>
	
	<h4>基本数据类型绑定</h4>
	<a href="<%=request.getContextPath()%>/data/baseTypeInt?param=20">通过URL传递基本数据类型参数</a>
	<hr>
	
	<h4>包装类数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/packTypeInt">通过URL传递基本数据类型参数,可以不传递</a>
	<hr>
	
	<h4>数组类型数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/arrType?name=张三&name=李四&name=wangwu">通过URL传递数组</a>
	<hr>
	
	<h4>对象数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/objType?teacher.username=老师&user.username=张三&user.password=123456">通过URL传递对象</a>
	<hr>
	
	<h4>List对象数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/listType?users[0].username=张三&users[0].password=123456&users[1].username=lisi&users[1].password=456789">通过URL传递List对象</a>
	<hr>
	
	<h4>Set对象数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/setType?users[0].username=张三&users[0].password=123456">通过URL传递Set对象</a>
	<hr>
	
	<h4>Map对象数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/mapType?users['user1'].username=张三&users['user1'].password=123456&users['user2'].username=lisi&users['user2'].password=456789">通过URL传递Map对象</a>
	<hr>
	
	<h4>JSON对象数据绑定</h4>
	通过POST, 使用POSTMAN传递JSON对象
	{"username":"张三","password":"123456"}
	<hr>
	
	<h4>XML对象数据绑定</h4>
	通过POST, 使用POSTMAN传递XML对象
	<code>
		<user>
		<username>张三</username>	
		<password>123456</password>
		</user>
	</code>
	<hr>
	
	<h4>Boolean对象数据绑定.true/yes/on/1 => ture</h4>
	<a href="<%=request.getContextPath()%>/data/booleanType?bool=true">通过URL传递布尔值对象</a>
	<hr>
	
	<h4>日期对象数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/dateOneType?dateOne=2017-5-25">通过URL传递日期对象</a>
	<hr>
	
	<h4>通过配置文件实现日期对象数据绑定</h4>
	<a href="<%=request.getContextPath()%>/data/dateTwoType?dateTwo=2017-6-1">通过URL传递日期对象</a>
	<hr>
	
	<h4>RESTful形式URL</h4>
	<a href="<%=request.getContextPath()%>/data/30/RESTfulType">RESTful形式URL</a>
	<hr>
	
</body>
</html>