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
	href="<%=ctxPath%>/css/mypage/myupcheckPwd.css" />

<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js"
	crossorigin="anonymous"></script>



<script type="text/javascript">
   $(document).ready(function() {
      // 버튼 클릭 이벤트
      $("button.btnsuccess").click(function() {
         gouppwd();
      });

      // 엔터 키 이벤트 처리
      $("input:password[name='pwd']").on("keydown", function(e) {
         if (e.keyCode === 13) { // 엔터 키
            gouppwd();
         }
      });

      // 비밀번호 검증 및 폼 제출 함수
      function gouppwd() {
         const pwd = $("input:password[name='pwd']");
         if (pwd.val().trim() === "") {
            alert("비밀번호를 입력하세요!!");
            pwd.val("").focus();
            return; // 종료
         }
         
         const frm = document.my_upcheckPwd_Frm;
         frm.action = "<%=ctxPath%>/mypage/myupcheckPwd.mp";
         frm.method = "post";
         frm.submit();
      }
   });
</script>



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
						<li><a href="<%=ctxPath%>/mypage/mymovielike.mp">기대되는 영화</a></li>
					</ul></li>

				<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp">회원정보</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/myupcheckPwd.mp"
							class="active">회원정보수정</a></li>
						<li><a href="<%=ctxPath%>/mypage/mydelete.mp">회원탈퇴</a></li>
					</ul></li>
			</ul>
		</div>
		<%-- 마이페이지 사이드바 끝 --%>
		<!-- 메인 콘텐츠 -->
		<div class="mypage_main_content">
			<div class="my_h2">
				<h2>회원정보 수정</h2>
				<p>회원님의 소중한 정보를 안전하게 관리하세요.</p>
			</div>
			<form name="my_upcheckPwd_Frm">
				<h3>회원정보를 수정하시려면 비밀번호를 입력하셔야 합니다.</h3>
				<p>회원님의 개인정보 보호를 위한 절차이오니, 로그인시 사용하는 비밀번호를 입력해주세요.</p>
				<ul>
					<li><input type="password" name="pwd" size="25"
						autocomplete="off" /></li>
				</ul>

				<input type="hidden" name="userid" id="userid"
					value="${sessionScope.loginuser.userid}" />

				<div class="mybutton">
					<button type="button" onclick="javascript:history.back();"
						class="btn">취소</button>
					<button type="button" class="btn btnsuccess">찾기</button>
				</div>
				<c:if test="${not empty message}">
					<div style="color: red;">${message}</div>
				</c:if>
			</form>
		</div>
		<!-- 메인 콘텐츠 끝 -->
	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>

<jsp:include page="../footer1.jsp" />