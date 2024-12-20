

$(document).ready(function() {
	

	$("input[id='movie_title']").focus(); // 영화제목 입력에 포커스

	// ===== 영화제목 유효성 검사 시작 ===== //
	$("input[id='movie_title'").blur((e) => {
		const movie_title = $("input[id='movie_title']").val().trim();
		var movie_dom = $("input[id='movie_title'")[0];

		if (movie_title == "") {
			// 입력하지 않거나 공백만 입력했을 경우

			// 영화 제목이 공백이라면 사용자 정의 오류 메시지 설정
			movie_dom.setCustomValidity("영화 제목을 입력해주세요!");

		} else {
			// 앞뒤 공백제거한 영화제목을 넘길 수 있도록 넣는다.
			$("input[id='movie_title']").val(movie_title.replace(/\s+/g, ' ')); // 여러 공백을 하나로 변환
			
			movie_dom.setCustomValidity("");  // 오류 메시지 제거
			
			movie_title_exist = true;
		}

		// 유효성 검사 메시지를 화면에 표시
		movie_dom.reportValidity();  // 유효성 검사 메시지 표시
	});// end of $("input[id='movie_title'").blur((e) => {})---------------------
	// ===== 영화제목 유효성 검사 끝  ===== //

	
	// ===== 감독 입력 시작 ===== //
	// --- 입력값 나열되도록 하기
	const director = $("input[name='director']");
	const director_list = $("div#director_tags");

	// Enter 키 눌렀을 때
	director.on('keydown', function(e) {
		if (e.keyCode === 13) {  // Enter 키
			e.preventDefault();  // 기본 동작 방지 (폼 제출 방지)

			const director_name = director.val().trim();

			if (director_name !== "") {
				// 감독 이름을 네모 칸 형태로 추가
				const director_tag = `
						            <div class="director_tag">
						                ${director_name}
						                <button type="button" class="remove_director">x</button>
						            </div>`;
				director_list.append(director_tag);  // 목록에 태그 추가
				director.val('');  // 입력 필드 초기화
				director.focus();   // 입력 필드에 포커스 다시 두기
			}
		}
	});

	// 감독 이름 삭제 기능
	director_list.on("click", "button.remove_director", function() {
		$(this).parent().remove();  // 해당 태그 삭제
	});
	// ===== 감독 입력 끝 ===== //


	// ===== 배우 입력 시작 ===== //
	// --- 입력값 나열되도록 하기
	const actor = $("input[name='actor']");
	const actor_list = $("div#actor_tags");

	// Enter 키 눌렀을 때
	actor.on('keydown', function(e) {
		if (e.keyCode === 13) {  // Enter 키
			e.preventDefault();  // 기본 동작 방지 (폼 제출 방지)

			const actor_name = actor.val().trim();

			if (actor_name !== "") {
				// 배우 이름을 네모 칸 형태로 추가
				const actor_tag = `
						            <div class="actor_tag">
						                ${actor_name}
						                <button type="button" class="remove_actor">x</button>
						            </div>`;
				actor_list.append(actor_tag);  // 목록에 태그 추가
				actor.val('');  // 입력 필드 초기화
				actor.focus();   // 입력 필드에 포커스 다시 두기
			}
		}
	});

	// 배우 이름 삭제 기능
	actor_list.on("click", "button.remove_actor", function() {
		$(this).parent().remove();  // 해당 태그 삭제
	});
	// ===== 배우 입력 끝 ===== //
	
	//char_count
	
	

		
	
	// ===== 제출 버튼 클릭 시 [필수입력값] 유효성 검사 시작 ===== //
	// 순서대로 유효성 검사 및 메세지가 띄워지도록 각 항목마다 if(is_empty) 작성
	$("button[id='resister_btn']").click(function(e) {
		
		// ---- [감독 / 배우] 이름 추출 시작 ---- // 	
	    // 감독 이름을 콤마로 구분하여 추출
	    const director_list = [];
	    $("div#director_tags .director_tag").each(function() {
			const director_name = $(this).text().trim().replace('x', '').trim(); // 클래스가 .director_tag 인 태그의 x제거, 공백 제거 후 추출
	        director_list.push(director_name);  // 'x' 버튼 제거 후 이름만 저장
	    });
	    const director_value = director_list.join(', ');  // 감독 이름을 콤마로 구분

	    // ---- 배우 이름을 콤마로 구분하여 추출
	    const actor_list = [];
	    $("div#actor_tags .actor_tag").each(function() {
			const actor_name = $(this).text().trim().replace('x', '').trim(); // 클래스가 .actor_tag 인 태그의 x제거, 공백 제거 후 추출
	        actor_list.push(actor_name);  // 'x' 버튼 제거 후 이름만 저장
	    });
	    const actor_value = actor_list.join(', ');  // 배우 이름을 콤마로 구분

	    // 콤마로 구분된 감독과 배우 이름을 각 input 선택자에 저장
	    $("input[name='director']").val(director_value); 
	    $("input[name='actor']").val(actor_value); 
		// ---- [감독 / 배우] 이름 추출 끝  ---- // 
		
		
		const content = $("textarea[id='content']").val().trim();
		const fk_category_code = $("select[id='fk_category_code']").val();
		const running_time = $("input[id='running_time']").val();
		const movie_grade = $("select[id='movie_grade']").val();
		const director = $("div#director_tags .director_tag");	// 태그의 개수를 확인한다.
		const actor = $("div#actor_tags .actor_tag");			// 태그의 개수를 확인한다.
		
		let is_empty = false;
		
		// ===== 줄거리 유효성 검사  ===== //
		var content_dom = $("textarea[id='content']")[0];

		if (content == "") {
			// 입력하지 않거나 공백만 입력했을 경우	
			content_dom.setCustomValidity("영화 줄거리를 입력해주세요!");
			content_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			is_empty = true;
		} 
		else if($("textarea[id='content']").val().replace(/\r?\n/g, '  ').length > 300) {
			// 영화줄거리가 300자 초과인 경우
			content_dom.setCustomValidity("영화 줄거리는 300자 이내만 입력 가능합니다.");
			content_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			is_empty = true;
	    } 
		else {
			// 앞뒤 공백제거한 줄거리를 넘길 수 있도록 넣는다.
			$("textarea[id='content']").val(content);
			
            content_dom.setCustomValidity("");  // 오류 메시지 제거
	    }
		
		if (is_empty) {
		    e.preventDefault();  // 폼 제출을 막음
		    return;  // 나머지 검사 생략
		}
			
		
		// ===== 감독 유효성 검사  ===== //
		var director_dom = $("input[name='director']")[0];
		
		if (director.length == 0) {
			director_dom.setCustomValidity("감독은 한 명 이상 입력해주세요!");
			director_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			is_empty = true;
		}
		else{
			director_dom.setCustomValidity("");  // 오류 메시지 제거
		}
		
		if (is_empty) {
	        e.preventDefault();  // 폼 제출을 막음
	        return;  // 나머지 검사 생략
	    }
		
		// ===== 배우 유효성 검사  ===== //
		var actor_dom = $("input[name='actor']")[0];
		
		if (actor.length == 0) {	
			actor_dom.setCustomValidity("감독은 한 명 이상 입력해주세요!");
			actor_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			is_empty = true;
		}
		else{
			actor_dom.setCustomValidity("");  // 오류 메시지 제거
		}
		
		if (is_empty) {
	        e.preventDefault();  // 폼 제출을 막음
	        return;  // 나머지 검사 생략
	    }
		
		// ===== 장르 유효성 검사  ===== //
		var fk_category_code_dom = $("select[id='fk_category_code']")[0];
		
		if (fk_category_code == "" ) {
			fk_category_code_dom.setCustomValidity("장르를 선택해주세요!");
			fk_category_code_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			is_empty = true;
		}
		else{
			fk_category_code_dom.setCustomValidity("");  // 오류 메시지 제거
		}
		
		if (is_empty) {
	        e.preventDefault();  // 폼 제출을 막음
	        return;  // 나머지 검사 생략
	    }
		
		// ===== 상영시간 유효성 검사  ===== //
		var running_time_dom = $("input[id='running_time']")[0];
		
		if (running_time == "" || running_time == "0") {
			running_time_dom.setCustomValidity("상영시간을 입력해주세요!");
			running_time_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			is_empty = true;
		}
		else{
			running_time_dom.setCustomValidity("");  // 오류 메시지 제거
		}
		
		if (is_empty) {
	        e.preventDefault();  // 폼 제출을 막음
	        return;  // 나머지 검사 생략
	    }
		
		// ===== 상영등급 유효성 검사  ===== //
		var movie_grade_dom = $("select[id='movie_grade']")[0];
	
		if (movie_grade == "") {
			movie_grade_dom.setCustomValidity("상영등급을 선택해주세요!");
			movie_grade_dom.reportValidity();  // 유효성 검사 메시지 표시
			
			is_empty = true;
		}
		else{
			movie_grade_dom.setCustomValidity("");  // 오류 메시지 제거
		}
		
		// ===== 모든 검사 후 폼 제출 여부 확인 ===== //
		if(is_empty){
			e.preventDefault();  // 폼 제출을 막음
		}
	});
	// ===== 제출 버튼 클릭 시 [필수입력값] 유효성 검사 유효성 검사 끝 ===== //
	
	
	
	

});// end of $(document).ready(function(){})----------------------



