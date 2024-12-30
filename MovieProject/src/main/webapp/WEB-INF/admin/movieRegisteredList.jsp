<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String ctxPath = request.getContextPath(); %>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/movieEdit.css" >

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/movieRegisteredList.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$("select[name='search_category']").val("${requestScope.search_category}");
	$("select[name='search_type']").val("${requestScope.search_type}");
	$("input[name='search_word']").val("${requestScope.search_word}");
	
	$("select[name='size_per_page']").val("${requestScope.size_per_page}");
	
	// select 태그의 숫자를 선택함에 따라 해당 값의 행만큼 보여지는 이벤트
	$("select[name='size_per_page']").bind("change", function(){
		const frm = document.movie_search_frm;
		// frm.action = "movieRegisteredList.mp";
		// frm.method = "get";
		
		frm.submit();
	});// end of $("select[name='size_per_page']").bind("change", function(){})--------------------------
	
	// select 태그의 장르를 선택함에 따라 해당 값의 행만큼 보여지는 이벤트
	$("select[name='search_category']").bind("change", function(){
		const frm = document.movie_search_frm;
		// frm.action = "movieRegisteredList.mp";
		// frm.method = "get";
		
		frm.submit();
	});// end of $("select[name='size_per_page']").bind("change", function(){})--------------------------
	
});
</script>

	<div class="movie_edit_container">
		<h2>영화 [수정/삭제/상영등록]</h2>
		<form name="movie_search_frm">
			<select name="search_category">
				<option value="">장르</option>
                <option value="액션">액션</option>
                <option value="코미디">코미디</option>
                <option value="드라마">드라마</option>
                <option value="스릴러">스릴러</option>
                <option value="로맨스">로맨스</option>
                <option value="sf">sf</option>
                <option value="판타지">판타지</option>
                <option value="애니메이션">애니메이션</option>
                <option value="역사">역사</option>
                <option value="범죄">범죄</option>
                <option value="스포츠">스포츠</option>
                <option value="느와르">느와르</option>
			</select>
			
			<select name="search_type">
				<option value="">검색대상</option>
                <option value="movie_title">영화제목</option>
                <option value="director">감독</option>
                <option value="actor">배우</option>
			</select>
			
			<input type="text" name="search_word"/>
			<button type="button" class="btn btn-primary">검색</button>
			
			<select name="size_per_page">	
				<option value="10">10개</option>
				<option value="15">15개</option>
				<option value="20">20개</option>
			</select>
		</form>
		
		
		<div id="search_result">
			<span id="re_desc">최신순</span>&nbsp;<span id="re_asc">등록순</span>
			<table class="table table-bordered" id="movie_table">
				<thead>
					<tr>
						<th>번호</th>
						<th>등록일자</th>
						<th>장르</th>
						<th>포스터/제목</th>
						<th>상영등급</th>
						<th>개봉일자</th>
					</tr>
				</thead>
				
				<tbody>
					<%-- 검색 결과 표시 --%>
					<c:if test="${not empty requestScope.movieList}">
						<c:forEach var="movievo" items="${requestScope.movieList}" varStatus="status">
							<tr class="movie_info">
								<fmt:parseNumber var="current_showpage_no" value="${requestScope.current_showpage_no}"/>
								<fmt:parseNumber var="size_per_page" value="${requestScope.size_per_page}"/>
								<td>${(requestScope.total_movie_count) - (current_showpage_no - 1) * size_per_page - (status.index)}</td>
								
								<td><span style="display:none;">${movievo.seq_movie_no}</span>${movievo.register_date}</td>
								<td>${movievo.catevo.category}</td>
								<td><img src="<%= ctxPath%>/images/admin/poster_file/${movievo.poster_file}" alt="${movievo.movie_title}" style="width:60px; height:auto;">&nbsp;${movievo.movie_title}</td>
								<td><img src="<%= ctxPath%>/images/admin/movie_grade/${movievo.movie_grade}.png" alt="${movievo.movie_grade}" style="width:30px; height:auto;"></td>
								<td>${movievo.start_date}</td>	
							</tr>
						</c:forEach>
						<div id="movie_detail_modal"></div>
					</c:if>
					
					<%-- 검색 결과가 없을 때 --%>
					<c:if test="${empty requestScope.movieList}">
						<tr>
			            	<td colspan="5">검색된 영화가 없습니다.</td>
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
		
		<form name="seqFrm">
			<input type="hidden" name="seq"/>
		</form>
		
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" /> 