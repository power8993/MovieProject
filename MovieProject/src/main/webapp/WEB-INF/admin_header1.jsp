<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page import="member.domain.*" %>  
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

<%-- 아이콘 사용 연동링크 --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<%-- 사용자 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/index.css" />

<script type="text/javascript">
//===스크롤 시 네브의 배경색을 변경하고 고정하는 함수===//
//===스크롤 시 네브의 배경색과 고정 설정===//
window.onscroll = function () {
    var nav = document.querySelector(".nav.sticky-nav");
    var mv_search = document.querySelector("#mv_search");

    if (window.pageYOffset > 240) { // 스크롤 240px 이상일 때
        nav.classList.add("scrolled");
        nav.style.backgroundColor="#252422";
        $(".custom_link").css("color", "white");
        $("#mv_search").css("color", "white");
    } else {
        nav.classList.remove("scrolled");
        nav.style.backgroundColor="";
        $(".custom_link").css("color", "");
        $("#mv_search").css("color", "");
    }
};

$(document).ready(function(){
	///////////////////////////////////////////////////////////
	// 클릭한 메뉴에 css 속성 부여
	const currentPage = window.location.pathname;	// 현재 페이지의 URL

	// #mysidebar a태그
	$('#mysidebar a').each(function() {
	    // 태그의 href 속성과 현재 페이지가 일치하는지 확인
	    if ($(this).attr('href') == currentPage) {
	        // 일치하면 active 클래스를 추가
	        $(this).addClass('active');
	        $(this).parent().parent().prev().addClass('parent_active');
	    }
	});
	///////////////////////////////////////////////////////////
});
</script>

</head>
<body>
<div id="adver">
<div id="centerAdver" style="width:980px; margin:0 auto;">
<img src="<%= ctxPath%>/images/index/top_ad.png"/>
</div>
</div>

   <!-- 상단 네비게이션 -->
<div class="container">
    <div id="header">
        <div id="logo" class="mb-4"><a href="<%= ctxPath%>/"><img src="<%= ctxPath%>/images/index/logo.png" style="width:200px;margin-left: -29px; margin-top:-25px;"/></a></div>
        <div id="web_name" class="mb-4">DEEP DIVE SPACE</div>
        
        
           <div>
              
           </div>
           
           
        <%-- ${paraMap.userid}가 null이 아니면 로그인이 된 상태와 null인 상태를 나눌 것  --%>
        <%-- 로그인이 되지 않은 상태 --%>
       <c:if test="${empty sessionScope.loginuser}">
           <ul class="mb-4">
               <li><div class="icon"><a href="<%= ctxPath %>/login/login.mp" style="color:#eb5e28;"><i class="fa-solid fa-unlock-keyhole fa-2x"></i></a></div><a class="navbar-brand custom_a" href="<%= ctxPath %>/login/login.mp" >로그인</a></li>
            <li><div class="icon"><a href="<%= ctxPath %>/member/memberRegister.mp" style="color:#eb5e28;"><i class="fa fa-user-plus fa-2x"></i></a></div><a class="navbar-brand custom_a" href="<%= ctxPath %>/member/memberRegister.mp" >회원가입</a></li>
            <li><div class="icon"><a href="<%= ctxPath %>/notice/notice.mp" style="color:#eb5e28;"><i class="fa-solid fa-bell fa-2x"></i></a></div><a class="navbar-brand custom_a" href="<%= ctxPath %>/notice/notice.mp" >공지사항</a></li>
           </ul>
        </c:if>
        
        <%-- 로그인 된 상태 --%>
        
        <c:if test="${not empty sessionScope.loginuser}">
           <ul class="mb-4">
               <li><div class="icon"><a href="<%= ctxPath %>/login/logout.mp" style="color:#eb5e28;"><i class="fa-solid fa-unlock-keyhole fa-2x"></i></a></div><a class="navbar-brand custom_a" href="<%= ctxPath %>/login/logout.mp" >로그아웃</a></li>
            <li><div class="icon"><a href="<%= ctxPath %>/mypage/mypage.mp" style="color:#eb5e28;"><i class="fa-solid fa-user fa-2x"></i></a></div><a class="navbar-brand custom_a" href="<%= ctxPath %>/mypage/mypage.mp" >마이페이지</a></li>
            <li><div class="icon"><a href="<%= ctxPath %>/notice/notice.mp" style="color:#eb5e28;"><i class="fa-solid fa-bell fa-2x"></i></a></div><a class="navbar-brand custom_a" href="<%= ctxPath %>/notice/notice.mp" >공지사항</a></li>
           </ul>
        </c:if>
    </div>
</div>

<!-- Sticky 네비게이션 바 -->
<hr style="margin-top: 0px; margin-bottom: 0px;">
<div class="nav sticky-nav">
    <div id="nav_mediatop">
        <ul class="nav-list" style=" align-items: center;">
            <li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieList.mp">영화</a></li>
         <li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieTime.mp">상영시간표</a></li>
         <li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/reservation/reservation.mp">영화예약</a></li>
         <li style="margin-left:auto; white-space: nowrap;">
                
            <!-- 검색 폼 -->
                <div style="position:relative; min-width:250px;">
                <form action="<%= ctxPath %>/movie/searchMovie.mp" method="get" class="d-flex align-items-center">
                    <input id="mv_search_input" name="keyword" class="form-control mr-2" type="search" placeholder="영화 검색" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">
                        <i class="fas fa-search"></i>
                    </button>
                </form>
                </div>           
         </li>
      </ul>
    </div>
</div>
<hr style="margin-top: 0px; margin-bottom: 0px; height: 2px; background-color: #eb5e28;">

<div class="mycontainer">
		<div class="myheader"><i class="fa-solid fa-gear" style="color: #252422;"></i><span id="admin_page">&nbsp;관리자 페이지</span></div>
		
		<div class="mybody">
			<section id="mysidebar">
				<li><a href="<%=ctxPath%>/admin/admin.mp">AdminPage Home</a></li>
				<li>영화관리</li>
					<ul>
						<li><a href="<%= ctxPath %>/admin/movieRegister.mp">영화 등록</a></li>
						<li><a href="<%= ctxPath %>/admin/movieRegisteredList.mp">영화 리스트</a></li>
						<li><a href="<%= ctxPath %>/admin/showtimeList.mp">상영일정 리스트</a></li>
					</ul>
				<li>공지관리</li>
					<ul>
						<li><a href="<%= ctxPath %>/notice/notice.mp">공지 리스트</a></li>
					</ul>
				<li>회원관리</li>
					<ul>
						<li><a href="<%= ctxPath %>/member/memberList.mp">회원 리스트</a></li>
					</ul>
				<li>통계</li>
				
			</section>
			
			<section id="mycontent">
