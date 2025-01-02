<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String ctxPath = request.getContextPath();
%>
<% String userid = (String) session.getAttribute("userid"); %>

<jsp:include page="../header1.jsp" />

<%-- 메인 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<%-- 영화 버튼 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mypagemovie.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mymovielike.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage/mymovielike.js"></script> 

<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>



<%-- 전체 창 --%>
<div class="my_container">
	<%-- 마이페이지 나의 프로필장 --%>
	<div class="myprofile">
		<div class="profile-container">
			<i class="fa-solid fa-circle-user" style="color: #252422;"></i>
			<%-- 사용자 정보 --%>
			<div class="profile-info">
				<h2>${(sessionScope.loginuser).name}님</h2>
				<p>
					나의 영화 랭킹 <strong>50</strong> 순위
				</p>
				<p>
					사용 가능 포인트: <strong>${(sessionScope.loginuser).point}pt</strong>
				</p>
				<p>
					사용한 포인트: <strong>0pt</strong>
				</p>
			</div>
			<%-- 사용자 정보 끝 --%>
		</div>
	</div>
	<%-- 마이페이지 나의 프로필장 끝 --%>


	<%-- 마이페이지 사이드바 & 매안 창 --%>
	<div class="my_main">

		<%-- 마이페이지 사이드바 --%>
		<div class="my_hside">
			<ul>
				<li><a href="<%=ctxPath%>/mypage/mypage.mp">MyPage HOME</a></li>

				<li><a href="<%=ctxPath%>/mypage/myreservationlist.mp">나의
						예매내역</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/myreservationpoint.mp">포인트
								적립/사용 내역</a></li>
					</ul></li>

				<li><a href="<%=ctxPath%>/mypage/mymoviewatched.mp">영화</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/mymoviewatched.mp">내가 본
								영화</a></li>
						<li><a href="<%=ctxPath%>/mypage/mymoviereview.mp">내가 쓴 평점</a></li>
						<li><a href="<%=ctxPath%>/mypage/mymovielike.mp"
							class="active">기대되는 영화</a></li>
					</ul></li>

				<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보수정</a></li>
						<li><a href="<%=ctxPath%>/mypage/mydelete.mp">회원탈퇴</a></li>
					</ul></li>
			</ul>
		</div>
		<%-- 마이페이지 사이드바 끝 --%>

		<%-- 메인콘텐츠 시작 --%>
		<div class="mypage_main_content">

			<!-- 영화 버튼 바 시작 -->
			<div class="my_movie_buttons">
				<a href="<%=ctxPath%>/mypage/mymoviewatched.mp" class="button">
					<i class="fa-solid fa-feather"></i> 내가 본 영화
				</a> <a href="<%=ctxPath%>/mypage/mymoviereview.mp" class="button"> <i
					class="fa-solid fa-star"></i> 내가 쓴 평점
				</a> <a href="<%=ctxPath%>/mypage/mymovielike.mp" class="button active">
					<i class="fa-solid fa-film"></i>기대되는 영화
				</a>
			</div>
			<%-- 영화 버튼 바 끝 --%>

			<!-- 기대되는 영화 -->
    <div>
      <div class="row" id="mymovielikeHIT" ></div>
        <input type="hidden" name="userid" id="userid"
					value="${sessionScope.loginuser.userid}" />
					
					
			<div class="col-12 mt-4">
            <p class="text-center">
                <span id="end" class="end-message" style="display:block; margin:20px; font-size: 14pt; font-weight: bold; color: red;"></span>
                <button type="button" class="btn-load-more btn btn-secondary btn-lg" id="btnmymovielike" value="">더보기...</button>
                <span id="totalmymovielike">${requestScope.totalmymovielike}</span>
                <span id="mymovielikecount">0</span>
            </p>
        </div>
</div>


			<%-- 기대되는 영화 끝 --%>






		</div>
		<!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>






<jsp:include page="../footer1.jsp" />