/////////////////////////////////////////////////////////////////////////


// ===== [등록된 영화 조회] 클릭 이벤트 처리 시작 ===== //
// ----- 기존 영화와 중복되면 안되므로, 검색할 수 있는 팝업창을 띄운다.
function checkDuplicate() {

    // 영화 제목이 입력된 경우에만 모달을 띄운다.
    if ($("input[id='movie_title']").val().trim() != "") {
        
        const container = $("div#movie_check_modal"); // 모달을 넣을 위치

        // 모달 HTML 구조
        const modal_popup = `
            <div class="modal fade" id="movie_find" tabindex="-1" aria-labelledby="movie_find" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
    
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <h5 class="modal-title" id="movie_find">등록된 영화 조회</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
    
                        <!-- Modal Body -->
                        <div class="modal-body">
                            <!-- 검색창 -->
                            <form id="movie_search_frm">
                                <div class="form-group">
                                    <label for="movie_search">영화 제목 검색</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="movie_search" placeholder="영화 제목을 입력하세요">
                                        <div class="input-group-append">
                                            <button type="button" class="btn btn-primary" id="search_button">검색하기</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
    
                            <!-- 검색 결과 -->
                            <div id="search_result" style="display:none;">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>영화제목</th>
                                            <th>장르</th>
                                            <th>상영등급</th>
                                            <th>등록일</th>
                                        </tr>
                                    </thead>
                                    <tbody id="movie_table"></tbody>
                                </table>
                            </div>
    
                            <!-- 검색된 영화가 없을 때 -->
                            <div id="no_movies_message" style="display:none;">
                                <p>검색된 영화가 없습니다.</p>
                            </div>
                        </div>
    
                        <!-- Modal Footer -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        container.html(modal_popup); // 모달 HTML 삽입

        // 모달을 표시
        $('#movie_find').modal('show'); // 모달 띄우기

        // 모달 닫기
        $(".close").click(function() {
            $('#movie_find').modal('hide');
        });

        // 검색 버튼 클릭
        $('button#search_button').on('click', function(e) {
            searchMovies(e); // 영화 검색
        });
		
		// 엔터 키에 대해서 기본 폼 제출 방지
		$("input[id='movie_search']").keydown(function(e) {
	        if (e.keyCode === 13) {  
	            e.preventDefault(); // 기본 폼 제출 방지
	            $('button#search_button').click(); // 검색 클릭 이벤트로 전환
	        }
	    });
    }
}// end of $("button[type='submit']").click(function(e) {})------------------------	



// ===== 등록된 영화 조회 팝업의 [검색] 클릭 이벤트 처리 시작 ===== //
// 영화 검색 (AJAX로 서버에서 영화 데이터를 가져옴)
function searchMovies(e) {
	
    // 메인 영화등록 form 제출을 방지
    e.preventDefault();

    const movie_title = $("input[id='movie_search']").val().trim(); // 검색할 영화 제목
    var movie_title_val_dom = $("input[id='movie_search']")[0];

    // 공백만 입력된 경우를 따로 처리
    if (movie_title == "") {
        movie_title_val_dom.setCustomValidity("조회할 영화를 입력해주세요!");
    } else {
        movie_title_val_dom.setCustomValidity("");  // 오류 메시지 제거
    }

    movie_title_val_dom.reportValidity();  // 유효성 검사 메시지 표시

    // AJAX(기존 등록된 영화 조회)
    if (movie_title != "") {
        $.ajax({
            url: 'movieRegisterSearch.up',  
            type: 'get',
            data: { "movie_title" : movie_title },  
			async: true,
            success: function(jsonObj_ctxPath) {
                const search_result = $('tbody#movie_table'); // 검색 결과를 보여줄 곳
                search_result.empty();	// 검색할 때마다 비운다.
				
				const movieArray = jsonObj_ctxPath.movie_List;
				const ctxPath = jsonObj_ctxPath.ctxPath;

                if (movieArray.length > 0) { // 검색된 영화가 있는 경우
                    
                    $('div#search_result').show();  // 검색 결과 테이블 표시
                    $('div#no_movies_message').hide();  // 검색된 영화가 없다는 메시지 숨기기
					
                    // 검색 결과를 테이블에 추가
                    movieArray.forEach(movie => {
                        const movie_result_list = `
										<tr>
						                    <td><img src="${movie.poster_file}" alt="${movie.movie_title}" style="width:50px; height:auto;"> ${movie.movie_title}</td>
						                    <td>${movie.fk_category_code}</td>
						                    <td><img src="${ctxPath}/images/admin/movie_grade/${movie.movie_grade}.png" alt="${movie.movie_grade}" style="width:30px; height:auto;"></td>
						                    <td>${movie.register_date}</td>
						                </tr>`;
                        search_result.append(movie_result_list);  // 테이블에 영화 데이터 추가
                    });
                } else {
                    // 검색된 영화가 없을 때
                    $('div#search_result').hide();  // 검색 결과 테이블 숨기기
                    $('div#no_movies_message').show();  // "검색된 영화가 없습니다." 메시지 표시
                }
            },
            error: function() {
                alert("영화 검색에 실패했습니다. 다시 시도해주세요.");
            }
        });// end of $.ajax--------------------
	
    }
}// end of function searchMovies(){}-----------------------------------------


function charCount(text, limit) {
    // 텍스트의 줄바꿈을 포함하여 글자 수 계산
    var text_value = text.value;
    
    // 줄바꿈을 2글자씩으로 처리하기 위해, 줄바꿈 문자 개수만큼 1을 더해줌
    var char_count = text_value.length + (text_value.match(/\n/g) || []).length;

    // 글자 수가 제한을 초과한 경우
    if (char_count > limit) {
        // 초과된 글자 수를 사용자에게 알림
        document.getElementById("char_count").innerHTML = "글자 수가 " + limit + "자를 초과했습니다. 현재 글자 수: " + char_count;

        // 초과된 글자 수를 넘기지 않도록 텍스트 수정 요구
        text.setCustomValidity("글자 수가 " + limit + "자를 초과했습니다.");
    } else {
        // 글자 수가 제한을 초과하지 않으면 정상적으로 표시
        document.getElementById("char_count").innerHTML = char_count + " / " + limit;
        text.setCustomValidity("");  // 유효성 검사 메시지 제거
    }
}// end of function charCount(text, length){}---------------------------