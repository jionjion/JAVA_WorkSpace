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
	<h1>部门列表</h1>
	<h3><a href="${pageContext.request.contextPath}/department_savePage.action">添加</a></h3>
	<hr>
		<table>
			<tr>
				<th>部门编号</th>
				<th>部门名称</th>
				<th>部门描述</th>
				<th>操作</th>
			</tr>
			<s:iterator value="list" var="d">
				<tr>
					<td><s:property value="#d.did"/></td>
					<td><s:property value="#d.dname"/></td>
					<td><s:property value="#d.ddesc"/></td>
					<td><a href="${ pageContext.request.contextPath }/department_edit.action?did=<s:property value="#d.did"/>">修改</a></td>
					<td><a href="${ pageContext.request.contextPath }/department_delete.action?did=<s:property value="#d.did"/>">删除</a></td>
				</tr>
			</s:iterator>
		</table>
	<hr>
	<!-- 不是第一页 -->
	<s:if test="currentPage !=1 ">
		<a href="${ pageContext.request.contextPath }/department_findAll.action?currentPage=1">[首页]</a>
		<a href="${ pageContext.request.contextPath }/department_findAll.action?currentPage=<s:property value="currentPage-1"/>">[上一页]</a>
	</s:if>
		<a>	当前<s:property value="currentPage"/>页
			共<s:property value="totalPage"/>页
			总<s:property value="totalCount"/>条记录	
		</a>
	<!-- 不是最后一页 -->
	<s:if test="currentPage != totalPage">
		<a href="${ pageContext.request.contextPath }/department_findAll.action?currentPage=<s:property value="currentPage+1"/>">[下一页]</a>
		<a href="${ pageContext.request.contextPath }/department_findAll.action?currentPage=<s:property value="totalPage"/>">[尾页]</a>
	</s:if>
		<a>每页显示<s:property value="pageSize"/> </a>
</body>
</html>