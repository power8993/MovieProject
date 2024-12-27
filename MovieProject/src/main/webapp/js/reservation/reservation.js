let adult_ticket_price = 0;
let adolescent_ticket_price = 0;
let youth_ticket_price = 0;

$(document).ready(function(){
	
	$("div#step2").hide();
	$("button#goMovieChoice").hide();
	$("button#goPay").hide();
	
	let seq_movie_no = "";
	let input_date = "";
	
	// 영화 티켓 가격 가져오기
	$.ajax({
		url:"/MovieProject/reservation/getTicketPrice.mp",
		dataType:"json",
        success: function(json){
        	if(json.length == 0) {
				console.log("티켓 가격 정보가 없습니다");
			}
			else {
				adult_ticket_price = json.adult_ticket_price;
				adolescent_ticket_price = json.adolescent_ticket_price;
				youth_ticket_price = json.youth_ticket_price;
			}
		},
		error: function(){
			alert("티켓 가격 정보를 가져오는 중 오류 발생!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
	
	console.log(adult_ticket_price);
	console.log(typeof adult_ticket_price);
	
	// 예약페이지에서 영화를 선택했을 때
	$("tr.movie-list").click(e => {
		$("tr.movie-list").css({'background-color':'','color':''});
		$(e.target).parent().css({'background-color':'black','color':'white'});
		
		$("div#movie-choice").html($(e.target).parent().find("td.movie-title").html());
		
		seq_movie_no = $(e.target).parent().find("td#seq_movie_no").text();
		
		if($("div#time-choice").text() == "시간선택") {
			return;
		}
		
		$.ajax({
			url:"/MovieProject/reservation/getScreenTime.mp",
			dataType:"json",
			data: {
	             "input_date": input_date,
	             "seq_movie_no": seq_movie_no
	        },
	        success: function(json){
	        	if(json.length == 0) {
	        		v_html = `선택하신 날짜에 상영중인 영화가 없습니다.`;
	        		$("div.time").find("div.col-body").html(v_html);
				}
				else if(json.length > 0) {
				   
					v_html = "<table><tbody>"
					
					$.each(json, function(index, item){
						v_html += `<tr class='time_choice'><td class='time_data' 
									onclick='onScreenClick(this, ${item.start_time},${item.seq_showtime_no},${item.fk_screen_no},"${item.seat_arr}")'>
									${(item.start_time).substr(0,2)}:${(item.start_time).substr(2,2)}</td><td>${item.unused_seat}석</td></tr>`;
						
					}); // end of $.each(json, function(index, item)--------------------------------------------------
					
					v_html += `</table></tbody>`;
							
					$("div.time").find("div.col-body").html(v_html);
				}
			},
			error: function(){
				alert("request error!");
			}
		}); // end of $.ajax({})---------------------------------------------------------------------
		
	});
	
	// 예약페이지에서 날짜를 선택했을 때
	$("li#day").find("span").click(e => {
		$("li#day").css({'background-color':'','color':''});
        $(e.target).parent().css({'background-color':'black','color':'white'});
        $("div#date-choice").html($(e.target).parent().find("span#input_date").text());
        $("div#time-choice").empty();
        
        input_date = $(e.target).parent().find("span#input_date").text();
        
        $("div#screen-date-info").html(input_date);
        
        if($("div#movie-choice").text() == "영화선택") {
        	return;
        }
        
        
        $.ajax({
			url:"/MovieProject/reservation/getScreenTime.mp",
			dataType:"json",
			data: {
	             "input_date": input_date,
	             "seq_movie_no": seq_movie_no
	        },
	        success: function(json){
	        	if(json.length == 0) {
	        		v_html = `선택하신 날짜에 상영중인 영화가 없습니다.`;
	        		$("div.time").find("div.col-body").html(v_html);
				}
				else if(json.length > 0) {
				   
					v_html = "<table><tbody>"
					
					$.each(json, function(index, item){
						v_html += `<tr class='time_choice'><td class='time_data' 
									onclick='onScreenClick(this, ${item.start_time},${item.seq_showtime_no},${item.fk_screen_no},"${item.seat_arr}")'>
									${(item.start_time).substr(0,2)}:${(item.start_time).substr(2,2)}</td><td>${item.unused_seat}석</td></tr>`;
						
					}); // end of $.each(json, function(index, item)--------------------------------------------------
					
					v_html += `</table></tbody>`;
							
					$("div.time").find("div.col-body").html(v_html);
				}
			},
			error: function(){
				alert("request error!");
			}
		}); // end of $.ajax({})---------------------------------------------------------------------
		
        
	}); // end of $("li#day").find("span").click(e => {})-------------------------------------------
	
	// 인원 선택
	$("div#adult").find(":nth-child(2)").css({'background-color':'black','color':'white'});
	$("div#adolescent").find(":nth-child(2)").css({'background-color':'black','color':'white'});
	$("div#youth").find(":nth-child(2)").css({'background-color':'black','color':'white'});
	
	let adult_cnt = 0;
	let adolescent_cnt = 0;
	let youth_cnt = 0;
	let total_cnt = 0;
	
	$("div#adult").find("button").click(e => {
		total_cnt = Number($(e.target).val()) + adolescent_cnt + youth_cnt;
		if(total_cnt > 5) {
			alert("예매는 5명까지 가능합니다.");
			return false;
		}
		else if(total_cnt < Number($("div#selected_seat_cnt").text())) {
			alert("선택한 좌석이 예매 인원 보다 많습니다.");
			return false;
		}
		adult_cnt = Number($(e.target).val());
		$("div#adult").find("button").css({'background-color':'','color':''})
		$(e.target).css({'background-color':'black','color':'white'});
		$("div#total_seat_cnt").text(total_cnt);
		if(total_cnt == 0) {
			$("div#seat-screen").addClass('mouse_block');
		}
		else {
			$("div#seat-screen").removeClass('mouse_block');
		}
	});
	
	$("div#adolescent").find("button").click(e => {
		total_cnt = adult_cnt + Number($(e.target).val()) + youth_cnt;
		if(total_cnt > 5) {
			alert("예매는 5명까지 가능합니다.");
			return false;
		}
		else if(total_cnt < Number($("div#selected_seat_cnt").text())) {
			alert("선택한 좌석이 예매 인원 보다 많습니다.");
			return false;
		}
		adolescent_cnt = Number($(e.target).val());
		$("div#adolescent").find("button").css({'background-color':'','color':''})
		$(e.target).css({'background-color':'black','color':'white'});
		$("div#total_seat_cnt").text(total_cnt);
		if(total_cnt == 0) {
			$("div#seat-screen").addClass('mouse_block');
		}
		else {
			$("div#seat-screen").removeClass('mouse_block');
		}
	});
	
	$("div#youth").find("button").click(e => {
		total_cnt = adult_cnt + adolescent_cnt + Number($(e.target).val());
		if(total_cnt > 5) {
			alert("예매는 5명까지 가능합니다.");
			return false;
		}
		else if(total_cnt < Number($("div#selected_seat_cnt").text())) {
			alert("선택한 좌석이 예매 인원 보다 많습니다.");
			return false;
		}
		youth_cnt = Number($(e.target).val());
		$("div#youth").find("button").css({'background-color':'','color':''})
		$(e.target).css({'background-color':'black','color':'white'});
		$("div#total_seat_cnt").text(total_cnt);
		if(total_cnt == 0) {
			$("div#seat-screen").addClass('mouse_block');
		}
		else {
			$("div#seat-screen").removeClass('mouse_block');
		}
	});
	
	
	
	

}); // end of $(document).ready(function() {});;--------------------------------------------


function goSeatChoice() {
	$("div#step1").hide();
	$("div#step2").show();
	$("button#goMovieChoice").show();
	$("button#goSeatChoice").hide();
	$("button#goPay").show();
}

function goMovieChoice() {
	$("div#step1").show();
	$("div#step2").hide();
	$("button#goMovieChoice").hide();
	$("button#goSeatChoice").show();
	$("button#goPay").hide();
}

function goPayChoice() {
	if($("div#total_seat_cnt").text() != $("div#selected_seat_cnt").text()) {
		alert("관람인원과 선택 좌석 수가 동일하지 않습니다.");
		return false;
	}
	console.log("결제 하기");
}

function onScreenClick(element, start_time, seq_showtime_no, fk_screen_no, seat_str) {

	$("tr.time_choice").css({'background-color':'','color':''})
	$(element).parent().css({'background-color':'black','color':'white'});
	
	$("div#time-choice").html(String(start_time).substr(0,2) + ":" + String(start_time).substr(2,2));
	
    $("div#screen-time-info").html(String(start_time).substr(0,2) + ":" + String(start_time).substr(2,2));
	$("div#seq_showtime_no").html(seq_showtime_no);
	
	let seat_arr = seat_str.split(",");
	console.log(seat_arr);
	
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
	
	seat_arr.forEach((item, index) => {
		
		
        if(item == 0){
            html += `<button type='button' class='seat'>${charArr[Math.floor(index/10)] + (index%10 + 1)}</button>`;
        }
        else {
            html += `<button type='button' class='seat mouse_block' style='background-color:gray; color:white;'>${charArr[Math.floor(index/10)] + (index%10 + 1)}</button>`;
        }

        if((index + 1) % 10 == division) {
            // html += `통로`;
            html += `<span></span>`;
        }

        if((index + 1) % 10 == 0) {
            html += `<br>`;
        }

    });// end of seat_arr.forEach((item, index) => {} -------------------------
	
	$("div#seat-screen").html(html);
	$("div#seat-screen").addClass('mouse_block');
	
	let seatArr = [];
	
	// 좌석 선택
	$("button.seat").click(e => {
		console.log($(e.target).text());
		const total_seat_cnt = Number($("div#total_seat_cnt").text());
		const selected_seat_cnt = Number($("div#selected_seat_cnt").text());
		if(total_seat_cnt < 1) {
			alert("관람인원은 0명일 수 없습니다.");
		}
		else if(selected_seat_cnt == total_seat_cnt && !$(e.target).hasClass('selected')) {
			alert("이미 좌석을 모두 선택하셨습니다.");
		}
		else {
			if($(e.target).hasClass('selected')) {
				seatArr.splice(seatArr.indexOf($(e.target).text()),1);
				$("div#seat-choice").text(seatArr.sort().join(","));
				$(e.target).removeClass('selected');
				$("div#selected_seat_cnt").text(selected_seat_cnt-1);
			}
			else {
				seatArr.push($(e.target).text());
				$("div#seat-choice").text(seatArr.sort().join(","));
				$(e.target).addClass('selected');
				$("div#selected_seat_cnt").text(selected_seat_cnt+1);
			}
		}
	});
	
}

