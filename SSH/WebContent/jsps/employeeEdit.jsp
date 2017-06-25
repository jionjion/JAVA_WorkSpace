<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>编辑员工</h1>
	<hr>
	<form action="${pageContext.request.contextPath }/employee_update" method="post">
		员工姓名:<input type="text" name="ename" value="<s:property value="model.ename"/>"><br>
		员工性别:<s:radio list="{'男','女'}" name="esex" value="%{model.esex}"></s:radio><br>
		员工生日:<input type="date" name="ebirthday" value="<s:date name="model.ebirthday" format="yyyy-mm-dd"/>"><br>
		入职日期:<input type="date" name="ejoinDate" value="<s:property value="model.ejoinDate"/>"><br>
		员工工号:<input type="text" name="eno" value="<s:property value="model.eno"/>"><br>
		员工密码:<input type="password" name="epassword" value="<s:property value="model.epassword"/>"><br>
		所属部门:<s:select list="list" name="department.did" listKey="did" listValue="dname" headerKey="--请选择--" value="%{model.department.did}"></s:select><br>
		<input type="hidden" name="eid" value="<s:property value="model.eid"/>">
		<input type="submit" value="提交">
	</form>
</body>
</html>