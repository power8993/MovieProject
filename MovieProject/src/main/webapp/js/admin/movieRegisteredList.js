
let confirm_movie_title;	// [삭제하기] 버튼의 confirm 에서 사용할 변수

// [상영일정등록] 버튼의 유효성 검사
let start_date_val;
let end_date_val;
let poster_file_val;
let video_url_val;

$(document).ready(function(){

	// === 영화 검색에 따른 목록을 보여주는 이벤트 처리 시작 === //
	$("button[type='button']").click(function(){
		goSearch();
	});// end of $("button[type='button']").click(function(){})----------------
	
	var search_word_dom = $("input:text[name='search_word']")[0];
	
	$("input:text[name='search_word']").bind("keydown", function(e){
		search_word_dom.setCustomValidity("");  // 오류 메시지 제거
		
		if(e.keyCode == 13) {
			e.preventDefault(); // Enter 키를 눌렀을 때 기본 동작 방지 --> 엔터키를 눌렀을 때 select 요소가 펼쳐지는 현상 방지 
			goSearch();
		}
	});// end of $("input:text[name='search_word']").bind("keydown", function(e){})-----------------------------
	// === 영화 검색에 따른 목록을 보여주는 이벤트 처리 끝  === //
	

	
	// === 특정 영화에 대한 상세정보 보기 이벤트 처리 시작 === //
	$("tbody").on("click", "tr", function(e) {
		
		const seq =$(this).find("span").text();
		// console.log(seq);
		const frm = document.seqFrm;
		
		if(seq != ""){
			frm.seq.value = seq;
			
			const container = $("div#movie_detail_modal"); // 모달을 넣을 위치
	
			// 기존 모달이 있으면 지우기
            if ($('#movie_find').length > 0) {
                $('#movie_find').remove();  // 기존 모달 제거
            }
			
			// AJAX(클릭한 영화 상세페이지)
			$.ajax({
				url: 'movieSelectOneDetail.mp',  
	            type: 'post',
	            data: { "seq" : seq },  
				async: true,
				
	            success: function(json) {
					const mvvo = json; // JSON 데이터 처리 (JSON 응답에서 mvvo라는 속성을 추출)
					
					//console.log(mvvo);
					// 모달 HTML 구조
					const modal_popup = `<div class="modal fade" id="movie_find" tabindex="-1" aria-labelledby="movie_find" aria-hidden="true">
					                        <div class="modal-dialog modal-lg">
					                            <div class="modal-content">
					                                <!-- Modal Header -->
					                                <div class="modal-header">
					                                    <h5 class="modal-title" id="movie_find"><i class="fa-solid fa-clapperboard" style="color: #252422;"></i>&nbsp;영화 상세 정보</h5>
					                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					                                        <span aria-hidden="true">&times;</span>
					                                    </button>
					                                </div>

					                                <!-- Modal Body -->
					                                <div class="modal-body">
					                                    <!-- 영화 포스터와 제목 -->
					                                    <table class="table" id="modal_table">
					                                        <tbody>
																<tr>
																	<td rowspan="3"><img src="${mvvo.ctxPath}/images/admin/poster_file/${mvvo.poster_file}" alt="" style="width:230px; height:auto;"></td>
																	<td colspan="2" class="modal_movie_title">${mvvo.movie_title}</td>
																</tr>
					                                            <tr>
					                                                <td class="modal_text">상영 시간<br><span class="text_ignore">${mvvo.running_time}분</span></td>
					                                                <td class="modal_text">상영 등급<br><img src="${mvvo.ctxPath}/images/admin/movie_grade/${mvvo.movie_grade}.png" alt="${mvvo.movie_grade}" style="width:40px; height:auto; margin-left:4%; padding-top:3%;"></td>
					                                            </tr>
					                                            <tr>
					                                                <td class="modal_text">개봉 일자<br><span class="text_ignore">${mvvo.start_date}</span></td>
					                                                <td class="modal_text">종영 일자<br><span class="text_ignore">${mvvo.end_date}</span></td>
					                                            </tr>
					                                            <tr>
					                                                <td class="modal_text">장르</td>
					                                                <td colspan="2" class="modal_content">${mvvo.fk_category_code}</td>
					                                            </tr>
					                                            <tr>
					                                                <td class="modal_text">감독</td>
					                                                <td colspan="2" class="modal_content">${mvvo.director}</td>
					                                            </tr>
					                                            <tr>
					                                                <td class="modal_text">배우</td>
					                                                <td colspan="2" class="modal_content">${mvvo.actor}</td>
					                                            </tr>
					                                            <tr>
					                                                <td class="modal_text">줄거리</td>
					                                                <td colspan="2" class="modal_content">${mvvo.content}</td>
					                                            </tr>
					                                            <tr>
					                                                <td class="modal_text">예고편</td>
					                                                <td colspan="2" class="modal_content"><a href="${mvvo.video_url}">${mvvo.video_url}</a></td>
					                                            </tr>
					                                            <tr>
					                                                <td class="modal_text">등록 일자</td>
					                                                <td colspan="2" class="modal_content">${mvvo.register_date}</td>
					                                            </tr>
					                                        </tbody>
					                                    </table>

					                                    <!-- 버튼 -->
					                                    <div class="modal-footer">
															<button type="button" class="btn btn-success" id="register_button" style="margin-right: auto;">상영일정 등록</button>
					                                        <button type="button" class="btn btn-primary" id="edit_button">수정하기</button>
					                                        <button type="button" class="btn btn-danger" id="delete_button">상영중지</button>
					                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
					                                    </div>
					                                </div>
					                            </div>
					                        </div>
					                    </div>`;
										
					container.html(modal_popup);
					
					// seq_movie_no 를 수정하기 이벤트로 넘기기위해 폼태그에 다시 대입
					const frm =document.seqFrm;
					frm.seq.value = mvvo.seq_movie_no;
					
					// 모달을 표시
					$('div#movie_find').modal('show');
					
					// [상영중지] 버튼의 confirm 에서 사용
					confirm_movie_title = mvvo.movie_title
					
					// [상영일정등록] 버튼의 유효성 검사를 위한 임시 저장 변수
					start_date_val = mvvo.start_date;
					end_date_val = mvvo.end_date;
					poster_file_val = mvvo.poster_file;
					video_url_val = mvvo.video_url;
					
	            },
	            error: function() {
	                alert("영화 상세조회에 실패했습니다. 다시 시도해주세요.");
	            }
			});// end of $.ajax({});-------------------

		}
		
		
		// === 수정하기 버튼 클릭 이벤트 (모달 내에서) === //
		$(document).on('click', '#edit_button', function(e) {
			const frm = document.seqFrm;
			//const seq = frm.seq.value;	// 선택한 영화의 seq
			
			frm.action = "movieEdit.mp";  // 수정할 페이지로 이동
			frm.method = "post";  // POST 방식
			frm.submit();  // 폼 전송
		});  // end of $(document).on('click', '#edit_button')
		// === 수정하기 버튼 클릭 이벤트 끝 === //
		
		
		// === 상영중지 버튼 클릭 이벤트 (모달 내에서) === //
		$(document).one('click', '#delete_button', function(e) {
			
			var current_date = new Date();
			var year = current_date.getFullYear();
		    var month = ("0" + (current_date.getMonth() + 1)).slice(-2); // 0부터 시작하므로 +1
		    var day = ("0" + current_date.getDate()).slice(-2);
			var current_day = year + "-" + month + "-" + day;
			
			if(start_date_val <= current_day && current_day <= end_date_val) {
				alert("현재 상영 기간 내의 영화는 삭제가 불가합니다.");
				return;
			}
			else {
				const user_confirm = confirm("영화 "+ confirm_movie_title +"를 정말로 상영중지(비활성화)하시겠습니까?");
				if(user_confirm) {
					const frm = document.seqFrm;
					//const seq = frm.seq.value;	// 선택한 영화의 seq
					
					frm.action = "movieDelete.mp";  // 삭제할 페이지로 이동
					frm.method = "post";  // POST 방식
					frm.submit();  // 폼 전송
				}
				else { 
					return;
				}
			}
		});  // end of $(document).on('click', '#delete_button')
		// === 삭제하기 버튼 클릭 이벤트 끝 === //
		
		
		// === 상영일정 등록 버튼 클릭 이벤트 (모달 내에서) === //
		$(document).one('click', '#register_button', function(e) {	// 이벤트 핸들러가 중복 방지 'one' 사용
																	// 버튼 클릭 후 이벤트 핸들러가 자동으로 제거되어 클릭 이벤트가 한 번만 실행되게 한다.
			
			if(start_date_val  == undefined || start_date_val  == "" ||
			   end_date_val    == undefined || end_date_val    == "" ||
			   poster_file_val == undefined || poster_file_val == "" ||
			   video_url_val   == undefined || video_url_val   == "") {
				alert("모든 영화정보를 [수정하기]에서 입력해주세요.");
				return; 
			}
			else {
				var current_date = new Date();
				var year = current_date.getFullYear();
			    var month = ("0" + (current_date.getMonth() + 1)).slice(-2); // 0부터 시작하므로 +1
			    var day = ("0" + current_date.getDate()).slice(-2);
				var current_day = year + "-" + month + "-" + day;
				
				if(current_day > end_date_val) {
					alert("종영일자가 지난 영화입니다.\n상영일정을 등록하려면 [수정하기]에서 상영일자를 수정해주세요.");
					return;
				}
				else {
					const frm = document.seqFrm;
					//const seq = frm.seq.value;	// 선택한 영화의 seq

					frm.action = "movieShowtimeRegister.mp";  // 상영일정 등록 페이지로 이동
					frm.method = "post";  // POST 방식
					frm.submit();  // 폼 전송	
				}

			}
			
		});  // end of $(document).on('click', '#register_button')
		// === 상영일정 등록 버튼 클릭 이벤트 끝 === //
		
	});// end of $("tbody > tr").click( (e) => {})-------------------------------	
	// === 특정 영화에 대한 상세정보 보기 이벤트 처리 끝 === //
	
});// end of $(document).ready(function(){})------------------------------



