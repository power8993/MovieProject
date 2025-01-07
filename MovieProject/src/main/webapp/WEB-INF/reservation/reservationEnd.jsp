<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../header1.jsp" />


<style type="text/css">

div.movie_info label {
	display: inline-block;
	width: 100px;
	line-height: 200%;
}


</style>

<div class="container">
	<h3 class="text-center my-5" style="font-size:30pt; font-weight:bold;">예매가 완료 되었습니다.</h3>
	
	<div class="movie_info" style="display: flex; width: 60%;">
		<img class="mx-5" src="/MovieProject/images/admin/poster_file/미니언즈.jpg" style="width: 30%; height: auto;">
		<div class="ml-5">
			<div class="mt-5"><label>예매번호</label>${requestScope.imp_uid}</div>
			<div><label>영화</label>${requestScope.movie_title}</div>
			<div><label>일시</label>${requestScope.start_time}</div>
			<div><label>예매번호</label>${requestScope.imp_uid}</div>
			<div><label>좌석</label>${requestScope.seat_str}</div>
			<div><label>결제금액</label>${requestScope.ticketPrice}</div>
		</div>
	</div>
	<hr>
	<div>
		<span>
			예매유의사항
		</span>
		<span>
			포인트는 상영 종료 시간이 지난 후에 적립됩니다.
			영화 상영 스케쥴은 영화관사정에 의해 변경될 수 있습니다.
		</span>
	</div>
</div>





<jsp:include page="../footer1.jsp" />