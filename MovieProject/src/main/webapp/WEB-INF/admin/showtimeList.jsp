<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<% String ctxPath = request.getContextPath(); %>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/showtimeList.css" >

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/showtimeList.js"></script>

	<div class="movie_edit_container">
		<h2>상영일정 리스트 [조회/삭제(고려사항)]</h2>
		<form name="movie_search_frm">
			<select name="search_orderby">
				<option value="">상영일자</option>		
				<option value="내림차순">내림차순</option>   <!-- 상영일자가 먼 순 -->
				<option value="오름차순">오름차순</option>   <!-- 상영일자가 가까운 순 -->
			</select>
			&nbsp;
			<select name="search_type">
				<option value="">시간</option>
                <option value="1">액션</option>
                <option value="2">코미디</option>
                <option value="3">드라마</option>
                <option value="4">스릴러</option>
                <option value="5">로맨스</option>
                <option value="6">sf</option>
                <option value="7">판타지</option>
                <option value="8">애니메이션</option>
                <option value="9">역사</option>
                <option value="10">범죄</option>
                <option value="11">스포츠</option>
                <option value="12">느와르</option>
			</select>
			
			<input type="text" name="movie_title"/>
			<button type="button" class="btn btn-primary">검색</button>
			
			<select name="sizePerPage">
				<option value="10">10개</option>
				<option value="15">15개</option>
				<option value="20">20개</option>
			</select>
		</form>
		
		
		<div id="search_result">
			<table class="table table-bordered" id="movie_table">
				<thead>
					<tr>
						<th>상영일자</th>
						<th>상영시간</th>
						<th>상영관</th>
						<th>포스터/제목</th>
						<th>잔여 좌석</th>
					</tr>
				</thead>
				
				<tbody>
					<%-- 검색 결과 표시 --%>
					<c:if test="${not empty requestScope.movieList}">
						<c:forEach var="movievo" items="${requestScope.movieList}">
							<tr>
								<td><span style="display: none;">${movievo.showvo.seq_showtime_no}</span>${fn:substring(movievo.showvo.start_time,0,10)}</td>
								<td>${fn:substring(movievo.showvo.start_time,11,16)} ~ ${fn:substring(movievo.showvo.end_time,11,16)}</td>
								<td>${movievo.showvo.fk_screen_no}관</td>
								<td><img src="<%= ctxPath%>/images/admin/poster_file/${movievo.poster_file}" alt="${movievo.movie_title}" style="width:60px; height:auto;">&nbsp;${movievo.movie_title}</td>
								<td>${movievo.showvo.unused_seat} / ${movievo.showvo.unused_seat}</td>
								
							</tr>
						</c:forEach>
						<div id="movie_detail_modal"></div>  <!-- 모달 사용할 경우 모달 위치 -->
					</c:if>
					
					<%-- 검색 결과가 없을 때 --%>
					<c:if test="${empty requestScope.movieList}">
						<tr>
			            	<td colspan="5">검색된 영화가 없습니다.</td>
			            </tr>
					</c:if>
				</tbody>
				
			</table>
		</div>
		
		<form name="seqFrm">
			<input type="hidden" name="seq"/>
		</form>
		
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" /> 