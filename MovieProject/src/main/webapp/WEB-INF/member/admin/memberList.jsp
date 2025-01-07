<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/admin/memberList.css" >

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/member/admin/memberList.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("select[name='search_type']").val("${requestScope.search_type}");
	$("input[name='search_word']").val("${requestScope.search_word}");

	$("select[name='size_per_page']").val("${requestScope.size_per_page}");
	
	// select 태그의 숫자를 선택함에 따라 해당 값의 행만큼 보여지는 이벤트
	$("select[name='size_per_page']").bind("change", function(){
		const frm = document.member_search_frm;
		// frm.action = "memberList.mp";
		// frm.method = "get";
		
		frm.submit();
	});// end of $("select[name='size_per_page']").bind("change", function(){})--------------------------
});
</script>

	<div class="member_list_container">
		<h2>회원 목록</h2>
		<form name="member_search_frm" id="member_list" class="form-row">
		  	<div class="col-2">
		    	<select name="search_type" class="form-control">
		      		<option value="">검색유형</option>
			      	<option value="user_id">아이디</option>
			      	<option value="email">이메일</option>
		   	 	</select>
		  	</div>
		
		  	<div class="col-6">
		    	<input type="text" name="search_word" class="form-control" placeholder="검색 내용을 입력하세요">
		  	</div>
		
		  	<div class="col-2">
		    	<button type="button" class="btn btn-primary" onclick="goSearch()">검색</button>
		  	</div>
		  	
		  	<div class="col-2">
		    	<select name="size_per_page" class="form-control">
		      		<option value="10">10개</option>
		      		<option value="15">15개</option>
		      		<option value="20">20개</option>
		    	</select>
		  	</div>			
		</form>
		
		<div id="search_result">
			<table class="table table-bordered" id="member_table">
				<thead>
					<tr>
						<th>번호</th>
						<th>아이디</th>
						<th>이름</th>
						<th>생년월일</th>
						<th>성별</th>
						<th>가입일자</th>
						<th>휴면상태</th>
						<th>탈퇴여부</th>
					</tr>
				</thead>
				
				<tbody>
					<%-- 검색 결과 표시 --%>
					<c:if test="${not empty requestScope.memberList}">
						<c:forEach var="membervo" items="${requestScope.memberList}" varStatus="status">
							<tr class="member_info">
								<fmt:parseNumber var="current_showpage_no" value="${requestScope.current_showpage_no}"/>
								<fmt:parseNumber var="size_per_page" value="${requestScope.size_per_page}"/>
								<td>${(requestScope.total_member_count) - (current_showpage_no - 1) * size_per_page - (status.index)}</td>
								
								<td class="userid">${membervo.userid}</td>
								<td>${membervo.name}</td>
								<td>${membervo.birthday}</td>
								<td>${membervo.gender}</td>
								<td>${membervo.registerday}</td>	
								
								<c:choose>
									<c:when test="${membervo.idle eq '1'}">
										<td class="member_status"><span style="background-color: #74b2b7; color: white;">활성</span></td>
									</c:when>
									<c:when test="${membervo.idle eq '0'}">
										<td class="member_status"><span style="background-color: #CCC5B9; color: white;">휴면</span></td>
									</c:when>
								</c:choose>	
								
								<c:choose>
									<c:when test="${membervo.status eq '1'}">
										<td class="member_status"><span style="background-color: #62A87C; color: white;">가입</span></td>
									</c:when>
									<c:when test="${membervo.status eq '0'}">
										<td class="member_status"><span style="background-color: #CCC5B9; color: white;">탈퇴</span></td>
									</c:when>
								</c:choose>	

							</tr>
						</c:forEach>
						<div id="member_detail_modal"></div>  <!-- 모달 사용할 경우 모달 위치 -->
					</c:if>
					
					<%-- 검색 결과가 없을 때 --%>
					<c:if test="${empty requestScope.memberList}">
						<tr>
			            	<td colspan="8">검색된 회원이 없습니다.</td>
			            </tr>
					</c:if>
				</tbody>
				
			</table>
			
			<div id="page_bar">
				<nav>
					<ul class="pagination">${requestScope.page_bar}</ul>
				</nav>
			</div>
			
		</div>
		
		<form name="useridFrm">
			<input type="hidden" name="userid"/>
		</form>
		
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" /> 