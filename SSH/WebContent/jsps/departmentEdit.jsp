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
	<h1>编辑部门</h1>
	<hr>
	<form action="${pageContext.request.contextPath }/department_update" method="post">
		部门名称:<input type="text" name="dname" value="<s:property value="model.dname"/>" ><br>
		部门介绍:<textarea name="ddesc"><s:property value="model.ddesc"/></textarea><br>
		<input type="hidden" name="did" value="<s:property value="model.did"/>">
		<input type="submit" value="提交">
	</form>
</body>
</html>