<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="header1.jsp" />

	<div class="container">
		<p class="h2 text-center mt-4">장애발생</p>
		<img src="<%= request.getContextPath()%>/images/error.gif" class="img-flouid" />	<%-- class="img-flouid" 는 반응형 이미지 --%>
		<p class="h2 text-primary mt-3">빠른 복구를 위해 최선을 다하겠습니다.</p>
	</div>

<jsp:include page="footer1.jsp" />



<%-- 페이지 전체에 에러 이미지를 띄우는 것이 아닌, 기존 틀의 일부분에서 보여줄 것이기 때문에 앞뒤로 include 로 jsp 를 가져왔다. --%>