// === 영화 검색에 따른 목록을 보여주는 이벤트 처리
function goSearch() {

		const search_category = $("select[name='search_category']").val();
		const search_type = $("select[name='search_type']").val();
		const search_word = $("input:text[name='search_word']").val().trim();
		$("input:text[name='search_word']").val(search_word);
		
		var search_word_val_dom = $("input:text[name='search_word']")[0];
		var search_type_val_dom = $("select[name='search_type']")[0];
		
		// 검색어가 비어 있을 경우 유효성 검사
		if ((search_category && search_type && search_word == "") ||
			(search_category == "" && search_type && search_word == "")) {

			search_word_val_dom.setCustomValidity("검색어를 입력해주세요!");
			search_word_val_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			return;  // 오류 발생 시 검색 진행하지 않음
		}

		// 검색타입을 선택하지 않고 검색어를 입력했을 경우
		if ((search_category && search_type == "" && search_word) ||
	        (search_category == "" && search_type == "" && search_word)) {
			search_type_val_dom.setCustomValidity("검색 타입을 선택해주세요!");
			search_type_val_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			return;
		}

		// 오류 메시지 제거
	    search_word_val_dom.setCustomValidity("");  
	    search_type_val_dom.setCustomValidity("");  

		const frm = document.movie_search_frm
		// frm.action = "movieRegisteredList.mp";
		// frm.method = "get";
		
		frm.submit();
}// end of function goSearch() {}-------------------------------------------



// === 상영작/미상영작 버튼 클릭 이벤트 처리
function toggleMovieInvalidStatus() {
	
	var invalid_movie =  $("span#invalid_movie");
	
	if (invalid_movie.text() == "상영작") {
		$("input[name='invalid_movie']").val("미상영작");
	} 
	else {
		$("input[name='invalid_movie']").val("상영작");
	}
	
	const frm = document.movie_search_frm;
	// frm.action = "movieRegisteredList.mp";
	// frm.method = "get";
	
	frm.submit();
	
}// end of function toggleMovieInvalidStatus() {}---------------------------
