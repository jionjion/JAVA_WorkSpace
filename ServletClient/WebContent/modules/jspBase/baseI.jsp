<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- page指令	  language:服务器语言	contentType:页面类型,字符	pageEncoding:编码格式	 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP基础介绍</title>
</head>
<body>

	<!-- HTML注释.客户端注释 -->
	<%-- JSP注释.服务器可见 --%>
	<%
		//单行注释
		/*	多行注释
			多行注释	
		*/
	%>
	
	<%	//jsp脚本
		out.print("打印到页面");
		System.out.println("打印到控制台");
		String author = "张谦";		//全局变量
	%>
	<hr>
	
	<%!	//JSP声明,注意分号结束
		String author = "JION";		//局部变量
		int sum( int x , int y ){return x+y;}
	%>
	
	<%=this.author %>	<!-- 局部变量 -->
	<%=author %>		<!-- 全局变量 -->
	<%=sum(1,2) %>		<!-- 调用表达式 -->
	<hr>
	
	今天是:<%= new SimpleDateFormat("YYYY年MM月dd日").format(new Date()) %>
	<hr>
	
	<%	//使用内置对象打印
		for(int i = 1 ; i<=9 ; i++){
			for(int j = i ; j<=9 ; j++){
				out.print(i+"X"+j+"="+(i*j)+"&nbsp;&nbsp;&nbsp;");
			}
			out.print("</br>");
		}
	%>
	
	<%!	//使用声明函数结合表达式打印
		String print(){
		String string = "";
		for(int i = 1 ; i<=9 ; i++){
			for(int j = i ; j<=9 ; j++){
				string += i+"X"+j+"="+(i*j)+"&nbsp;&nbsp;&nbsp;";
			}
			string += "</br>";
		}
		return string;
	}
	%>
	<%=print() %>
</body>
</html>