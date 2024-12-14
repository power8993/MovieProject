<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String ctxPath = request.getContextPath();
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>

<jsp:include page="../header1.jsp" />

<%-- 전체 창 --%>
<div class="my_container">
	<%-- 마이페이지 나의 프로필장 --%>
	<div class="myprofile"></div>
	<%-- 마이페이지 사이드바 & 매안 창 --%>
	<div class="my_main">

		<%-- 마이페이지 사이드바 --%>
		<div class="my_hside">
			<ul>
				<li><a href="" class="active">MyPage HOME</a></li>

				<li><a href="">나의 예매내역</a>
					<ul>
						<li><a href="">포인트 적립/사용 내역</a></li>
					</ul></li>

				<li><a href="">영화</a>
					<ul>
						<li><a href="">내가 본 영화</a></li>
						<li><a href="">내가 쓴 평점</a></li>
						<li><a href="">기대되는 영화</a></li>
					</ul></li>

				<li><a href="">회원정보</a>
					<ul>
						<li><a href="">회원정보수정</a></li>
						<li><a href="">회원탈퇴</a></li>
					</ul></li>
			</ul>
		</div>
		<%-- 마이페이지 사이드바 끝 --%>

		<!-- 메인 콘텐츠 -->
		<div class="mypage_main_content">

			<!-- 나의 예매내역 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/reservation/list.jsp"
						class="my_section_title">나의 예매내역</a> <a
						href="<%=ctxPath%>/reservation/list.jsp"><i
						class="fa-solid fa-square-plus"
						style="color: #ccc5b9; margin-right: 8px;"> </i></a>
				</h3>
				<div class="my_box_list">
					<c:if test="${not empty requestScope.reservationList}">
						<c:forEach var="reservation"
							items="${requestScope.reservationList}">
							<div class="reservation-item">
								<div class="poster">포스터</div>
								<div class="details">
									<p>예매번호: ${reservation.id}</p>
									<p>${reservation.movieTitle}|${reservation.date}</p>
									<p>${reservation.theater}</p>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty requestScope.reservationList}">
						<p>예매내역이 없습니다.</p>
					</c:if>
				</div>
			</div>

			<!-- 기대되는 영화 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/movies/expected.jsp" class="my_section_title">기대되는
						영화</a> <a href="<%=ctxPath%>/movies/expected.jsp"><i
						class="fa-solid fa-square-plus"
						style="color: #ccc5b9; margin-right: 8px;"> </i></a>
				</h3>
				<div class="my_box_list">
					<c:if test="${not empty requestScope.expectedMovies}">
						<ul>
							<c:forEach var="movie" items="${requestScope.expectedMovies}">
								<li>${movie.title}</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.expectedMovies}">
						<p>기대되는 영화가 없습니다.</p>
					</c:if>
				</div>
			</div>

			<!-- 내가 본 영화 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/movies/watched.jsp" class="my_section_title">내가
						본 영화</a> <a href="<%=ctxPath%>/movies/watched.jsp"><i
						class="fa-solid fa-square-plus"
						style="color: #ccc5b9; margin-right: 8px;"> </i></a>
				</h3>
				<div class="my_box_list">
					<c:if test="${not empty requestScope.watchedMovies}">
						<ul>
							<c:forEach var="movie" items="${requestScope.watchedMovies}">
								<li>${movie.title}(${movie.dateWatched})</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.watchedMovies}">
						<p>본 영화가 없습니다.</p>
					</c:if>
				</div>
			</div>

			<!-- 내가 쓴 평점 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/reviews/list.jsp" class="my_section_title">내가
						쓴 평점</a> <a href="<%=ctxPath%>/reviews/list.jsp"><i
						class="fa-solid fa-square-plus"
						style="color: #ccc5b9; margin-right: 8px;"> </i></a>
				</h3>
				<div class="my_box_list">
					<c:if test="${not empty requestScope.reviews}">
						<ul>
							<c:forEach var="review" items="${requestScope.reviews}">
								<li>${review.movieTitle}-평점:${review.rating}</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.reviews}">
						<p>작성한 평점이 없습니다.</p>
					</c:if>
				</div>
			</div>





		</div><!-- 메인 콘텐츠 끝 -->

	</div><%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div><%-- 전체 창 끝 --%>






<jsp:include page="../footer1.jsp" />