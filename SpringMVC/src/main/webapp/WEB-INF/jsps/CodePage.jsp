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
		alert("刷新!");
		
	}
</script>
</head>
<body>
	<h1>各种验证码</h1>
	<fieldset>
	<legend>图片验证码</legend>
		<form action="<%=request.getContextPath()%>/login/login" method="post">
			用户:<input type="text" name="username"><br>
			密码:<input type="password" name="password"><br>
			验证码:<a href="javascript:reloadCode();"><img alt="点击刷新" src="${pageContext.request.contextPath}/code/reloadCode"></a>
			<input type="submit" value="登录">
		</form> 
	</fieldset>
</body>
</html>