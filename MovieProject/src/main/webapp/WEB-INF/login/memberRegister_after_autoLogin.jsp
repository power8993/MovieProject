<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	window.onload = function() {
		
		const frm = document.loginFrm;
		frm.action = "<%= ctxPath%>/login/login.mp";
		frm.method = "post";
		frm.submit();
		
	}
</script>

</head>
<body>
	<form name="loginFrm">
		<input type="hidden" name="userid" value="${requestScope.userid}" />
		<input type="hidden" name="pwd" value="${requestScope.pwd}" />
		<input type="hidden" name="successRegister" value="1" />
		
	</form>
</body>
</html>