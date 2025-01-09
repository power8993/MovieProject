<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String ctxPath = request.getContextPath();
%>
<jsp:include page="../header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<%-- 영화 버튼바 css --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemovie.css" />
<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mymoviereview.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage/mymoviereview.js"></script> 

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
						<li><a href="<%=ctxPath%>/mypage/mymoviereview.mp" class="active">내가 쓴 평점</a></li>
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
		
		
			<%-- 영화 버튼바 시작 --%>
			<div class="my_movie_buttons">
				<a href="<%=ctxPath%>/mypage/mymoviewatched.mp" class="button">
					<i class="fa-solid fa-feather"></i> 내가 본 영화
				</a> <a href="<%=ctxPath%>/mypage/mymoviereview.mp" class="button active">
					<i class="fa-solid fa-star"></i> 내가 쓴 평점
				</a> <a href="<%=ctxPath%>/mypage/mymovielike.mp" class="button"> <i
					class="fa-solid fa-film"></i> 기대되는 영화
				</a>
			</div>
			<%-- 영화 버튼바 끝 --%>




 			<!-- 내가 쓴 평점  -->
 			<div class="my_main_movie">
 			
					<c:if test="${not empty requestScope.mymoviereviewList}">
					<ul class="my_moviereview_List">
						<c:forEach var="mrvo" items="${requestScope.mymoviereviewList}"
							varStatus="status">
							<li class="my_main_moviereview_card">
							
							<div class = "sect-viw-rated">
							
							<!-- 포스터 이미지 -->
								<div class="my_main_moviereview_poster">
								<a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${mrvo.fk_seq_movie_no}" >
									<img 
										src="${pageContext.request.contextPath}/images/admin/poster_file/${mrvo.mvo.poster_file}"
										alt="${mrvo.mvo.movie_title}"/>
								</a>					
								</div>
								
								 <!-- 리뷰 정보 -->
									<div class="my_main_moviereview_box">

										<div class="my_moviereview_title">
											<strong id="movie_title_${mrvo.seq_review_no}"
												class="movie_title">${mrvo.mvo.movie_title}</strong>
										</div>
										<ul>
											<li>${mrvo.fk_user_id}|</li>
											<li>${mrvo.review_write_date}</li>
										</ul>
										<!-- 삭제 버튼 -->
										<button type="button" class="mymoviereview_delete_btn"
											onclick="mymoviereview_delete(event, ${mrvo.seq_review_no})">×</button>



										<%-- 별점 --%>
										<div id="movie_rating_${mrvo.seq_review_no}" class="movie_rating" data-rating="${mrvo.movie_rating}">
										    <c:forEach var="i" begin="1" end="5">
										        <i class="fa-solid fa-star" 
										           data-rating="${i}" 
										           style="color: ${i <= mrvo.movie_rating ? '#eb5e28' : '#E0E0E0'}; cursor: pointer;" 
										           onclick="setRating(this)">
										        </i>
										    </c:forEach>
										</div>





										<p id="review_content_${mrvo.seq_review_no}"
											class="review_content">${mrvo.review_content}</p>

									</div>

									<!-- 수정 버튼 -->
									<button type="button" class="mymoviereview_update_btn"
										onclick="mymoviereview_update(${mrvo.seq_review_no})">수정하기</button>
										<div id="mymoviereview_update_modal"></div>  <!-- 모달을 삽입할 위치 -->
								</div>
									
							</li>
						</c:forEach>
					</ul>
				</c:if>
					<c:if test="${empty requestScope.mymoviereviewList}">
						<p class="empty">작성한 평점이 없습니다.</p>
					</c:if>
					
				</div>
		<!-- 내가 쓴 평점 끝  -->


		</div>
		<!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>

<jsp:include page="../footer1.jsp" />