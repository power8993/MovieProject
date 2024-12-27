<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String ctxPath = request.getContextPath();
%>
<jsp:include page="../header1.jsp" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mydeleteEnd.css" />
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>
<div class="deleteclear">
<i class="fa-solid fa-circle-chevron-down fa-bounce" style="color: #eb5e28;"></i>
<h1>회원탈퇴가 완료되었습니다.</h1>
<div class="clearborder"></div>
<p> <strong>HGV</strong>를 이용해주셔서 감사합니다. </p>
<p> 더욱 노력하고 발전하는 <strong>HGV</strong>가 되겠습니다. </p>
<a class="deletemainclear" href="<%=ctxPath%>/index.mp">메인으로</a>
</div>

<jsp:include page="../footer1.jsp" />