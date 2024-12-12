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

<!-- Font Awesome 6 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">

<!-- 직접 만든 CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/template/template.css" />

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 

<%-- jQueryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script> 

<%-- 직접 만든 JS --%>
<script type="text/javascript" src="<%= ctxPath%>/js/template/template.js"></script>

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
body{
margin:0;
padding:0;
background-color:#fffcf2;
}

div#adver {
   width: 100%;
   border: solid 1px red;
   height: 92px;
}

div.container {
   max-width: 980px; /* 최대 너비를 980px로 제한 */
   min-width: 980px;
   margin: 0 auto; /* 가운데 정렬 */
   padding: 0px; /* 좌우 여백 */
   box-sizing: border-box; /* padding 포함 계산 */
   border: solid 1px red;
}

div#header{
   width:100%;
   display:flex;
   align-items: flex-start; /* 기본값: 자식 요소들이 위쪽에 정렬 */
   margin-top:50px;
   margin-bottom:0px;
}

div#logo{
   width: 12.24%;
   height: 61px;
   border: solid 1px red;
   margin-top: auto;
}
div#web_name{
   width: 10%; /* 부모가 980px일 때 정확히 98px */
    max-width: 98px; /* 부모가 작아질 경우 최대 크기를 제한 */
    height:27px;
    border: solid 1px red;
    align-self: flex-end; /* 부모 요소의 교차 축(세로) 하단으로 이동 */
    margin-left:2%;
}

div#header > ul {
	width:360px;
   list-style: none;
   display:flex;    
   margin-left:250px;
   margin-bottom:0px;
   padding:0px;
   border: solid 1px black;
}

div#header > ul > li{
   width:6em;
   height:80px;
}

	

div#header > ul > li:nth-child(1){
   
   border: solid 1px red;
   margin-right:5%;
}
div#header > ul > li:nth-child(2){
   
   border: solid 1px blue;
   margin-left:5%;
   margin-right:5%;
}
div#header > ul > li:nth-child(3){
   
   border: solid 1px black;
   margin-left:5%;
}

/* 네비게이션 바 */
.nav.sticky-nav {
    width: 100%; /* 화면 전체 너비 */
    position: sticky;
    top: 0;
    z-index: 99; /* 다른 요소보다 위에 표시 */
    background-color: #fffcf2; /* 기본 배경색 */
}

#nav_mediatop {
    width: 100%; /* 화면 전체 너비 */
    display: flex;
    justify-content: center; /* 내부 컨테이너를 가운데 정렬 */
    background-color: inherit; /* 부모의 배경색 상속 */
}

#nav_mediatop .nav-list {
    max-width: 980px; /* .container의 최대 너비와 동일 */
    width: 100%; /* 레이아웃 유지 */
    display: flex;
    justify-content: flex-start; /* 메뉴를 왼쪽 정렬 */
    list-style: none;
    padding: 0;
    margin: 0;
}

/* 링크 스타일 초기화 */
.nav.sticky-nav .nav-link {
    color: black; /* 기본 링크 색상 */
    padding: 10px 20px; /* 내부 여백 */
    text-decoration: none; /* 밑줄 제거 */
}

/* 링크 hover 스타일 */
.nav.sticky-nav .nav-link:hover {
    background-color: #eb5e28;
}


/* 헤더 끝 index.jsp 시작  */
#movie_ct{
   margin-top: 20px;
   margin-bottom: 20px;
}

div#content{
   width:100%;
   border:solid 1px blue;
   margin-top:0px;
    width: 90%; /* 부모 요소 너비의 90% */
   max-width: 980px; /* 최대 너비를 980px로 제한 */
   min-width: 980px;
   margin: 0 auto; /* 가운데 정렬 */
   padding: 0px; /* 좌우 여백 */
   box-sizing: border-box; /* padding 포함 계산 */
}

.card {
    width: 170px;
    height: 234px;
    border: none; /* 카드 테두리 없애기 */
}

.first-card {
    padding-left: 4px !important;
}

#cardCarousel {
    position: relative; /* carousel의 부모 요소에 상대적 위치를 지정 */
}

#cardCarousel > a.carousel-control-next > span.carousel-control-next-icon,
#cardCarousel > a.carousel-control-prev > span.carousel-control-prev-icon {
    position: absolute;
    top: 50%; /* 버튼이 세로 가운데에 오도록 */
    transform: translateY(-50%); /* 정확하게 세로 중앙에 위치시키기 위한 변환 */
}

