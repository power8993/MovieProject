<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String ctxPath = request.getContextPath();
%>
<% String userid = (String) session.getAttribute("userid"); %>

<jsp:include page="../header1.jsp" />

<%-- 메인 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<%-- 영화 버튼 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mypagemovie.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/mypage/mymovielike.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage/mymovielike.js"></script> 

<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>



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
						<li><a href="<%=ctxPath%>/mypage/mymoviereview.mp">내가 쓴 평점</a></li>
						<li><a href="<%=ctxPath%>/mypage/mymovielike.mp"
							class="active">기대되는 영화</a></li>
					</ul></li>

				<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보수정</a></li>
						<li><a href="<%=ctxPath%>/mypage/mydelete.mp">회원탈퇴</a></li>
					</ul></li>
			</ul>
		</div>
		<%-- 마이페이지 사이드바 끝 --%>

		<%-- 메인콘텐츠 시작 --%>
		<div class="mypage_main_content">

			<!-- 영화 버튼 바 시작 -->
			<div class="my_movie_buttons">
				<a href="<%=ctxPath%>/mypage/mymoviewatched.mp" class="button">
					<i class="fa-solid fa-feather"></i> 내가 본 영화
				</a> <a href="<%=ctxPath%>/mypage/mymoviereview.mp" class="button"> <i
					class="fa-solid fa-star"></i> 내가 쓴 평점
				</a> <a href="<%=ctxPath%>/mypage/mymovielike.mp" class="button active">
					<i class="fa-solid fa-film"></i>기대되는 영화
				</a>
			</div>
			<%-- 영화 버튼 바 끝 --%>

			<!-- 기대되는 영화 -->
    <div>
      <div class="row" id="mymovielikeHIT" ></div> <%-- 기대되는 영화가 있을 때 --%>
       <p class="empty"></p> <%-- 기대되는 영화가 없을 때 --%>
        <input type="hidden" name="userid" id="userid"
					value="${sessionScope.loginuser.userid}" />
					
					
			<div class="col-12 mt-4">
            <p class="text-center">
                <button type="button" class="mymovielikebutton" id="btnmymovielike" value="">↓더보기
                <span id="mymovielikecount">0</span>
                <span id="totalmymovielike"> ${requestScope.totalmymovielike}</span>
                </button>
            </p>
        </div>
</div>


			<%-- 기대되는 영화 끝 --%>






		</div>
		<!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>






<jsp:include page="../footer1.jsp" />
