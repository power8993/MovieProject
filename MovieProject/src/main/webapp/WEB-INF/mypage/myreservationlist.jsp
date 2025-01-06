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
<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/myreservationlist.css" />
<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js"
	crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage/myreservationlist.js"></script> 


<%-- 전체 창 --%>
<div class="my_container">

	<jsp:include page="mypageProfile.jsp" />

	<%-- 마이페이지 사이드바 & 매안 창 --%>
	<div class="my_main">

		<%-- 마이페이지 사이드바 --%>
		<div class="my_hside">
			<ul>
				<li><a href="<%=ctxPath%>/mypage/mypage.mp">MyPage HOME</a></li>

				<li><a href="<%=ctxPath%>/mypage/myreservationlist.mp"
					class="active">나의 예매내역</a>
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
			<div class="my_box_list">
				<h3 class="title0">나의 예매내역</h3>
				<p class="title1">
					지난 <strong>1개월까지</strong>의 예매내역을 확인하실 수 있으며, 영수증은 <strong>신용카드
						내역</strong>만 출력 가능합니다.
				</p>
				<h3 class="title2">
					현장에서 발권하실 경우 꼭 <strong>예매번호</strong>를 확인하세요.
				</h3>
				<p class="title2">티켓판매기에 예매번호를 입력하면 티켓을 발급받을 수 있습니다.</p>

				<c:if test="${not empty requestScope.myreservationList}">
					<c:forEach var="reservation"
						items="${requestScope.myreservationList}">
						<div class="reservation_box">

							<!-- 예매번호 -->
							 <p class="reservation_number">예매번호 ${reservation.imp_uid}</p>

							<!-- 포스터 이미지 -->
							<div class="my_main_reservationlist_poster">
								<a
									href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${reservation.svo.fk_seq_movie_no}">
									<img
									src="${pageContext.request.contextPath}/images/admin/poster_file/${reservation.svo.mvo.poster_file}"
									alt="${reservation.svo.mvo.movie_title}" />
								</a>
							</div>
							
							
							<!-- 영화제목, 영화총가격, 관람인원, 관람일자, 관람좌석, 상영관, 매수 -->
							<div class="reservation_details">
								<h2 class="reservation_h2"><a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${reservation.svo.fk_seq_movie_no}">${reservation.svo.mvo.movie_title}</a> <span>${reservation.pay_amount}원</span> </h2>
								<ul>
								<li><dl><dt>관람일시</dt> <dd class="start_time">${reservation.svo.start_time}</dd></dl></li>
								<li><dl><dt>관람좌석</dt> <dd> ${reservation.tvo.seat_no_list}</dd></dl></li>
								<li><dl><dt>상영관</dt> <dd> ${reservation.svo.fk_screen_no}관</dd></dl></li>
								<li><dl><dt>매수</dt> <dd> ${reservation.tvo.seat_count}매</dd></dl></li>
							</ul>
							</div>
							<!-- 버튼 -->
							<div class="reservation_actions">
								<a href="javascript:Receipt_Printing('${(sessionScope.loginuser).userid}','<%=ctxPath%>')">영수증 출력</a>
								<button type="button" class="Cancel_Reservation" onclick="myreservation_cancel()">예매 취소</button>
								<div id="myreservation_cancel_modal"></div>  <!-- 모달을 삽입할 위치 -->
							</div>	
									
										
						</div>
					</c:forEach>
				</c:if>

				<c:if test="${empty requestScope.myreservationList}">
					<p class="empty-message">고객님의 최근 예매내역이 존재하지 않습니다.</p>
				</c:if>
			</div>





			<!-- 취소내역 리스트 -->
			<div class="my_box_list">
				<div class="title">
					<h4>나의 취소내역</h4>
					<p>상영일 기준 지난 7일 동안의 취소내역입니다.</p>
				</div>
				<div class="movie_status">
					<table>
						<thead>
							<tr>
								<th>영화제목</th>
								<th>관람일시</th>
								<th>취소일</th>
								<th>결제취소 금액</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty requestScope.myreservationList_cancel}">
								<c:forEach var="cancel"
									items="${requestScope.myreservationList_cancel}">
									<tr>
										<td>${cancel.svo.mvo.movie_title}</td>
										<td>${cancel.svo.start_time}</td>
										<td>${cancel.pay_cancel_date}</td>
										<td>${cancel.pay_amount}</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>

				<c:if test="${empty requestScope.myreservationList_cancel}">
					<p class="empty-message">고객님의 최근 취소내역이 없습니다.</p>
				</c:if>
			</div>


		</div>
		<!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>
<jsp:include page="../footer1.jsp" />