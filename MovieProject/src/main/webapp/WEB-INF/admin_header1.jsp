<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>
<!DOCTYPE html>
<html>
<head>

<title>:::HOMEPAGE:::</title> 

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" > 

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 

<%-- jQueryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script> 


<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/index.css" />

<script type="text/javascript">
//===스크롤 시 네브의 배경색을 변경하고 고정하는 함수===//
//===스크롤 시 네브의 배경색과 고정 설정===//
window.onscroll = function () {
    var nav = document.querySelector(".nav.sticky-nav");

    if (window.pageYOffset > 240) { // 스크롤 240px 이상일 때
        nav.classList.add("scrolled");
        nav.style.backgroundColor="#252422";
        $(".custom_link").css("color", "white");
    } else {
        nav.classList.remove("scrolled");
        nav.style.backgroundColor="";
        $(".custom_link").css("color", "");
    }
};
</script>

<style>

</style>
</head>
<body>
<div id="adver">광고영역</div>

   <!-- 상단 네비게이션 -->
<div class="mycontainer">
    <div id="header">
        <div id="logo" class="mb-4">logo</div>
        <div id="web_name" class="mb-4">사이트명</div>
	        <div>
	        	<a href="<%= ctxPath %>/admin/admin.mp" >관리자</a>
				<a href="<%= ctxPath %>/mypage/mypage.mp" >마이페이지</a>
	        </div>
        <ul class="mb-4">
            <li><div class="icon">icon</div><a class="navbar-brand custom_a" href="<%= ctxPath %>/login/login.mp" >로그인</a></li>
			<li><div class="icon">icon</div><a class="navbar-brand custom_a" href="<%= ctxPath %>/member/memberRegister.mp" >회원가입</a></li>
			<li><div class="icon">icon</div><a class="navbar-brand custom_a" href="<%= ctxPath %>/notice/notice.mp" >공지사항</a></li>
        </ul>
    </div>
</div>

<!-- Sticky 네비게이션 바 -->
<div class="nav sticky-nav">
    <div id="nav_mediatop">
        <ul class="nav-list">
            <li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieList.mp">영화</a></li>
			<li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieTime.mp">상영시간표</a></li>
			<li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieDetail.mp">영화상세보기</a></li>
			<li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/reservation/reservation.mp">영화예약</a></li>
        </ul>
    </div>
</div>

<div class="mycontainer">
		<div class="myheader">
		
		</div>
		
		<div class="mybody">
			<section id="mysidebar">
				<div>AdminPage Home</div>
				<li>영화관리</li>
					<ul>
						<li><a href="movieRegister.mp">영화 등록</a></li>
						<li><a href="movieRegisteredList.mp">영화 리스트<br>[수정/삭제/상영등록]</a></li>
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
