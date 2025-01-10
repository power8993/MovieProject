<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String ctx_Path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/WEB-INF/header1.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/login/threeMonthPwdChange.css" />
<script type="text/javascript" src="<%= ctx_Path%>/js/login/threeMonthPwdChange.js"></script>
</head>
<body>
<div id="path" style="display:none;" ><%= ctx_Path%></div> <%-- 자바스크립트에 값을 전달하기 위한 요소 --%>
<div id="form_container">

	<div id="contentElmt">
	
		<div id="contentHeader">
			<div id="lockImg">
				<img src="<%= ctx_Path%>/images/login/Lock.png">
			</div>
			<p id="contentHeaderMent">개인정보를 위해<br>비밀번호를 변경해주세요</p>
		</div>
		
		
		<div id="addMent">
			<p>이상우 회원님은 <span style="color:#0099ff;">3개월 이상 동일한 비밀번호</span>를 사용하고 계십니다.<br>소중한 개인 정보보호를 위해 비밀번호를 주기적으로 변경해주세요.</p>
		</div>	
		
		<form name="threeMonthFrm">
			<div id="formElmt">
			
				<input type="password" name="currentPwd" placeholder="현재 비밀번호">
				<div id="currentPwdMent"></div> <%-- 비밀번호의 유효성에 대한 문구 --%>
				<input type="password" name="newPwd1" placeholder="새 비밀번호">
				<input type="password" name="newPwd2" placeholder="새 비밀번호 확인">
				<div id="newPwdMent"></div> <%-- 새로운 비밀번호의 유효성에 대한 문구 --%>
			
			</div>
			
			<div id="btnElmt">
				<button type="button" id="submitBtn">비밀번호 변경</button><button type="button" id="button">나중에 변경</button>
			</div>
		</form>
		
	</div>
	
</div>




<jsp:include page="/WEB-INF/footer1.jsp" />
</body>
</html>