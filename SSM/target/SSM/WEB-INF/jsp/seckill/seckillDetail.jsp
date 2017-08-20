<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ include file="../common/constant.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>秒杀商品详情</title>
<%@include file="../common/head.jsp"%>
</head>
	<script type="text/javascript" src="<%=basePath %>/static/js/seckill.js"></script>
	<script type="text/javascript">
		$(function(){
			//使用EL表达式传入参数
			seckill.detail.init({
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},
				endTime   :	${seckill.endTime.time}
			});
		});
	</script>
<body>
	<!-- 面板部分 -->
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading"><h3>${seckill.name}</h3></div>
			<div>
				<div class="panel-body">
					<h2 class="text-danger">
						<!-- 图标 -->
						<span class="glyphicon glyphicon-time"></span>
						<!-- 计时 -->
						<span class="glyphicon" id="seckill-box"></span>
					</h2>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 模态弹窗 -->
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone">用户电话:</span>
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhoneKey" placeholder="请填写手机号" class="form-control">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<!-- 验证 -->
					<span id="killPhoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone">提交</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>