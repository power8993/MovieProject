

$(document).ready(function(){
	
	
	var search_type_dom = $("select[name='search_type']")[0];
	var search_word_dom = $("input:text[name='search_word']")[0];
	
	
	// 유효성 검사 메시지 반복하여 출력되지 않도록 제어 
	$("input:text[name='search_word']").bind("keydown", function(e){
		search_word_dom.setCustomValidity("");  // 오류 메시지 제거
		
		if(e.keyCode == 13) {
			e.preventDefault(); // Enter 키를 눌렀을 때 기본 동작 방지 --> 엔터키를 눌렀을 때 select 요소가 펼쳐지는 현상 방지 
			goSearch();
		}
	});// end of $("input:text[name='search_word']").bind("keydown", function(e){})-----------------------------
	
	// 유효성 검사 메시지 반복하여 출력되지 않도록 제어 
	$("select[name='search_type']").bind("change", function(e){
		search_type_dom.setCustomValidity("");  // 오류 메시지 제거
	});// end of $("select[name='search_type']").bind("change", function(e){})----------------------------------
	
	
	
	// === 특정 회원을 클릭하면 그 회원의 상세정보를 보여주기 시작 === //
	$("table#member_table tr.member_info").click( e => {
		
		const userid = $(e.target).parent().children(".userid").text();
		//alert(userid);
		
		const frm = document.useridFrm;
		
		if(userid != ""){
			frm.userid.value = userid;
			
			const container = $("div#member_detail_modal"); // 모달을 넣을 위치
			
			// AJAX(클릭한 회원 상세페이지)
			$.ajax({
				url: 'memberSelectOneDetail.mp',  
	            type: 'post',
	            data: { "userid" : userid },  
				async: true,
				
	            success: function(json) {
					const mvo = json; // JSON 데이터 처리 (JSON 응답에서 mvvo라는 속성을 추출)
					
					//console.log(mvvo);
					// 모달 HTML 구조
					const modal_popup = `<div class="modal fade" id="member_find" tabindex="-1" aria-labelledby="member_find" aria-hidden="true">
					                        <div class="modal-dialog modal-lg">
					                            <div class="modal-content">
					                                <!-- Modal Header -->
					                                <div class="modal-header">
					                                    <h5 class="modal-title" id="member_find">${mvo.user_id} 회원 상세정보</h5>
					                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					                                        <span aria-hidden="true">&times;</span>
					                                    </button>
					                                </div>

					                                <!-- Modal Body -->
					                                <div class="modal-body">
					                                    <!-- 회원 정보와 이용내역 -->
					                                    <table class="table">
					                                        <tbody>
																<tr>
																	<td>이름</td>
																	<td colspan="2">${mvo.name}</td>
																</tr>
																<tr>
																    <td>연락처</td>
																	<td colspan="2">${mvo.mobile}</td>
																</tr>
																<tr>
																	<td>이메일</td>
																	<td colspan="2">${mvo.email}</td>
																</tr>
					                                            <tr>
					                                                <td>예매횟수</td>
					                                                <td>총결제액</td>
																	<td>포인트</td>
					                                            </tr>
																<tr>
																    <td>${mvo.reserved_cnt}</td>
																    <td>${mvo.pay_sum}</td>
																	<td>${mvo.point_sum}</td>
																</tr>
					                                        </tbody>
					                                    </table>
														
														<div id="graph_container">
										                   <canvas id="reserved_graph"></canvas>
										               </div>

					                                    <!-- 버튼 -->
					                                    <div class="modal-footer">
					                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">돌아가기</button>
					                                    </div>
					                                </div>
					                            </div>
					                        </div>
					                    </div>`;
										
					container.html(modal_popup);
					
					// 모달을 표시
					$('div#member_find').modal('show');
					
	            },
	            error: function() {
	                alert("회원 상세조회에 실패했습니다. 다시 시도해주세요.");
	            }
			});// end of $.ajax({});-------------------
		}
		
		       
	});// end of $("table#memberTbl tr.memberInfo").click( e => {})----------------------------
	
	// === 특정 회원을 클릭하면 그 회원의 상세정보를 보여주기 끝  === //
		
});// end of $(document).ready(function(){})------------------------------



// === 회원 검색에 따른 상영시간이 등록된 회원 목록을 보여주는 이벤트 처리
function goSearch() {
	
	const search_type = $("select[name='search_type']").val();
	const search_word = $("input:text[name='search_word']").val().trim();

	var search_type_dom = $("select[name='search_type']")[0];
	var search_word_dom = $("input:text[name='search_word']")[0];
	
	var is_empty = false;
		
	if (search_type && !search_word) {
		// 검색어 유형만 선택한 경우
		search_word_dom.setCustomValidity("검색어를 입력해주세요!");
		search_word_dom.reportValidity();  // 유효성 검사 메시지 표시
					
		is_empty = true;
	} 
	else if (!search_type && search_word) {
		// 검색어만 입력한 경우
		search_type_dom.setCustomValidity("검색 유형을 선택해주세요!");
		search_type_dom.reportValidity();  // 유효성 검사 메시지 표시
					
		is_empty = true;
	}
	
	// === 유효성 검사 후 폼 제출 여부 === //
	if (is_empty) {
	    return;  // 나머지 검사 생략
	}
	else {
				
		$("input:text[name='search_word']").val(search_word);
		
		const frm = document.member_search_frm
		// frm.action = "memberList.mp";
		// frm.method = "get";
		
		frm.submit();
	}

}// end of function goSearch() {}-------------------------------------------