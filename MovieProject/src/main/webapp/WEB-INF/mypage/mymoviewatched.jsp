<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
String ctxPath = request.getContextPath();
%>
<jsp:include page="../header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemovie.css" />
	<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mymoviewatched.css" />
<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js"
	crossorigin="anonymous"></script>


<%-- 전체 창 --%>
<div class="my_container">

	<jsp:include page="mypageProfile.jsp" />
	
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
						<li><a href="<%=ctxPath%>/mypage/mymovielike.mp">기대되는 영화</a></li>
					</ul></li>

				<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보수정</a></li>
						<li><a href="<%=ctxPath%>/mypage/mydelete.mp">회원탈퇴</a></li>
					</ul></li>
			</ul>
		</div>
		<%-- 마이페이지 사이드바 끝 --%>

		<%-- 메인 콘텐츠 시작 --%>
		<div class="mypage_main_content">
		
		<%-- 영화 버튼바 --%>
			<div class="my_movie_buttons">
			<a href="<%=ctxPath%>/mypage/mymoviewatched.mp" class="button active">
					<i class="fa-solid fa-feather"></i> 내가 본 영화
				</a> 
				<a href="<%=ctxPath%>/mypage/mymoviereview.mp" class="button">
					<i class="fa-solid fa-star"></i> 내가 쓴 평점
				</a>
				<a href="<%=ctxPath%>/mypage/mymovielike.mp" class="button"> <i
					class="fa-solid fa-film"></i> 기대되는 영화
				</a> 
			</div>
			<!-- 영화 버튼바 끝 -->
			
			<!-- 내가 본 영화  -->
			<div class="my_main_movie">
			<div class="my_mywatchedmovie_list">
					<c:if test="${not empty requestScope.mymoviewatchedList}">
						
							<c:forEach var="watched" items="${requestScope.mymoviewatchedList}">
							<li class="my_main_watchedmovie_card">
							<!-- 포스터 이미지 -->
							<div class="my_main_moviewatchedList_poster">
								<a
									href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${watched.svo.fk_seq_movie_no}">
									<img src="<%= ctxPath%>/images/admin/poster_file/${watched.svo.mvo.poster_file}.jpg" alt="영화 포스터"/>
								</a>
							</div>
							
							<!-- 영화제목, 관람인원, 관람일자, 관람좌석, 상영관, 매수 -->
							<div class="moviewatched_details">
								<h2 class="moviewatched_h2">
								<a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${watched.svo.fk_seq_movie_no}">
								${watched.svo.mvo.movie_title}</a> </h2>
								<ul>
								<li>${watched.svo.start_time}~${watched.svo.end_time}</li>
								<li>${watched.svo.fk_screen_no}관/ ${watched.tvo.seat_no_list}</li>
								<li> ${watched.tvo.seat_count}명</li></ul>
							</div>
							</li>
							</c:forEach>
					</c:if>
					<c:if test="${empty requestScope.mymoviewatchedList}" >
						<p class="empty">본 영화가 없습니다.</p>
					</c:if>
				</div>
			</div><!-- 내가 본 영화 리스트  끝 -->
			
		</div><!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>

<jsp:include page="../footer1.jsp" />