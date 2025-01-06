<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/showtimeList.css" >

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/showtimeList.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$("input[name='search_date']").val("${requestScope.search_date}");
	$("select[name='search_time']").val("${requestScope.search_time}");
	$("input[name='search_movie_title']").val("${requestScope.search_movie_title}");
	$("input[name='search_orderby']").val("${requestScope.search_orderby}");
	$("input[name='invalid_showtime']").val("${requestScope.invalid_showtime}");
	
	$("select[name='size_per_page']").val("${requestScope.size_per_page}");
	
	// select 태그의 숫자를 선택함에 따라 해당 값의 행만큼 보여지는 이벤트
	$("select[name='size_per_page']").bind("change", function(){
		const frm = document.showtime_search_frm;
		// frm.action = "movieRegisteredList.mp";
		// frm.method = "get";
		
		frm.submit();
	});// end of $("select[name='size_per_page']").bind("change", function(){})--------------------------
	
	// select 태그의 날짜를 선택함에 따라 해당 값의 행만큼 보여지는 이벤트
	$("input[name='search_date']").bind("change", function(){
		$("input[name='search_movie_title']").val("");
		
		const frm = document.showtime_search_frm;
		// frm.action = "movieRegisteredList.mp";
		// frm.method = "get";
		
		frm.submit();
	});// end of $("select[name='size_per_page']").bind("change", function(){})--------------------------

	// select 태그의 시간대를 선택함에 따라 해당 값의 행만큼 보여지는 이벤트
	$("select[name='search_time']").bind("change", function(){
		$("input[name='search_movie_title']").val("");
		
		const frm = document.showtime_search_frm;
		// frm.action = "movieRegisteredList.mp";
		// frm.method = "get";
		
		frm.submit();
	});// end of $("select[name='size_per_page']").bind("change", function(){})--------------------------

	

	// === 시간 빠른 순/시간 늦은 순 선택함에 따라 해당 기준으로 정렬하여 보여주는 이벤트
	$("span.re_orderby").click(function(e){
		const text = $(e.target).text();
		
		if(text == "시간 빠른 순") {
			$("input[name='search_orderby']").val("asc");
		}
		else if(text == "시간 늦은 순") {
			$("input[name='search_orderby']").val("desc");
		}
		
		const frm = document.showtime_search_frm;
		// frm.action = "movieRegisteredList.mp";
		// frm.method = "get";
		
		frm.submit()
		
	});// end of $("span#re_desc").click(function(){})------------------	
	
	// === 서버에서 전달된 값에 따라 활성화된 버튼을 설정 === //
    var search_orderby = $("input[name='search_orderby']").val();

    // 페이지 로딩 시 활성화된 버튼에 css 부여
    if (search_orderby == 'desc') {
        $('#re_desc').addClass('active'); 
        $('#re_asc').removeClass('active'); 
    } else if (search_orderby == 'asc') {
        $('#re_asc').addClass('active'); 
        $('#re_desc').removeClass('active');
    }
    
    
	// === 상영예정작/상영종료작 선택 후 서버에서 전달된 값에 따라 활성화된 버튼을 설정 === //
    var invalid_showtime = $("input[name='invalid_showtime']").val();
	
    // 페이지 로딩 시 활성화된 버튼에 css 부여
    if (invalid_showtime == '상영예정작') {
    	$("span#invalid_showtime").text("상영예정작");
        $('span#invalid_showtime').css({ 'background-color': '#403D39' });
    } else if (invalid_showtime == '상영종료작') {
    	$("span#invalid_showtime").text("상영종료작");
        $('span#invalid_showtime').css({ 'background-color': '#CCC5B9' });
    }
    

});
</script>

	<div class="movie_showtime_container">
		<h2>상영일정 리스트</h2>
		<form name="showtime_search_frm" id="showtime_list" class="form-row">
			<div class="col-2">
		    	<input type="date" class="form-control" name="search_date" id="search_date">
		  	</div>
		
		  	<div class="col-2">
		    	<select name="search_time" class="form-control">
		      		<option value="">시간대</option>
			      	<option value="모닝(06:00~10:00)">모닝(06:00~10:00)</option>
			      	<option value="브런치(10:01~13:00)">브런치(10:01~13:00)</option>
			      	<option value="일반(13:01~23:59)">일반(13:01~23:59)</option>
		      		<option value="심야(00:00~05:59)">심야(00:00~05:59)</option>
		   	 	</select>
		  	</div>
		
		  	<div class="col-4">
		    	<input type="text" name="search_movie_title" class="form-control" placeholder="영화 제목을 입력하세요">
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
		
			<input type="hidden" name="search_orderby"/>
			<input type="hidden" name="invalid_showtime"/>
			
		</form>
		
		
		<div id="search_result">
			<div class="btn_container">
				<span class="re_orderby" id="re_asc">시간 빠른 순</span>&nbsp;<span class="re_orderby" id="re_desc">시간 늦은 순</span>
				<span id="invalid_showtime" onclick="toggleShowtimeInvalidStatus()">상영예정작</span>
			</div>
				<table class="table table-bordered" id="showtime_table">
				<thead>
					<tr>
						<th>번호</th>
						<th>상영일자</th>
						<th>상영시간</th>
						<th>상영관</th>
						<th>영화</th>
						<th>잔여 좌석</th>
					</tr>
				</thead>
				
				<tbody>
					<%-- 검색 결과 표시 --%>
					<c:if test="${not empty requestScope.showtimeList}">
						<c:forEach var="movievo" items="${requestScope.showtimeList}" varStatus="status">
							<tr class="showtime_info">
								<fmt:parseNumber var="current_showpage_no" value="${requestScope.current_showpage_no}"/>
								<fmt:parseNumber var="size_per_page" value="${requestScope.size_per_page}"/>
								<td>${(requestScope.total_showtime_count) - (current_showpage_no - 1) * size_per_page - (status.index)}</td>
								
								<td><span style="display: none;">${movievo.showvo.seq_showtime_no}</span>${fn:substring(movievo.showvo.start_time,0,10)}</td>
								<td>${fn:substring(movievo.showvo.start_time,11,16)} ~ ${fn:substring(movievo.showvo.end_time,11,16)}</td>
								<td class="fk_screen_no">${movievo.showvo.fk_screen_no}관</td>
								<td><img src="<%= ctxPath%>/images/admin/poster_file/${movievo.poster_file}" alt="${movievo.movie_title}" style="width:60px; height:auto;">&nbsp;${movievo.movie_title}</td>
								<td class="seat_status"><span id="seat_arr" style="display: none;">${movievo.showvo.seat_arr}</span>${movievo.showvo.unused_seat} / ${movievo.scvo.seat_cnt}</td>
							</tr>
						</c:forEach>
						<div id="movie_detail_modal"></div>  <!-- 모달 사용할 경우 모달 위치 -->
					</c:if>
					
					<%-- 검색 결과가 없을 때 --%>
					<c:if test="${empty requestScope.showtimeList}">
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
			<input type="hidden" name="movie_status"/>
		</form>
		
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" /> 