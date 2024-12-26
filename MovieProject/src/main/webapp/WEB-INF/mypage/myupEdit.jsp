<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
String ctxPath = request.getContextPath();
%>
<jsp:include page="../header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypagemain.css" />
<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css"
	href="<%=ctxPath%>/css/mypage/mypageedit.css" />
<%-- h3 a태그의 이모티콘 --%>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js"
	crossorigin="anonymous"></script>

<%-- 직접 만든 JS --%>
<script type="text/javascript"
	src="<%=ctxPath%>/js/mypage/mypageEdit.js"></script>

<%-- 전체 창 --%>
<div class="my_container">
	<%-- 마이페이지 나의 프로필장 --%>
	<div class="myprofile">
		<div class="profile-container">
			<i class="fa-solid fa-circle-user" style="color: #252422;"></i>
			<%-- 사용자 정보 --%>
			<div class="profile-info">
				<h2>${(sessionScope.loginuser).name}님</h2>
				<p>
					나의 영화 랭킹 <strong>50</strong> 순위
				</p>
				<p>
					사용 가능 포인트: <strong>${(sessionScope.loginuser).point}pt</strong>
				</p>
				<p>
					사용한 포인트: <strong>0pt</strong>
				</p>
			</div>
			<%-- 사용자 정보 끝 --%>
		</div>
	</div>
	<%-- 마이페이지 나의 프로필장 끝 --%>

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

				<li><a href="<%=ctxPath%>/mypage/mywatchedmovie.mp">영화</a>
					<ul>
						<li><a href="<%=ctxPath%>/mypage/mywatchedmovie.mp">내가 본
								영화</a></li>
						<li><a href="<%=ctxPath%>/mypage/myreview.mp">내가 쓴 평점</a></li>
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
			<div class="mypageEditclass" id="mypageEditFrm">

				<div class="my_h2">
					<h2>회원정보 수정</h2>
					<p>회원님의 소중한 정보를 안전하게 관리하세요.</p>
				</div>

				<form name="editFrm">

					<table id="tblMemberEdit">
						<thead>
							<tr>
								<td>기본 정보</td>
							</tr>

						</thead>
						<tbody>
							<tr>
								<td id="tblEditcss">성명</td>
								<td><input type="text" name="name" id="name" maxlength="30"
									class="requiredInfo" value="${sessionScope.loginuser.name}" />
									<span class="error">성명은 필수입력 사항입니다.</span></td>
							</tr>

							<tr>
								<td id="tblEditcss">아이디</td>
								<td><input type="text" name="userid" id="userid" maxlength="30"
									class="requiredInfo" value="${sessionScope.loginuser.userid}"
									readonly /></td>
							</tr>

							<tr>
								<td id="tblEditcss">비밀번호</td>
								<td><button type="button" class="mypagepwdcss" data-toggle="modal"
									data-target="#passwdFind" data-dismiss="modal"
									data-backdrop="static">비밀번호 변경</button>
									<p class="pwdupdate">비밀번호 변경일: ${sessionScope.loginuser.lastpwdchangedate}</p>
									
									<div class="modal fade" id="passwdFind">
										<div class="modal-dialog">
											<div class="modal-content">

												<!-- Modal header -->
												<div class="modal-header">
													<h4 class="modal-title">비밀번호 변경</h4>
													<button type="button" class="close passwdFindClose"
														data-dismiss="modal">&times;</button>
												</div>

												<!-- Modal body -->
												<div class="modal-body">
													<div id="pwFind">
														<iframe style="border: none; width: 100%; height: 350px;"
															src="<%=ctxPath%>/mypage/myuppwdEdit.mp"> </iframe>
													</div>
												</div>

												<!-- Modal footer -->
												<div class="modal-footer">
													<button type="button"
														class="btn btn-danger passwdFindClose"
														data-dismiss="modal">Close</button>
												</div>
											</div>

										</div>
									</div></td>
									
									
							</tr>

							<tr>
								<td id="tblEditcss">이메일&nbsp;<span class="star">*</span></td>
								<td><input type="text" name="email" id="email"
									maxlength="60" class="requiredInfo"
									value="${sessionScope.loginuser.email}" /> <span class="error">이메일
										형식에 맞지 않습니다.</span> <%-- 이메일중복체크 --%> <span id="emailcheck">이메일중복확인</span>
									<span id="emailCheckResult"></span></td>
							</tr>

							<tr>
								<td id="tblEditcss">연락처&nbsp;</td>
								<td><input type="text" name="hp1" id="hp1" size="6"
									maxlength="3" value="010" readonly />&nbsp;-&nbsp; <input
									type="text" name="hp2" id="hp2" size="6" maxlength="4"
									value="${fn:substring(sessionScope.loginuser.mobile, 3,7)}" />&nbsp;-&nbsp;
									<input type="text" name="hp3" id="hp3" size="6" maxlength="4"
									value="${fn:substring(sessionScope.loginuser.mobile, 7,11)}" />
									<span class="error">휴대폰 형식이 아닙니다.</span></td>
							</tr>
							<tr>
								<td id="tblEditcss">생년월일&nbsp;</td>
								<td><input type="text" name="birthday" id="birthday"
									maxlength="20" class="requiredInfo"
									value="${sessionScope.loginuser.birthday}" readonly /></td>
							</tr>
						</tbody>
					</table>
					<div class="mypageupbutton">
						<input type="button" class="mypageupbtn" value="수정하기"
							onclick="goEdit()" /> <input type="reset" class="mypageupbtn"
							value="취소하기" onclick="self.close()" />
					</div>
				</form>

			</div>
		</div>
		<!-- 메인 콘텐츠 끝 -->

	</div>
	<%-- 마이페이지 사이드바 & 매안 창 끝 --%>

</div>
<%-- 전체 창 끝 --%>






<jsp:include page="../footer1.jsp" />