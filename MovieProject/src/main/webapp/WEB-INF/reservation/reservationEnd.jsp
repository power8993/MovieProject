	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../header1.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style type="text/css">

div.movie_info label {
	display: inline-block;
	width: 100px;
	line-height: 250%;
	font-weight: normal;
}

div#reservation_info {
	font-size: 18pt;
	font-weight: bold;
}

div#information > span {
	display: inline-block;
}


</style>

<div class="container">
	<h3 class="text-center my-5" style="font-size:30pt; font-weight:bold;">예매가 완료 되었습니다.</h3>
	
	<div class="movie_info ml-3 mx-5" style="display: flex; width: 60%;">
		<img src="/MovieProject/images/admin/poster_file/${requestScope.poster_file}.jpg" style="width: 50%; height: auto;">
		<div id="reservation_info" class="ml-5">
			<div class="mt-2" style="width: 500px;"><label>예매번호 : </label>${fn:replace(requestScope.imp_uid, 'imp_', '')}</div>
			<div><label>영화제목 : </label>${requestScope.movie_title}</div>
			<div><label>상영관&nbsp;&nbsp;&nbsp; : </label>[HGV] ${requestScope.fk_screen_no}관</div>
			<div><label>시작시간 : </label>${requestScope.start_time}</div>
			<div><label>종료시간 : </label>${requestScope.end_time}</div>
			<div><label>예매좌석 : </label>${requestScope.seat_str}</div>
			<div><label>결제금액 : </label>${requestScope.ticketPrice} 원</div>
		</div>
	</div>
	<hr>
	<div id="information" class="mb-4 mx-5">
		<span class="mr-5" style="font-size: 18pt">
			예매유의사항<br>
		</span>
		<span class="mt-2" style="font-size:15pt;">
			포인트는 상영 종료 시간이 지난 후에 적립됩니다.<br>
			영화 상영 스케쥴은 영화관사정에 의해 변경될 수 있습니다.
		</span>
	</div>
</div>





<jsp:include page="../footer1.jsp" />