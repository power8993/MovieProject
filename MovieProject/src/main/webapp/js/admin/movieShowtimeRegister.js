
var showtime_select_btn_click = false;

$(document).ready(function(){
	
	// 상영종료시간 입력 비활성화
	$("input[id='end_time']").prop('disabled', true);
	
	// ===== [상영시작시간] 을 선택하면 [상영종료시간] 에 자동 입력되는 이벤트 시작 ===== //
	const running_time = parseInt($("#tbl_showtime_register > tbody > tr:nth-child(2) > td:nth-child(1) > span").text(), 10); // running_time을 정수로 변환
	$("input[id='start_time']").bind("change", (e) => {
		const start_time = new Date(e.target.value);	// 입력한 '상영시작시간'
		const end_time = new Date(start_time.getTime() + (running_time * 60000)); 
						// 밀리초로 변환하여 상영시작시간 + 상영시간 => 상영종료시간을 알 수 있다. 
		
		// 상영 종료시간 입력	
		// "YYYY-MM-DDTHH:MM" 형식으로 변환
		const year = end_time.getFullYear();
	    const month = (end_time.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 +1
	    const day = end_time.getDate().toString().padStart(2, '0');
	    const hours = end_time.getHours().toString().padStart(2, '0');
	    const minutes = end_time.getMinutes().toString().padStart(2, '0');
	
		// ISO 8601 형식 => 국제적으로 사용되는 날짜 및 시간의 표준 형식
	    const formatted_end_time = `${year}-${month}-${day}T${hours}:${minutes}`;
	    
	    $("input[name='end_time']").val(formatted_end_time); 
		
		// 실제로 보낼 end_time 의 값
		$("input[name='end_time_val']").val(formatted_end_time);
		
	});// end of $("input:datetime-local[id='start_time']").bind("change", (e) => {})-----------------
	// ===== [상영시작시간] 을 선택하면 [상영종료시간] 에 자동 입력되는 이벤트 끝 ===== //
	
	
	let is_exist = false;
	let last_end_time = null;
	// ===== [상영시간 조회하기] 버튼 클릭 시 필수입력 값 유효성 검사 시작 ===== //
	$("button[id='showtime_select_btn']").click(function(e) {
		
		showtime_select_btn_click = true;
		
		const start_time = $("input[name='start_time']").val();
		const end_time = $("input[name='end_time']").val();
		const screen_no = $("input:radio[name='screen_no']:checked").val();
		const screen_no_length = $("input:radio[name='screen_no']:checked").length;
		
		var start_time_dom = $("input[id='start_time']")[0];
		var screen_no_dom = $("input:radio[name='screen_no']")[0];
		
		start_time_dom.setCustomValidity("");  // 오류 메시지 제거
		
		if(start_time == "") {
			start_time_dom.setCustomValidity("상영시간 입력 후 조회 가능합니다!");
			start_time_dom.reportValidity();  // 유효성 검사 메시지 표시
			return;
		}
		else if(screen_no_length == 0) {
			screen_no_dom.setCustomValidity("상영관 입력 후 조회 가능합니다!");
			screen_no_dom.reportValidity();  // 유효성 검사 메시지 표시
			return;
		}
		else{
			// 모달을 넣을 위치
			const container = $("div#showtime_conflict_check_modal");
			
			// AJAX(선택한 시간대의 영화가 있는지 조회)
			$.ajax({
				url:  'showtimeConflictMovie.mp',
				type: 'get',
				data: {"start_time":start_time
					  ,"end_time":end_time
					  ,"screen_no":screen_no
					  },
				async: true,
				
				success: function(jsonObj_ctxPath) {
					
					const modal_popup = `<div class="modal fade" id="movie_find" tabindex="-1" aria-labelledby="movie_find" aria-hidden="true">
					                        <div class="modal-dialog modal-lg" style="max-width: 85%;">
					                            <div class="modal-content">
					                                <!-- Modal Header -->
					                                <div class="modal-header">
					                                    <h5 class="modal-title" id="movie_find">상영시간 조회 결과</h5>
					                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					                                        <span aria-hidden="true">&times;</span>
					                                    </button>
					                                </div>
													
													<!-- Modal Body -->
								                    <div class="modal-body">
													
													<!-- 검색 결과 -->
							                        <div id="search_result" style="display:none;">
							                            <table class="table">
							                                <thead>
							                                    <tr>
							                                        <th>영화제목</th>
							                                        <th>상영등급</th>
							                                        <th>상영관</th>
																	<th>러닝타임</th>
																	<th>상영시작시간</th>
																	<th>상영종료시간</th>
							                                    </tr>
							                                </thead>
							                                <tbody id="movie_table"></tbody>
							                            </table>
							                        </div>
													
													<!-- 검색된 영화가 없을 때 -->
							                        <div id="no_movies_message" style="display:none;">
							                            <p>해당 상영관 및 상영시간대에 중첩된 영화가 없습니다.</p>
							                        </div>
												</div>
												
												<!-- Modal Footer -->
							                    <div class="modal-footer">
							                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							                    </div>
							                </div>
							            </div>
							        </div>`;
						
					container.html(modal_popup); // 모달 HTML 삽입
					
					// 모달을 표시			
					$('div#movie_find').modal('show'); // 모달 띄우기
					
					// 모달 닫기
				    $(".close").click(function() {
				        $('#movie_find').modal('hide');
				    });		
					
					
					const search_result = $('tbody#movie_table'); // 검색 결과를 보여줄 곳
	                search_result.empty();	// 검색할 때마다 비운다.
					
					const movieArray = jsonObj_ctxPath.movie_List;
					const ctxPath = jsonObj_ctxPath.ctxPath;
					
					
					if (movieArray.length > 0) {
						// 상영시간과 상영관이 중첩된 영화가 있는 경우
						
						is_exist = true;

						movieArray.forEach(movie => {
							
							$('div#search_result').show();      // 검색 결과 테이블 표시
							$('div#no_movies_message').hide();  // 검색된 영화가 없다는 메시지 숨기기
							
							const movie_result_list = `<tr>
													   		<td><img src="${ctxPath}/images/admin/poster_file/${movie.poster_file}" alt="${movie.movie_title}" style="width:50px; height:auto;"> ${movie.movie_title}</td>
															<td><img src="${ctxPath}/images/admin/movie_grade/${movie.movie_grade}.png" alt="${movie.movie_grade}" style="width:30px; height:auto;"></td>
															<td>${movie.screen_no}관</td>
															<td>${movie.running_time}분</td>
															<td>${movie.start_time.substring(0,16)}</td>
															<td>${movie.end_time.substring(0,16)}</td>
													   </tr>`;
							search_result.append(movie_result_list);  // 테이블에 영화 데이터 추가
							
							// 가장 나중인 end_time을 가져와서 유효성 검사에 사용
							const movie_end_time = new Date(movie.end_time);
				            if (last_end_time === null || movie_end_time > last_end_time) {
				                last_end_time = movie_end_time; 
								console.log(last_end_time);
				            }
							
						});
					}
					else {
						// 상영시간과 상영관이 중첩된 영화가 없는 경우
						$('div#search_result').hide();      // 검색 결과 테이블 숨기기
						$('div#no_movies_message').show();  // "해당 상영관 및 상영시간대에 중첩된 영화가 없습니다." 메시지 표시
					}
				},
				error: function() {
	                alert("해당하는 시간대와 중첩된 영화 조회에 실패했습니다. 다시 시도해주세요.");
	            }
			});// end of $.ajax({})------------------------------------
					
			
		}
	});// end of $("button[id='showtime_select_button']").click(function(e) {})------------------------
	// ===== [상영시간 조회하기] 버튼 클릭 시 필수입력 값 유효성 검사 끝  ===== //
	
	
	
	// ===== [상영일정 등록] 버튼 클릭 시 필수입력 값 유효성 검사 시작 ===== //
	$("button[id='showtime_resister_btn']").click(function(e) {
		
		if (!showtime_select_btn_click) {
			alert("[상영시간 조회하기] 버튼을 클릭하여 상영시간 중첩 여부를 먼저 확인해주세요!");
			
			e.preventDefault();  // 폼 제출을 막음
			return;  // 나머지 검사 생략
		}
		else {
			const user_confirm = confirm("상영일정을 등록하시겠습니까?");
			
			if(user_confirm){
				let is_empty = false;
				
				// ===== 상영관번호 유효성 검사 ===== //
				const radio_check_length = $("input:radio[name='screen_no']:checked").length; // radio 에 체크되어진 개수
				var screen_no_dom = $("input:radio[name='screen_no']")[0];
				
				if(radio_check_length == 0) {
					// 선택하지 않았을 경우
					screen_no_dom.setCustomValidity("상영관을 선택해주세요!");
					screen_no_dom.reportValidity();  // 유효성 검사 메시지 표시
					
					is_empty = true;
				}
				
				if (is_empty) {
					e.preventDefault();  // 폼 제출을 막음
					return;  // 나머지 검사 생략
				}
				
				// ===== 상영시간 유효성 검사 ===== //
				const start_time = $("input[id='start_time']").val();
				const end_time = $("input[id='end_time']").val();
				var start_time_dom = $("input[id='start_time']")[0];
				
				if(start_time == "" || end_time == "") {
					// 시간을 입력하지 않았을 경우
					start_time_dom.setCustomValidity("상영시간을 입력해주세요!");
					start_time_dom.reportValidity();  // 유효성 검사 메시지 표시
					
					is_empty = true;
				}
				else {
					start_time_dom.setCustomValidity("");  // 오류 메시지 제거
				}
				
				if (is_empty) {
					e.preventDefault();  // 폼 제출을 막음
					return;  // 나머지 검사 생략
				}
				
				// ===== 상영시간대 중첩 검사 ==== //
				const last_end_time_val = new Date(last_end_time);	// 가장 마지막 end_time
				const input_start_time_val = new Date(start_time);  // 입력한 상영 시작 시간인 start_time
				
				const time_minus = (input_start_time_val - last_end_time_val) / (1000 * 60);  // 밀리초를 분으로 변환
				
				
				
				if(is_exist) {
					// 같은 상영관 및 상영시간대에 이미 등록된 영화가 있는 경우
					start_time_dom.setCustomValidity("선택하신 시간대 및 상영관에 상영중인 영화가 있습니다!");
					start_time_dom.reportValidity();  // 유효성 검사 메시지 표시
					
					is_exist = false;
					is_empty = true;
				}
				else if (time_minus < 10) {
					// 입력한 start_time 이 앞선 영화의 상영 종료시간보다 10분 미만인 경우
					start_time_dom.setCustomValidity("앞선 영화와 최소 10분 이상의 간격을 두어야 합니다!");
					start_time_dom.reportValidity();  // 유효성 검사 메시지 표시
					
					is_empty = true;
				}
				
				if (is_empty) {
					e.preventDefault();  // 폼 제출을 막음
					return;  // 나머지 검사 생략
				}
				
			}
			else {
				const start_time = $("input[id='start_time']").val();
				const end_time = $("input[id='end_time']").val();
				const last_end_time_val = new Date(last_end_time);	// 가장 마지막 end_time
				const input_start_time_val = new Date(start_time);  // 입력한 상영 시작 시간인 start_time		
				const time_minus = (input_start_time_val - last_end_time_val) / (1000 * 60);  // 밀리초를 분으로 변환
				
				console.log("start_time ==> " + start_time);
				console.log("end_time ==> " + end_time);
				console.log("last_end_time_val ==> " + last_end_time_val);
				console.log("input_start_time_val ==> " + input_start_time_val);
				console.log("time_minus ==> " + time_minus);
				
				e.preventDefault();  // 폼 제출을 막음
				return;
			}
		}
	});// end of $("button[id='showtime_resister_btn']").click(function(e) {})-------------
	// ===== [상영일정 등록] 버튼 클릭 시 필수입력 값 유효성 검사 끝  ===== //
	
});// end of $(document).ready(function(){})-----------------------


// ===== 상영일정 등록 페이지에서 [취소하기]를 눌렀을 때의 클릭이벤트 ===== //
function confirmCancel() {

    const user_confirm = confirm("상영일정 등록을 취소하시겠습니까?");

    if (user_confirm) {
        // 사용자가 '확인'을 눌렀을 경우
		window.location.href = "admin.mp"; // 관리자의 첫 페이지로 이동
    }

}// end of function confirmCancel() {}----------------------------------


// Function
function formatChange(date_val) {
	const date_time = new Date(date_val);	// 입력한 시간

	// "YYYY-MM-DDTHH:MM" 형식으로 변환
	const year = date_time.getFullYear();
    const month = (date_time.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = date_time.getDate().toString().padStart(2, '0');
    const hours = date_time.getHours().toString().padStart(2, '0');
    const minutes = date_time.getMinutes().toString().padStart(2, '0');

	// ISO 8601 형식 => 국제적으로 사용되는 날짜 및 시간의 표준 형식
    const formatted_date_time = `${year}-${month}-${day}T${hours}:${minutes}`;
	
	return formatted_date_time;
}// end of function formatChange(date_val) {}---------------------------------