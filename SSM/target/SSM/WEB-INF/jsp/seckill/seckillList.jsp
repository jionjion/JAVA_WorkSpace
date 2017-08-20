<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ include file="../common/constant.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>秒杀商品列表</title>
<%@include file="../common/head.jsp"%>
</head>
<body>

	<!-- 列表部分 -->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>秒杀商品列表</h2>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>详情点击</th>
						</tr>
						<c:forEach var="item" items="${seckillList}">
							<tr>
								<td>${item.name}</td>
								<td>${item.number}</td>
								<td><fmt:formatDate value="${item.startTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${item.endTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${item.createTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><a class="btn btn-info"
									href="<%=basePath %>/seckill/${item.seckillId}/detail"
									target="_blank">详情</a></td>
							</tr>
						</c:forEach>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>