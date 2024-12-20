
$(document).ready(function(){
	
	// === 영화 검색에 따른 목록을 보여주는 이벤트 처리 시작 === //
	$("button[type='button']").click(function(){
		
		const movie_title = $("input:text[name='movie_title']").val().trim();
		
		var movie_title_dom = $("input:text[name='movie_title']")[0];
		
		if (movie_title == "") {
			movie_title_dom.setCustomValidity("검색어를 입력하세요!");
			movie_title_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			return;
		}

		movie_title_dom.setCustomValidity("");  // 오류 메시지 제거
		
		const frm = document.movie_search_frm;
		frm.action = "movieRegisteredList.up";	// 현재 페이지 보여준다(생략가능)
		frm.method = "get";	
		frm.submit();

	});// end of $("button[type='button']").click(function(){})----------------
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
				url: 'movieSelectOneDetail.up',  
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
					                                    <h5 class="modal-title" id="movie_find">영화 상세 정보</h5>
					                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					                                        <span aria-hidden="true">&times;</span>
					                                    </button>
					                                </div>

					                                <!-- Modal Body -->
					                                <div class="modal-body">
					                                    <!-- 영화 포스터와 제목 -->
					                                    <table class="table">
					                                        <tbody>
																<tr>
																	<td rowspan="3"><img src="${mvvo.poster_file}" alt="" style="width:50px; height:auto;"></td>
																	<td colspan="2">${mvvo.movie_title}</td>
																</tr>
					                                            <tr>
					                                                <td>상영 시간<br>${mvvo.running_time}분</td>
					                                                <td>상영 등급<br><img src="${mvvo.ctxPath}/images/admin/movie_grade/${mvvo.movie_grade}.png" alt="${mvvo.movie_grade}" style="width:30px; height:auto;"></td>
					                                            </tr>
					                                            <tr>
					                                                <td>개봉 일자<br>${mvvo.start_date}</td>
					                                                <td>종영 일자<br>${mvvo.end_date}</td>
					                                            </tr>
					                                            <tr>
					                                                <td>장르</td>
					                                                <td colspan="2">${mvvo.fk_category_code}</td>
					                                            </tr>
					                                            <tr>
					                                                <td>감독</td>
					                                                <td colspan="2">${mvvo.director}</td>
					                                            </tr>
					                                            <tr>
					                                                <td>배우</td>
					                                                <td colspan="2">${mvvo.actor}</td>
					                                            </tr>
					                                            <tr>
					                                                <td>줄거리</td>
					                                                <td colspan="2">${mvvo.content}</td>
					                                            </tr>
					                                            <tr>
					                                                <td>예고편</td>
					                                                <td colspan="2"><a href="${mvvo.video_url}">${mvvo.video_url}</a></td>
					                                            </tr>
					                                            <tr>
					                                                <td>등록 일자</td>
					                                                <td colspan="2">${mvvo.register_date}</td>
					                                            </tr>
					                                        </tbody>
					                                    </table>

					                                    <!-- 버튼 -->
					                                    <div class="modal-footer">
					                                        <button type="button" class="btn btn-primary" id="edit_button">수정하기</button>
					                                        <button type="button" class="btn btn-danger" id="delete_button">삭제하기</button>
					                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소하기</button>
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
			
			frm.action = "movieEdit.up";  // 수정할 페이지로 이동
			frm.method = "post";  // POST 방식
			frm.submit();  // 폼 전송
		});  // end of $(document).on('click', '#edit_button')
		// === 수정하기 버튼 클릭 이벤트 끝 === //
			
		
	});// end of $("tbody > tr").click( (e) => {})-------------------------------	
	// === 특정 영화에 대한 상세정보 보기 이벤트 처리 끝 === //
	
	
	
	
	
	
});// end of $(document).ready(function(){})------------------------------
