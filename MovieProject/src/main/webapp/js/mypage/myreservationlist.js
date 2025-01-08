/* 예매내역 영수증 출력 */
function Receipt_Printing(imp_uid,ctxPath){

		const width = 550;
		const height = 700;
	 
		 const left = Math.ceil( (window.screen.width - width)/2 ); // 정수로 만듬
		 const top = Math.ceil( (window.screen.height - height)/2 ); // 정수로 만듬
		 
		 const url = `${ctxPath}/mypage/myreservationReceipt.mp?imp_uid=${imp_uid}`;      
	 
		 window.open(url, "Receipt_Printing",
					`left=${left}, top=${top}, width=${width}, height=${height}`);
}//end of function Receipt_Printing(ctxPath, userid)---







/* 예매취소 */
function myreservation_cancel(imp_uid,fk_seq_movie_no,userid) {

	if (confirm(`예매를 취소하시겠습니까? \n\n ※ 예매 취소는 상영시간 20분 전까지 가능하며, \n 예매 가능은 상영시간 30분 전까지 가능합니다.`)) {
		$.ajax({
			url: "/MovieProject/reservation/reservationCancel.mp",
			type: "post",
			data: {"imp_uid":imp_uid, "fk_seq_movie_no":fk_seq_movie_no,"userid":userid},
			dataType: "json",
			success: function(json) {
				if (json.n == 1) {
					// 삭제 성공 후 수정 모달 띄우기
					myreservation_cancel_Modal();
				} else {
					/*alert("삭제 실패. 다시 시도해 주세요.");*/
					myreservation_cancel_Modal();
				}
			},
			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
	}
}


/* 예매취소 후 모달창 */
function myreservation_cancel_Modal(){

	const myreservation = $("div#myreservation_cancel_modal"); // 모달을 넣을 위치

	const modal_popup = `
	<div class="modal fade" id="myreservation_find" tabindex="-1" aria-labelledby="myreservation_find" aria-hidden="true">
	<div class="modal-dialog modal-lg">
	    <div class="modal-content">
	        <!-- Modal Header -->
	        <div class="modal-header">
	            <h3>예매 취소 및 환불 안내</h3>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                <span aria-hidden="true">&times;</span>
	            </button>
	        </div>
			
				<!-- Modal Body -->
			   <div class="modal-body">
	            <p>예매 취소가 완료되었습니다.</p>
	            <h4>환불 안내</h4>
	            <ul>
	                <li><strong>신용카드:</strong> 결제 후 3일 이내 취소 시 승인 취소되며, 3일 이후 매입 취소 시 영업일 기준 3~5일 소요됩니다.</li>
	                <li><strong>체크카드:</strong> 결제 후 3일 이내 취소 시 당일 카드사에서 환불처리되며, 3일 이후 매입 취소 시 카드사에 따라 3~10일 소요됩니다.</li>
	                <li><strong>휴대폰 결제:</strong> 결제 일자 기준 당월(1일~말일) 취소만 가능하며, 익월 취소 관련 문의는 CGV고객센터(1544-1122)를 통해 확인 후 3~10일 이내 환불됩니다.</li>
	                <li><strong>카카오페이:</strong> 카카오페이머니나 카카오포인트를 사용하신 경우 각각의 잔액으로 원복되며, 카드 결제를 하신 경우는 카드사 정책에 따라 승인취소가 진행되며 3일 이후 매입 취소시 영업일 기준 3~10일 소요됩니다.</li>
	                <li><strong>PAYCO:</strong> PAYCO 쿠폰/포인트를 사용하신 경우 각각의 쿠폰/포인트로 원복되며 쿠폰의 경우 조건에 따라 재사용이 불가 할 수 있습니다.</li>
	                <li><strong>스마일페이:</strong> 스마일캐시를 사용하신 경우 스마일캐시로 원복되며, 카드 결제금액은 카드사 정책에 따라 승인취소가 진행됩니다.</li>
	                <li><strong>NAVER Pay:</strong> NAVER Pay 포인트를 사용하신 경우 NAVER Pay 포인트로 원복되며, 카드사 결제를 하신 경우는 카드사 정책에 따라 승인취소가 진행됩니다.</li>
	                <li><strong>카카오톡 선물하기 복합상품:</strong> 매점쿠폰 사용 시 예매 티켓 환불 불가. 단, 매점 쿠폰 미 사용 시 예매 티켓 환불 가능.</li>
	                <li><strong>계좌이체:</strong> 예매 취소 후 은행별 환급 기준이 다르며, 예매 취소 후 7일~10일 이내 환급 처리됩니다.</li>
	            </ul>
	        </div>
			<!-- Modal Footer -->
			 <div class="modal-footer">
			 <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
			  </div>
	    </div>
	</div>
	`;

	myreservation.html(modal_popup); // 모달 HTML 삽입
	
	$('#myreservation_find').modal('show');

}