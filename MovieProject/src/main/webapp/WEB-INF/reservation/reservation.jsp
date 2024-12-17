<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
   String ctxPath = request.getContextPath();
    //     /MyMVC
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/reservation/reservation.css" />

<jsp:include page="../header1.jsp" />

<div class="container">
	
	<div class="row text-center">
		
		<div class="col-md-5">
			<h5>영화</h5>
			<ul>
				<li>하얼빈</li>
				<li>무파사</li>
				<li>소방관</li>
				<li>대가족</li>
				<li>인터스텔라</li>
				<li>위키드</li>
			</ul>
		</div>
		<div class="col-md-2">
			<h5>날짜</h5>
		</div>
		<div class="col-md-5">
			<h5>시간</h5>
		</div>
		
	</div>

</div>

<jsp:include page="../footer1.jsp" />