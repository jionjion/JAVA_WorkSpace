<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证码 </title>
<script type="text/javascript">
	function reloadCode(){
		debugger;
		var date = new Date().getTime();
		//刷新请求地址,携带时间参数,避免浏览器缓存
		document.getElementById("imageCode").src="${pageContext.request.contextPath}/code/reloadCode?d ="+date ;
	}
</script>
</head>
<body>
	<h1>各种验证码</h1>
	<fieldset>
	<legend>图片验证码</legend>
		<form action="<%=request.getContextPath()%>/code/login" method="post">
			验证码:<a href="javascript:reloadCode();"><img id="imageCode" alt="点击刷新" src="${pageContext.request.contextPath}/code/reloadCode"></a>
			<br>
			<input type="text" name="imageCode">
			<input type="submit" value="登录">
		</form> 
	</fieldset>
</body>
</html>