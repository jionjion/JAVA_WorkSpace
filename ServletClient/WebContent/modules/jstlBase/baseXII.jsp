<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 核心标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 常用函数 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>常用函数</h4>
	<hr>
	<!-- 判断一个字符串中是否包含另一个字符串 -->
	<c:out value="'锄禾日当午'中是否包含词语'锄禾':${fn:contains('锄禾日当午','锄禾') }"></c:out>
	<br>

	<!-- 判断一个字符串中是否包含另一个字符串,忽略大小写 -->
	<c:out value="'hello world'中是否包含词语'HELLO':${fn:containsIgnoreCase('锄禾日当午','锄禾') }"></c:out>
	<br>
	
	<!-- 判断一个字符串是否以XX结尾 -->
	<c:out value="'hello world'中是否以'he'开头:${fn:startsWith('hello world','he') }"></c:out><br>
	
	<!-- 判断一个字符串是否以XX结尾 -->
	<c:out value="'hello world'中是否以'ld'结尾:${fn:endsWith('hello world','ld') }"></c:out><br>
	
	<!-- 判断一个字符串中子串的位置 -->
	<c:out value="'hello world'中ll的位置:${fn:indexOf('hello world','ll') }"></c:out><br>
	
	<!-- 输出xml文件 -->
	<c:out value="${fn:escapeXml('<book>我不喜欢这个世界,我只喜欢你</book>')}"></c:out><br>
	<c:out value="<book>我不喜欢这个世界,我只喜欢你</book>"></c:out>
</body>
</html>