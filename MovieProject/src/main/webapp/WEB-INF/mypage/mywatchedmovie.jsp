<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String ctxPath = request.getContextPath();
%>


<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemovie.css" />
<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js"
	crossorigin="anonymous"></script>

<jsp:include page="../header1.jsp" />

<%-- 전체 창 --%>
<div class="my_container">
	<%-- 마이페이지 나의 프로필장 --%>
	<div class="myprofile">
        <div class="profile-container">
             <i class="fa-solid fa-circle-user" style="color: #252422;"></i>
            
            <!-- 사용자 정보 -->
            <div class="profile-info">
                <h2>회원 님</h2>
                <p>나의 영화 랭킹 <strong>50</strong> 순위</p>
                <p>사용 가능 포인트: <strong>0pt</strong></p>
                <p>사용한 포인트: <strong>0pt</strong></p>
            </div>
        </div>
    </div>
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

				<li><a href="<%=ctxPath%>/mypage/mywatchedmovie.mp">영화</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/mywatchedmovie.mp"
							class="active">내가 본 영화</a></li>
						<li><a href="<%=ctxPath%>/mypage/myreview.mp">내가 쓴 평점</a></li>
						<li><a href="<%=ctxPath%>/mypage/mymovielike.mp">기대되는
								영화</a></li>
					</ul></li>

				<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보수정</a></li>
						<li><a href="<%=ctxPath%>/mypage/mydelete.mp">회원탈퇴</a></li>
					</ul></li>
			</ul>
		</div>
		<%-- 마이페이지 사이드바 끝 --%>

		<!-- 메인 콘텐츠 -->
		<div class="mypage_main_content">
			<div class="my_movie_buttons"><!-- 메인 영화 콘텐츠 버튼 -->
			<a href="<%=ctxPath%>/mypage/mywatchedmovie.mp" class="button active">
					<i class="fa-solid fa-feather"></i> 내가 본 영화
				</a> 
				<a href="<%=ctxPath%>/mypage/myreview.mp" class="button">
					<i class="fa-solid fa-star"></i> 내가 쓴 평점
				</a>
				<a href="<%=ctxPath%>/mypage/mymovielike.mp" class="button"> <i
					class="fa-solid fa-film"></i> 기대되는 영화
				</a> 
			</div><!-- 메인 영화 콘텐츠 버튼 끝 -->
			<div class="my_main_movie"><!-- 내가 본 영화 리스트  -->
			<div class="my_mywatchedmovie_list">
					<c:if test="${not empty requestScope.watchedMovies}">
						<ul>
							<c:forEach var="movie" items="${requestScope.watchedMovies}">
							<li>${movies.POSTER_FILE}</li>
								<li>영화:${movies.MOVIE_TITLE}</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.watchedMovies}" >
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