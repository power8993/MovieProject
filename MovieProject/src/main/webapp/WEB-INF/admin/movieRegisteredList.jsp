<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/movieEdit.css" >

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/movieRegisteredList.js"></script>

	<div class="movie_edit_container">
		<h2>영화 [수정/삭제/상영등록]</h2>
		<form name="movie_search_frm">
			<select name="search_orderby">
				<option value="">정렬</option>		
				<option value="최신순">최신순</option>
				<option value="등록순">등록순</option>
			</select>
			&nbsp;
			<select name="search_type">
				<option value="">장르</option>
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
						<c:forEach var="movievo" items="${requestScope.movieList}">
							<tr>
								<td><span style="display:none;">${movievo.seq_movie_no}</span>${movievo.register_date}</td>
								<td>${movievo.fk_category_code}</td>
								<td><img src="${movie.poster_file}" alt="${movie.movie_title}" style="width:50px; height:auto;">&nbsp;${movievo.movie_title}</td>
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
		</div>
		
		<form name="seqFrm">
			<input type="hidden" name="seq"/>
		</form>
		
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" /> 