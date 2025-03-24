<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
String ctxPath = request.getContextPath();
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage/mypageProfile.js"></script>
	

<%-- 마이페이지 나의 프로필장 --%>
<div class="myprofile">

	<div class="profile-container">


		<%-- 프로필 사진과 버튼 컨테이너 --%>
		<div class="profile-photo-container">


			<div id="lastprofile_Edit" data-context-path="${pageContext.request.contextPath}">
				<img id="profileimage" alt="Profile image">
			</div><%-- 프로필 사진 모달창 --%>


			<button type="button" class="profile_Edit"
				onclick="profile_Edit('${sessionScope.loginuser.userid}', '<%=ctxPath%>')">
				프로필 편집</button>

		</div>

		<%-- 사용자 정보 --%>
		<div class="profile-info">

			<h2>${(sessionScope.loginuser).name}님</h2>

			<p>
				나의 영화 랭킹:
				<c:forEach var="myranking" items="${requestScope.myranking}">
					<strong>${myranking.myranking}</strong> 순위
                </c:forEach>
			</p>

			<c:forEach var="profilepoint"
				items="${requestScope.myreservationprofile}">
				<p>
					사용 가능 포인트: <strong> <fmt:formatNumber
							value="${profilepoint.total_points}" pattern="#,###" />pt
					</strong>
				</p>
				<p>
					사용한 포인트: <strong> <fmt:formatNumber
							value="${profilepoint.total_deducted}" pattern="#,###" />pt
					</strong>
				</p>
			</c:forEach>

		</div>


	</div>


</div>
<%-- 마이페이지 나의 프로필장 끝 --%>
