

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
					                                    <h5 class="modal-title" id="member_find"><i class="fa-solid fa-circle-user" style="color: #252422;"></i>&nbsp;${mvo.user_id} 회원 상세정보</h5>
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
																	<td class="info_field">이름</td>
																	<td colspan="2">${mvo.name}</td>
																</tr>
																<tr>
																    <td class="info_field">연락처</td>
																	<td colspan="2">${mvo.mobile.substring(0,3)}-${mvo.mobile.substring(3,7)}-${mvo.mobile.substring(7)}</td>
																</tr>
																<tr>
																	<td class="info_field">이메일</td>
																	<td colspan="2">${mvo.email}</td>
																</tr>
															</tbody>
														</table>
														
														<table class="table">
					                                        <tbody>	
					                                            <tr class="info_pay">
					                                                <td class="info_field">예매횟수</td>
					                                                <td class="info_field">총결제액</td>
																	<td class="info_field">포인트</td>
					                                            </tr>
																<tr class="info_pay">
																    <td>${mvo.reserved_cnt}&nbsp;회</td>
																    <td>${Number(mvo.pay_sum).toLocaleString()}&nbsp;원</td>
																	<td>${Number(mvo.point_sum).toLocaleString()}&nbsp;pt</td>
																</tr>
																</tbody>
														</table>
              
														<!-- 일주일간 예매추이 차트 -->
														<div id="graph_container">
															<div><i class="fa-solid fa-chart-simple" style="color: #252422;"></i>&nbsp;일주일간 예매추이</div>
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
					

					// === AJAX(클릭한 회원 예매차트) === //
					$.ajax({
						url: 'memberSelectOneDetail.mp',
						type: 'get',
						data: { "userid" : userid },  
						async: true,
						
						success: function(json) {
							
							// 라벨(현재 기점으로 7일전까지)
							const labels = getLast7Days();
							const pay_sum = [];
							const reserved_cnt = [];
							
							json.forEach(item => {	
								pay_sum.push(item.pay_sum);
								reserved_cnt.push(item.reserved_cnt);
							});
							
							// 차트를 넣을 위치
							const reserved_chart = $("canvas#reserved_graph")[0].getContext('2d');
							
							const my_linechart = new Chart(reserved_chart, {
								type: 'line',
								data: {
									labels: labels,
									datasets: [{
										label: '예매건수',
										data: reserved_cnt,
										borderColor: '#eb5e28',  // 라인 색
							            fill: false,     		 // 영역 채우기 여부
							            tension: 0.1  			 // 선의 곡률
									}]
								},
								options: {
									responsive: true,
							        scales: {
							            x: {
							                title: {
							                    display: true,
							                    //text: '날짜'
							                }
							            },
							            y: {
							                title: {
							                    display: true,
							                    //text: '예매 건수'
							                },
											min: 0,  // Y축의 최소값을 0으로 설정
											ticks: {
								                stepSize: 1, // Y축 값을 정수로 표시 (1씩 증가)
								                beginAtZero: true,  // Y축이 0부터 시작하도록 설정
								                callback: function(value) {
								                    // 값이 정수일 경우에만 표시하고, 소수점은 표시하지 않음
								                    return Number.isInteger(value) ? value : '';
								                }
								            }
							            }
							        },
									plugins: {
							            tooltip: {
							                callbacks: {
							                    title: function(tooltipItem) {
							                        return tooltipItem[0].label;  // 날짜 (mm-dd)
							                    },
							                    label: function(tooltipItem) {
							                        // 결제 금액 표시
							                        const dateIndex = tooltipItem.dataIndex;  // 마우스를 올린 날짜의 인덱스
							                        const payAmount = pay_sum[dateIndex];     // 해당 날짜의 결제 금액
							                        return '결제 금액: ' + payAmount + ' 원';    // 결제 금액만 툴팁에 표시
							                    }
							                }
							            }
							        }
								}
							});// end of new Chart(reserved_chart, {})--------------------------------
							
						},
						error: function() {
			                alert("회원 예매 내역 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
			            }
						
					});// end of $.ajax({})-------------------------------
					
					
					
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


// 현재 날짜 기준으로 7일 간 날짜 배열 생성
function getLast7Days() {
    const dates = [];
    const today = new Date();

    for (let i = 6; i >= 0; i--) {
        const date = new Date(today);
        date.setDate(today.getDate() - i);  // 오늘 날짜에서 i일 빼기
		
        const date_str = date.toISOString().split('T')[0];  // yyyy-mm-dd 형식으로 저장
		
		// '월일'만 가져오기
		const md_date = date_str.substring(5);
		
		dates.push(md_date);
    }

    return dates;
}// end of function getLast7Days() {}------------------------------------------