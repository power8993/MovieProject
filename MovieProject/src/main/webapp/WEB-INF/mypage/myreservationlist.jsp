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
	href="<%=ctxPath%>/css/mypage/myreservationlist.css" />
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

				<li><a href="<%=ctxPath%>/mypage/myreservationlist.mp"
					class="active">나의 예매내역</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/myreservationpoint.mp">포인트
								적립/사용 내역</a></li>
					</ul></li>

				<li><a href="<%=ctxPath%>/mypage/mywatchedmovie.mp">영화</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/mywatchedmovie.mp">내가 본
								영화</a></li>
						<li><a href="<%=ctxPath%>/mypage/myreview.mp">내가 쓴 평점</a></li>
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
				<c:if test="${not empty requestScope.reservationList}">
					<c:forEach var="reservation"
						items="${requestScope.reservationList}">
						<div class="reservation_item">
							<div class="poster">포스터</div>
							<div class="details">
								<p>예매번호: ${reservation.id}</p>
								<p>${reservation.movieTitle}|${reservation.date}</p>
								<p>상영관: ${reservation.theater}</p>
							</div>
							<button type="button" class="btn">영수증 출력</button>
                            <button type="button" class="btn">예매 취소</button>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty requestScope.reservationList}">
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
							<c:forEach var="cancel" items="${cancelList}">
								<tr>
									<td>${cancel.movieTitle}</td>
									<td>${cancel.dateTime}</td>
									<td>${cancel.cancelDate}</td>
									<td>${cancel.price}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<c:if test="${empty requestScope.cancelList}">
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