#cardCarousel > a.carousel-control-prev > span.carousel-control-prev-icon {
    left: -10; /* 왼쪽 끝에 위치 */
}

#cardCarousel > a.carousel-control-next > span.carousel-control-next-icon {
    right: -10; /* 오른쪽 끝에 위치 */
}




#cardCarousel2 {
    position: relative; /* carousel의 부모 요소에 상대적 위치를 지정 */
}

#cardCarousel2 > a.carousel-control-next > span.carousel-control-next-icon,
#cardCarousel2 > a.carousel-control-prev > span.carousel-control-prev-icon {
    position: absolute;
    top: 50%; /* 버튼이 세로 가운데에 오도록 */
    transform: translateY(-50%); /* 정확하게 세로 중앙에 위치시키기 위한 변환 */
}

#cardCarousel2 > a.carousel-control-prev > span.carousel-control-prev-icon {
    left: -10; /* 왼쪽 끝에 위치 */
}

#cardCarousel2 > a.carousel-control-next > span.carousel-control-next-icon {
    right: -10; /* 오른쪽 끝에 위치 */
}




/* 공지사항 */
div#notis {
  margin: 0 auto;
  margin-top: 60px;
  margin-bottom: 100px;
  display:flex;
  width: 95%;
  height: 223px;
  /*  border: solid 1px gray;*/
  border-radius: 10px; 
}

div#notis>#left {
  width: 60%;
  padding-left:2%;
  padding-top:2%;
  height: 100%;
  border: solid 1px gray;
  border-top-left-radius: 10px;   /* 왼쪽 위 모서리 둥글게 */
  border-bottom-left-radius: 10px; /* 왼쪽 아래 모서리 둥글게 */
}
div#notis>#right {
  width: 40%;
  height: 100%;
  border: solid 1px gray;
  border-top-right-radius: 10px;   /* 왼쪽 위 모서리 둥글게 */
  border-bottom-right-radius: 10px; /* 왼쪽 아래 모서리 둥글게 */
}



/* footer  */
div#footer{
   width:100%;
   height: 170px;
   background-color:#CCC5B9;
}
div#footer_info{
max-width: 980px;
   mix-width: 980px; /* 최대 너비를 980px로 제한 */
   margin-right: auto; /* 가운데 정렬 */
   padding: 0px; /* 좌우 여백 */
   box-sizing: border-box; /* padding 포함 계산 */
   text-align: left;
   border:solid 1px red;
   
   margin: 0 auto; /* 가운데 정렬 */
   padding: 0px; /* 좌우 여백 */
   box-sizing: border-box; /* padding 포함 계산 */
   white-space: nowrap; /* 텍스트가 자동으로 줄 바꿈되지 않도록 */
}
#footer_info>p{
padding-left: 25px;
padding-top: 25px;
color:#252422;
}

</style>
</head>
<body>
<div id="adver">광고영역</div>

<%-- 스크롤 내리면 나타나는 네브바  시작 --%>

<%-- 스크롤 내리면 나타나는 네브바  시작 --%>

   <!-- 상단 네비게이션 -->
<div class="container">
    <div id="header">
        <div id="logo" class="mb-4">logo</div>
        <div id="web_name" class="mb-4">사이트명</div>
	        <div>
	        	<a href="<%= ctxPath %>/admin/admin.up" >관리자</a>
				<a href="<%= ctxPath %>/mypage/mypage.up" >마이페이지</a>
	        </div>
        <ul class="mb-4">
            <li><div>icon</div><a class="navbar-brand custom_a" href="<%= ctxPath %>/login/login.up" >로그인</a></li>
			<li><div>icon</div><a class="navbar-brand custom_a" href="<%= ctxPath %>/login/login.up" >회원가입</a></li>
			<li><div>icon</div><a class="navbar-brand custom_a" href="<%= ctxPath %>/notice/notice.up" >공지사항</a></li>
        </ul>
    </div>
</div>

<!-- Sticky 네비게이션 바 -->
<div class="nav sticky-nav">
    <div id="nav_mediatop">
        <ul class="nav-list">
            <li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieList.up">영화</a></li>
			<li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieTime.up">상영시간표</a></li>
			<li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/movie/movieDetail.up">영화상세보기</a></li>
			<li class="nav-item"><a class="nav-link custom_link" href="<%= ctxPath %>/reservation/reservation.up">영화예약</a></li>
        </ul>
    </div>
</div>