<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
String ctxPath = request.getContextPath();
%>

<jsp:include page="../header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js"
	crossorigin="anonymous"></script>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage/mypage.js"></script> 

<%-- 전체 창 --%>
<div class="my_container">

	<jsp:include page="mypageProfile.jsp" />

	<%-- 마이페이지 사이드바 & 매안 창 --%>
	<div class="my_main">

		<%-- 마이페이지 사이드바 --%>
		<div class="my_hside">
			<ul>
				<li><a href="<%=ctxPath%>/mypage/mypage.mp" class="active">MyPage
						HOME</a></li>

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
						<li><a href="<%=ctxPath%>/mypage/mymoviereview.mp">내가 쓴
								평점</a></li>
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
		
		
		

			<!-- 나의 예매내역 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/mypage/myreservationlist.mp"
						class="my_section_title">나의 예매내역</a> <a
						href="<%=ctxPath%>/mypage/myreservationlist.mp"><i
						class="fa-solid fa-square-plus"> </i></a>
				</h3>

				<div class="my_box_list">
					<c:if
						test="${not empty requestScope.main_mypage_Myreservationlist}">
						<ul class="mypage_main_List">
							<c:forEach var="reservation"
								items="${requestScope.main_mypage_Myreservationlist}">
								<li class="mypage_main_List_card">

									<div class="sect-viw-rated">

										<!-- 예매번호 -->
										<p class="reservation_number">예매번호: ${fn:replace(reservation.imp_uid, 'imp_', '')}
											(${reservation.pay_success_date})</p>

										<!-- 포스터 이미지 -->
										<div class="mypage_main_List_poster">
											<a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${reservation.svo.fk_seq_movie_no}">
												<img
												src="${pageContext.request.contextPath}/images/admin/poster_file/${reservation.svo.mvo.poster_file}"
												alt="${reservation.svo.mvo.movie_title}" />
											</a>
										</div>

										<!-- 영화제목, 영화총가격, 관람일자, 관람좌석 -->
										<div class="reservation_details">
											<p class="reservation_id">
												${reservation.svo.mvo.movie_title}
												<fmt:formatNumber value="${reservation.pay_amount}" pattern="#,###" />원</p>
											<p class="movie_info">관람일시 ${reservation.svo.start_time}</p>
											<p class="theater_info">관람좌석
												${reservation.tvo.seat_no_list}</p>
										</div>

										<!-- 버튼 -->
										<div class="reservation_actions">
											<button type="button" class="Receipt_Printing"  onclick="Receipt_Printing('${reservation.imp_uid}','<%=ctxPath%>' )">영수증 출력</button>
											<div id="Receipt_Printing_model"></div>
										</div>

									</div>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					
					<c:if test="${empty requestScope.main_mypage_Myreservationlist}">
						<p>예매내역이 없습니다.</p>
					</c:if>
					
				</div>
			</div>



			<!-- 내가 본 영화 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/mypage/mymoviewatched.mp"
						class="my_section_title">내가 본 영화</a> <a
						href="<%=ctxPath%>/mypage/mymoviewatched.mp"><i
						class="fa-solid fa-square-plus "> </i></a>
				</h3>

				<div class="my_box_list">
					<c:if test="${not empty requestScope.main_mypage_MovieWatchedList}">
						<ul class="mypage_main_List">
							<c:forEach var="watched" items="${requestScope.main_mypage_MovieWatchedList}">
							
							<li class="mypage_main_List_card">

									<div class="sect-viw-rated">
							
							
							<!-- 포스터 이미지 -->
							<div class="mypage_main_List_poster">
								<a
									href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${watched.svo.fk_seq_movie_no}">
									<img
									src="${pageContext.request.contextPath}/images/admin/poster_file/${watched.svo.mvo.poster_file}"
									alt="${watched.svo.mvo.movie_title}" />
								</a>
							</div>
							
							<!-- 영화제목, 영화총가격, 관람인원, 관람일자, 관람좌석, 상영관, 매수 -->
							<div class="moviewatched_details">
								<p>${watched.svo.mvo.movie_title}</p>
								<ul>
								<li>${watched.svo.start_time}~${watched.svo.end_time}</li>
								<li>${watched.svo.fk_screen_no}관/ ${watched.tvo.seat_no_list}</li>
								<li> ${watched.tvo.seat_count}명</li>
							</ul>
							</div>
							</div>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.main_mypage_MovieWatchedList}" >
						<p class="empty">본 영화가 없습니다.</p>
					</c:if>
				</div>
			</div>






			<!-- 내가 쓴 평점 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/mypage/mymoviereview.mp"
						class="my_section_title">내가 쓴 평점</a> <a
						href="<%=ctxPath%>/mypage/mymoviereview.mp"><i
						class="fa-solid fa-square-plus"> </i></a>
				</h3>
				<div class="my_box_list">

					<c:if test="${not empty requestScope.main_mypage_MovieReviewList}">
						<ul class="mypage_main_List">
							<c:forEach var="mrvo"
								items="${requestScope.main_mypage_MovieReviewList}"
								varStatus="status">
								<li class="mypage_main_List_card">

									<div class="sect-viw-rated">

										<!-- 포스터 이미지 -->
										<div class="mypage_main_List_poster">
											<a
												href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${mrvo.fk_seq_movie_no}">
												<img
												src="${pageContext.request.contextPath}/images/admin/poster_file/${mrvo.mvo.poster_file}"
												alt="${mrvo.mvo.movie_title}" />
											</a>
										</div>

										<!-- 리뷰 정보 -->
										<div class="mypage_main_List_moviereview_box">

											<div class="mypage_main_List_movieReview_title">
												<strong>${mrvo.mvo.movie_title}</strong>
											</div>
											<ul>
												<li>${mrvo.fk_user_id}</li>
												<li>${mrvo.review_write_date}</li>
											</ul>
											<div class="movie-rating">
												<c:forEach var="i" begin="1" end="5">
													<span
														style="color: ${i <= mrvo.movie_rating ? '#eb5e28' : '#E0E0E0'};">&#9733;</span>
													<%-- 평점 별점 --%>
												</c:forEach>
											</div>
											<p class="review_date">${mrvo.review_content}</p>

										</div>
									</div>

								</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.main_mypage_MovieReviewList}">
						<p class="empty">작성한 평점이 없습니다.</p>
					</c:if>
				</div>
			</div>







			<!-- 기대되는 영화 -->
			<div class="mypage_box_css">
				<h3>
					<a href="<%=ctxPath%>/mypage/mymovielike.mp"
						class="my_section_title">기대되는 영화</a> <a
						href="<%=ctxPath%>/mypage/mymovielike.mp"><i
						class="fa-solid fa-square-plus"> </i></a>
				</h3>

				<div class="my_box_list">

					<c:if test="${not empty requestScope.main_mypage_MovieLikeList}">
						<ul class="mypage_main_List">
							<c:forEach var="mlvo"
								items="${requestScope.main_mypage_MovieLikeList}"
								varStatus="status">
								<li class="mypage_main_List_card">

									<div class="sect-viw-rated">

										<!-- 포스터 이미지 -->
										<div class="mypage_main_List_poster">
											<a
												href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${mlvo.FK_SEQ_MOVIE_NO}">
												<img
												src="${pageContext.request.contextPath}/images/admin/poster_file/${mlvo.mvo.poster_file}"
												alt="${mlvo.mvo.movie_title}" />
											</a>
										</div>

										<!-- 영화 정보 -->
										<div class="mypage_main_List_movieLike_title">
											<strong>${mlvo.mvo.movie_title}</strong>
											<p>${mlvo.mvo.start_date}</p>
											<div class="text-center">
												<a
													href="/MovieProject/reservation/reservation.mp?seq_movie_no?seq_movie_no=${mlvo.FK_SEQ_MOVIE_NO}"
													class="btn mybtnlike" role="button">예매하기</a>
											</div>
										</div>

									</div>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty requestScope.main_mypage_MovieLikeList}">
						<p>기대되는 영화가 없습니다.</p>
					</c:if>
				</div>
			</div>







		</div>
		<!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>



<jsp:include page="../footer1.jsp" />