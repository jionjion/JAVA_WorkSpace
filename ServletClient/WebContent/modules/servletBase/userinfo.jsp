<%@ page language="java" import="java.util.*,java.text.*" contentType="text/html; charset=utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户信息显示页面</title>
  </head>
  
  <body>
    <h1>用户信息</h1>
    <hr>
    	<jsp:useBean  id="user" class="servletBase.bean.User" scope="session"/>   
          	用户名：&nbsp;<jsp:getProperty name="user" property="username"/><br>
          	密码：&nbsp;<jsp:getProperty name="user" property="password"/><br>
          	性别：  &nbsp;<jsp:getProperty name="user" property="sex"/><br>
          	E-mail：  &nbsp;<jsp:getProperty name="user" property="email"/><br>
         	出生日期：&nbsp; <%=new SimpleDateFormat("yyyy年MM月dd日").format(user.getBirthday())%><br>
         	爱好：&nbsp;
	            <% 
	               String[] hobby = user.getHobby();
	               for(String h:hobby)
	               {
	            %>
	        <%=h%> &nbsp;&nbsp;
	            <% 
	               }
	            %><br>
          	自我介绍： &nbsp;<jsp:getProperty name="user" property="introduce"/><br>
        	是否介绍协议：&nbsp;<jsp:getProperty name="user" property="flag"/>
  </body>
</html>
