<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
div.ticket_info {
	margin: 0;
	padding: 0;
	background-color: #f8f8f8;
	color: #333;
}

.container {
	max-width: 600px;
	margin: 20px auto;
	background: #fff;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
}

.header {
	background: #000;
	color: #fff;
	padding: 10px;
	text-align: center;
	font-size: 18px;
	border-radius: 5px 5px 0 0;
}

div.reservation_info {
	margin: 30px;
	padding: 20px;
	font-size: 26px;
	font-weight: bold;
	text-align: center;
	border-bottom: solid 3px #eee;
	border-top: solid 3px #eee;
	border-bottom: solid 3px #eee;
	text-align: center;
}

.info {
	margin: 15px 0;
}

strong {
	color: #eb5e28;
	display: inline-block;
	width: 100px;
	font-weight: bold;
	display: inline-block;
}

.warning {
	color: #403d39;
	font-weight: bold;
	margin: 20px 0;
	text-align: center;
}

.rules {
	margin: 20px 0;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
}

table th, table td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

table th {
	background-color: #f4f4f4;
}

.actions {
	text-align: center;
	margin: 20px 0;
}

.btn {
	border: 1px solid #403d39; background-color : #fff;
	border: none;
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
	margin: 5px;
	border-radius: 4px;
	background-color: #fff;
}

.btn:hover {
	background-color: #403d39;
	color: white;
}

.btn.close {
	background-color: #fff;
}

@media print {
    body {
        background-color: #fff;
        margin: 0;
        padding: 0;
    }
    .container {
        page-break-inside: avoid;
        page-break-before: avoid;
        page-break-after: avoid;
        width: 100%;
        margin: 0 auto;
    }
    .actions {
        display: none;
    }
    * {
        font-size: 10px;
    }
}

</style>



<div class="ticket_info">
	<div class="header">예매정보 확인</div>

	<p class="warning">
		본 화면으로는 <strong>입장이 불가</strong>합니다.<br> 극장 매표소 또는 티켓판매기에서 아래의
		예매번호로<br> 영화 티켓을 발급받으신 후 입장하시기 바랍니다.
	</p>

	<c:if test="${not empty myreservationList_impUid}">
		<c:forEach var="reservation" items="${myreservationList_impUid}">
			<div class="reservation_info">${fn:replace(reservation.imp_uid, 'imp_', '')}</div>
			<div class="info">
				<strong>영화명:</strong> ${reservation.svo.mvo.movie_title}
			</div>
			<div class="info">
				<strong>상영일시:</strong> ${reservation.svo.start_time} ~
				${reservation.svo.end_time}
			</div>
			<div class="info">
				<strong>좌석:</strong> ${reservation.tvo.seat_no_list}
			</div>
			<div class="info">
				<strong>관람인원:</strong> ${reservation.tvo.AGE_GROUP_COUNT_LIST}명
			</div>
		</c:forEach>
	</c:if>

	<p class="warning">본 화면으로는 입장이 불가합니다. 티켓 발권 후 입장 바랍니다.</p>

	<p>입장 지연에 따른 관람 불편을 최소화하기 위해 본 영화는 10분 후 상영이 시작됩니다.</p>

	<h2>취소 및 환불 규정</h2>
	<p>홈페이지를 통해 취소하실 경우 상영시간 20분 전까지 취소 가능하며, 현장에서 직접 방문하실 경우 상영시간 전까지
		취소하실 수 있습니다.</p>
	<p>상영시간 이후 취소/환불/결제수단 변경은 불가합니다.</p>

	<h2>관람 전 반드시 확인 하세요!</h2>
	<p>본 영화는 만 15세 이상 관람 가능한 영화입니다.</p>
	<p>만 15세 미만 고객은 만 19세 이상 성인 보호자 동반 시 관람이 가능합니다. 연령 확인 불가 시 입장이 제한될
		수 있습니다.</p>
	<p>※ 연령 확인 수단 (사진,캡쳐본 불가): 학생증, 모바일 학생증, 청소년증, 여권</p>

	<h2>극장 내 비상 상황 안내</h2>
	<p>극장 내 비상 상황(천재 지변 등) 발생 시 예매 고객님께 문자 안내를 시행 중입니다. 고객님의 휴대폰 정보를 최신
		정보로 유지해 주세요.</p>
	<p>(HGV 마이페이지> 회원정보 > 회원정보수정)</p>

	<h2>매점 상품 구매 안내</h2>
	<p>매점 상품 구매 시 픽업 신청은 영화 상영 당일에 한해 가능하며 극장 별로 패스트오더 운영 시간은 상이할 수
		있습니다.</p>
	<p>구매한 매점 상품을 픽업하지 않으신 경우 구매자에 한하여 영화 상영일 이후 1일 이내 취소 요청이 가능합니다.</p>
	<p>(HGV 마이페이지 > 나의 예매내역)</p>

	<h2>판매자 및 거래에 관한 정보 고지</h2>
	<table>
		<tr>
			<th>판매자</th>
			<td>HGV</td>
		</tr>
		<tr>
			<th>주연</th>
			<td>영화상세 참고</td>
		</tr>
		<tr>
			<th>관람등급</th>
			<td>영화상세 또는 예매내역 정보 참고</td>
		</tr>
		<tr>
			<th>상영시간/장소</th>
			<td>예매내역 정보 참고</td>
		</tr>
		<tr>
			<th>소비자 상담</th>
			<td>1544-1122 (평일 09:00~18:00)</td>
		</tr>
	</table>

	<div class="actions">
		<input type="button" class="btn" value="인쇄" onclick="window.print()" />
		<input type="reset" class="btn" value="닫기" onclick="self.close()" />
	</div>
</div>

