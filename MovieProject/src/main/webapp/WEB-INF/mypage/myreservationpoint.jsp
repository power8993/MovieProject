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
	href="<%=ctxPath%>/css/mypage/myreservationpoint.css" />

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
						<li><a href="<%=ctxPath%>/mypage/myreservationpoint.mp"
							class="active">포인트 적립/사용 내역</a></li>
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
			<div class="my_h2">
				<h2>HGV 포인트</h2>
				<p>포인트 적립/사용 내역</p>
			</div>
			<div class="movie_point">
				<table>
					<thead>
						<tr>
							<th>구분</th>
							<th>내용</th>
						</tr>
					<tbody>
						<c:forEach var="point" items="${requestScope.myreservationpoint}">
							<tr>
								<td>적립 내역</td>
								<td><fmt:formatNumber value="${point.total_earned}" pattern="#,###" />pt</td>
							</tr>
							<tr>
								<td>사용 내역</td>
								<td><fmt:formatNumber value="${point.total_deducted}" pattern="#,###" />pt</td>
							</tr>
							<tr>
								<td>총 포인트 내역</td>
								<td><fmt:formatNumber value="${point.total_points}" pattern="#,###" />pt</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>


			<div class="my_point_list">
				<table>
					<thead>
						<tr>
							<th>적립/사용</th>
							<th>포인트</th>
							<th>날짜</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty requestScope.myreservationpointList}">
							<c:forEach var="pointList"
								items="${requestScope.myreservationpointList}">
								<tr>
									<td><c:choose>
											<c:when test="${pointList.point_type == 0}">사용</c:when>
											<c:when test="${pointList.point_type == 1}">적립</c:when>
										</c:choose></td>
									<td><fmt:formatNumber value="${pointList.point}"
											pattern="#,###" /></td>
									<td>${pointList.point_date}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>

				<c:if test="${empty requestScope.myreservationpointList}">
					<p class="empty-message">고객님의 최근 포인트내역이 없습니다.</p>
				</c:if>
			</div>





		</div>
		<!-- 메인 콘텐츠 끝 -->
	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>
<jsp:include page="../footer1.jsp" />