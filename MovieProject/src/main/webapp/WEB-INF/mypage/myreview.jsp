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
						<li><a href="<%=ctxPath%>/mypage/mywatchedmovie.mp">내가 본
								영화</a></li>
						<li><a href="<%=ctxPath%>/mypage/myreview.mp" class="active">내가
								쓴 평점</a></li>
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

		<!-- 메인 콘텐츠 -->
		<div class="mypage_main_content">
			<div class="my_movie_buttons">
				<a href="<%=ctxPath%>/mypage/mywatchedmovie.mp" class="button">
					<i class="fa-solid fa-feather"></i> 내가 본 영화
				</a> <a href="<%=ctxPath%>/mypage/myreview.mp" class="button active">
					<i class="fa-solid fa-star"></i> 내가 쓴 평점
				</a> <a href="<%=ctxPath%>/mypage/mymovielike.mp" class="button"> <i
					class="fa-solid fa-film"></i> 기대되는 영화
				</a>
			</div>

			<div class="my_main_movie">
				<!-- 내가 쓴 평점  -->
				<div class="my_mywatchedmovie_list">
					<c:if test="${not empty requestScope.REVIEW_CONTENT}">
						<ul>
							<c:forEach var="my_review" items="${requestScope.REVIEW_CONTENT}">
								<li>${review.POSTER_FILE}</li>
								<li>영화:${review.MOVIE_TITLE}-평점:${review.MOVIE_RATING}</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.reviews}">
						<p class="empty">작성한 평점이 없습니다.</p>
					</c:if>
				</div>
				
			</div>
			<!-- 내가 쓴 평점  끝 -->
			
		</div>
		<!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>

<jsp:include page="../footer1.jsp" />