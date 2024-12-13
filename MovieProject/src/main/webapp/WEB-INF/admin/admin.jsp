<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >

<jsp:include page="/WEB-INF/header1.jsp" />
	
	<div class="mycontainer">
		<div class="myheader">
		
		</div>
		
		<div class="mybody">
			<section id="mysidebar">
				<div>AdminPage Home</div>
				<li>영화관리</li>
					<ul>
						<li><a href="movieRegister.up">영화 등록</a></li>
						<li><a>영화 수정/삭제</a></li>
					</ul>
				<li>공지관리</li>
					<ul>
						<li>공지 등록</li>
						<li>공지 수정/삭제</li>
					</ul>
				<li>유저관리</li>
					<ul>
						<li>유저 검색</li>
					</ul>
				<li>통계</li>
				
			</section>
			
			<section id="mycontent">

			</section>
		</div>
	</div>

	
<jsp:include page="/WEB-INF/footer1.jsp" />  