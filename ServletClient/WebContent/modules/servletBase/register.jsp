<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登录界面</title>
  </head>
  
  <body>
    <h1>用户注册</h1>
    <form name="regForm" action="<%=basePath %>servletBase/servlet/RegisterServlet" method="post" >
			    	用户名：
			    	<input type="text" name="username" /><br>
			    	密码：
			    	<input type="password" name="password" ><br>
			    	确认密码：
			    	<input type="password" name="confirmpass" ><br>
			    	电子邮箱：
			    	<input type="text" name="email" ><br>
			    	性别：
			    	<input type="radio" name="sex" checked="checked" value="Male">男
			    	<input type="radio" name="sex" value="Female">女<br>
			    	出生日期：
			    	  <input name="birthday" type="date"/><br>
			    	爱好：
				    	<input type="checkbox" name="hobby" value="nba"> NBA &nbsp;
				    	<input type="checkbox" name="hobby" value="music"> 音乐 &nbsp;
				    	<input type="checkbox" name="hobby" value="movie"> 电影 &nbsp;
				    	<input type="checkbox" name="hobby" value="internet"> 上网 &nbsp;<br>
			    	自我介绍：
			    		<textarea name="introduce" rows="10" cols="40"></textarea><br>
			    	接受协议：
			    		<input type="checkbox" name="flag" value="true">是否接受霸王条款<br>
	
	
			    		<input type="submit" value="注册"/>&nbsp;&nbsp;
			    	    <input type="reset" value="取消"/>&nbsp;&nbsp;<br>
			</form>
  </body>
</html>
