	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../header1.jsp" />


<style type="text/css">

div.movie_info label {
	display: inline-block;
	width: 100px;
	line-height: 300%;
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
		<img src="/MovieProject/images/admin/poster_file/${requestScope.poster_file}" style="width: 50%; height: auto;">
		<div id="reservation_info" class="ml-5">
			<div class="mt-5" style="width: 500px;"><label>예매번호 : </label>${requestScope.imp_uid}</div>
			<div><label>영&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;화 : </label>${requestScope.movie_title}</div>
			<div><label>일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;시 : </label>${requestScope.start_time}</div>
			<div><label>좌&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;석 : </label>${requestScope.seat_str}</div>
			<div><label>결제금액 : </label>${requestScope.ticketPrice} 원</div>
		</div>
	</div>
	<hr>
	<div id="information" class="mb-4 mx-5">
		<span class="mr-3" style="font-size: 18pt;">
			예매유의사항
		</span>
		<span class="">
			포인트는 상영 종료 시간이 지난 후에 적립됩니다.<br>
			영화 상영 스케쥴은 영화관사정에 의해 변경될 수 있습니다.
		</span>
	</div>
</div>





<jsp:include page="../footer1.jsp" />