

$(document).ready(function() {
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 툴팁이벤트
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
    $("td.seat_status").hover(function(e) {
		
        // 좌석 배열을 가져오기
        const seat_arr_elmt = $(this).find('span#seat_arr').text();
        let seat_arr = seat_arr_elmt.split(",");
		
		// 관 가져오기
		const fk_screen_no = $(e.target).prev().prev().text().substring(0,1);

		let division = 10;
			
		if(fk_screen_no == 1) {
			division = 4;
		}
		else if(fk_screen_no == 2) {
			division = 6;
		}
		
		let chars = 'ABCDEFGD';
		let charArr = chars.split('');
		
		html = ``;
			
		html += `<span style='display: inline-block; background-color:#CCC5B9; color:white; height: 16px; width: 205px; font-size:8pt; font-weight:bold;'>SCREEN</span>
		         <br>`;
		
		seat_arr.forEach((item, index) => {
			
	        if(item == 0){
	            html += `<span class='seat' style='background-color:#403D39; color:white;'>${charArr[Math.floor(index/10)] + (index%10 + 1)}</span>`;
	        }
	        else {
	            html += `<span class='seat mouse_block' style='background-color:#EB5E28; color:white;'>${charArr[Math.floor(index/10)] + (index%10 + 1)}</span>`;
	        }

	        if((index + 1) % 10 == division) {
	            // html += `통로`;
	            html += `<span></span>`;
	        }

	        if((index + 1) % 10 == 0) {
	            html += `<br>`;
	        }

	    });// end of seat_arr.forEach((item, index) => {} -------------------------
		

        // 툴팁의 기본 구조 설정
        let tooltip = $("<div class='tooltip'>");
        tooltip.append(html); // 툴팁에 "확인" 메시지 추가
		

        // 툴팁을 해당 태그 내부에 추가
        $(this).append(tooltip);


        // 해당 태그의 위치 계산
        let offset = $(this).offset();  // 좌석의 위치 계산
        let tooltipWidth = tooltip.outerWidth();  // 툴팁의 너비
        let tooltipHeight = tooltip.outerHeight();  // 툴팁의 높이

		
        // 툴팁 위치 설정: 잔여좌석 기준 우측으로 표시
        tooltip.css({
            //top: offset.top + "px", // 해당 태그의 상단 위치
            left: offset.left + $(this).outerWidth() + 18 + "px",  // 좌석의 우측에 10px 떨어진 위치
			top: offset.top - 5 + "px", // 10px 위로 이동
            position: "absolute",
            border: "1px solid #ccc",
            background: "#e1d3c050",
            padding: "10px",
            boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
            borderRadius: "5px",
            zIndex: 999999,  // 툴팁이 다른 요소 위에 표시되도록 높은 z-index 설정
            maxWidth: "500px",
            whiteSpace: "normal",
            display: "block !important", // 툴팁을 강제로 보이게 설정
            visibility: "visible !important", // 툴팁을 확실히 보이도록 설정
            opacity: 1 // 투명도 설정 (툴팁이 완전히 보이게)
        });

    }, function() {
        // 마우스를 벗어나면 툴팁 제거
        $(this).find(".tooltip").remove();
		
    });// end of $("td.seat_status").hover(function(e) {})-------------------------
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// === 아무도 예매하지 않은 상영일정이 등록된 영화 삭제 이벤트 시작 === //
	$("tbody").on("click", "tr", function(e) {
		var unused_seat = $(this).find("td.seat_status > span#seat_text").text().substring(0,2);
		var seat_cnt = $(this).find("td.seat_status > span#seat_text").text().substring(5);
		var movie_title = $(this).find("td#movie_title_text").text().substring(1);
		var showdate = $(this).find("td#show_date_text").text().substring(2); 
		var showtime = $(this).find("td#show_time_text").text();
		
		var seq_showtime_no = $(this).find("span#seq_showtime_no").text(); // ajax 로 넘길 seq 번호
		
		const user_confirm = confirm(`[영화] ${movie_title}\n[상영일자] ${showdate} ${showtime}\n\n위의 상영일정을 삭제하시겠습니까?`);
		
		if(user_confirm) {
			
			if(unused_seat == seat_cnt) {
				// ajax 호출
				$.ajax({
					url: 'showtimeDelete.mp',
					type: 'post',
					data: { "seq" : seq_showtime_no },
					async: false,
					success: function(json) {
						alert("상영일정이 삭제되었습니다.");
						window.location.href = 'showtimeList.mp';
					},
		            error: function() {
		                alert("상영일정 삭제에 실패했습니다. 다시 시도해주세요.");
		            }
				});// end of $.ajax({})------------------------------------
			}
			else {
				// 해당 영화를 예매한 회원이 한 명이라도 있을경우 삭제할 수 없다
				alert("해당 일정에 대한 예매 이력이 존재하여 삭제가 불가합니다.");
				return;
			}
			
		}
		else {
			alert("상영일정 삭제를 취소하셨습니다");
		}
		
	});// end of 	$("tbody").on("click", "tr", function(e) {})-------------------------
	// === 아무도 예매하지 않은 상영일정이 등록된 영화 삭제 이벤트 시작 === //		
	
	
	
});// end of $(document).ready(function() {})--------------------------













// === 영화 검색에 따른 상영시간이 등록된 영화 목록을 보여주는 이벤트 처리
function goSearch() {
	
	//const search_time = $("select[name='search_time']").val();
	//const search_movie_title = $("input:text[name='search_movie_title']").val().trim();

	const frm = document.showtime_search_frm
	// frm.action = "showtimeList.mp";
	// frm.method = "get";
	
	frm.submit();

}// end of function goSearch() {}-------------------------------------------



// === 상영예정작/상영종료작 버튼 클릭 이벤트 처리
function toggleShowtimeInvalidStatus() {
	
	var invalid_showtime =  $("span#invalid_showtime");
	
	if (invalid_showtime.text() == "상영예정") {
		$("input[name='invalid_showtime']").val("상영종료");
	} 
	else {
		$("input[name='invalid_showtime']").val("상영예정");
	}
	
	const frm = document.showtime_search_frm;
	// frm.action = "movieRegisteredList.mp";
	// frm.method = "get";
	
	frm.submit();
	
}// end of function toggleMovieInvalidStatus() {}---------------------------