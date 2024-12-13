<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String ctxPath = request.getContextPath();
//     /MyMVC
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemain.css">

<jsp:include page="../header1.jsp" />

<%-- 전체 창 --%>  
<div class="my_container">
	<%-- 마이페이지 나의 프로필장 --%>
	<div class="myprofile"></div>
	<%-- 마이페이지 사이드바 & 매안 창 --%>
	<div class="my_content">
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
		<%-- 마이페이지 메인 창 --%>
		<div class="my_hcontent_detail"></div>
	</div>

</div>







<jsp:include page="../footer1.jsp" />