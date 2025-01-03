let adult_ticket_price = 0;
let adolescent_ticket_price = 0;
let youth_ticket_price = 0;
let total_price = 0;
let adult_cnt = 0;
let adolescent_cnt = 0;
let youth_cnt = 0;

$(document).ready(function(){
	
	$("div#step2").hide();
	$("div#step3").hide();
	$("button#goMovieChoice").hide();
	$("button#goPointChoice").hide();
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


function goSeatChoice(userid) {
	if(userid == "") {
		alert("로그인이 필요한 서비스입니다.");
		return;
	}
	$("div#step1").hide();
	$("div#step2").show();
	$("button#goMovieChoice").show();
	$("button#goSeatChoice").hide();
	$("button#goPointChoice").show();
	
}

function goMovieChoice() {
	$("div#step1").show();
	$("div#step2").hide();
	$("button#goMovieChoice").hide();
	$("button#goSeatChoice").show();
	$("button#goPointChoice").hide();
}

function goPointChoice(ctxPath, userid) {
	$("div#step1").hide();
	$("div#step2").hide();
	$("div#step3").show();
	$("button#goPay").show();
	$("button#goMovieChoice").hide();
	$("button#goPointChoice").hide();
	
	let havingPoint = 0;
	
	if($("div#total_seat_cnt").text() == 0) {
		alert("관람인원은 0명일 수 없습니다.");
	}
	else if($("div#total_seat_cnt").text() != $("div#selected_seat_cnt").text()) {
		alert("관람인원과 선택 좌석 수가 동일하지 않습니다.");
	}
	else {
		$.ajax({
			url:ctxPath + "/reservation/getHavingPoint.mp",
			data: {
	             "userid": userid
	        },
			type:"post",
			dataType:"json",
	        success: function(json){
				console.log(json.havingPoint);
	        	if(json.length != 0) {
					console.log("포인트 가져오기 성공");
					$("label#having-point").text(json.havingPoint);
					havingPoint = json.havingPoint;
					console.log(havingPoint);
					console.log(typeof havingPoint);
					console.log(json.havingPoint);
					console.log(typeof json.havingPoint);
				}
				else {
					console.log("포인트 가져오기 실패")
				}
			},
			error: function(){
				alert("request error!");
			}
		}); // end of $.ajax({})---------------------------------------------------------------------
	}
	
	$("input#using-point").bind("change", function(e) {
		if($(e.target).val() > havingPoint) {
			alert("보유하신 point 만큼만 사용 가능합니다.");
			$(e.target).val(0);
		}
		else if($(e.target).val() < 0) {
			alert("음수 입력은 불가능합니다.");
			$(e.target).val(0);
		}
		else if($(e.target).val() % 10 != 0) {
			alert("포인트는 10 단위로 사용 가능합니다.");
			$(e.target).val( Math.floor($(e.target).val() / 10) * 10 );
		}
	});
	
	
}

function goPay(ctxPath, userid) {
	if($("div#total_seat_cnt").text() == 0) {
		alert("관람인원은 0명일 수 없습니다.");
	}
	else if($("div#total_seat_cnt").text() != $("div#selected_seat_cnt").text()) {
		alert("관람인원과 선택 좌석 수가 동일하지 않습니다.");
	}
	else {
		const ticketInfo = "영화 : " + $("div#movie-choice").text() + " " + $("div#total_seat_cnt").text() + "명";
		const using_point = $("input#using-point").val();
		
		const width = 1000;
		const height = 600;

	    const left = Math.ceil( (window.screen.width - width)/2 ); // 정수로 만듬
	    const top = Math.ceil( (window.screen.height - height)/2 ); // 정수로 만듬
		    
	    const url = `${ctxPath}/reservation/goPayTicket.mp?ticketInfo=${ticketInfo}&userid=${userid}&total_price=${total_price}&using_point=${using_point}`;      

	    window.open(url, "goPayTicket",
		               `left=${left}, top=${top}, width=${width}, height=${height}`);
		
	}
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
		let selected_seat_cnt = Number($("div#selected_seat_cnt").text());
		
		if(total_seat_cnt < 1) { // 관람인원을 선택하지 않았을 때
			alert("관람인원은 0명일 수 없습니다.");
		}
		else if(selected_seat_cnt == total_seat_cnt && !$(e.target).hasClass('selected')) { // 관람인원만큼 좌석을 선택했을 때
			alert("이미 좌석을 모두 선택하셨습니다.");
		}
		else { // 선택할 좌석이 남았을 때
			
			if($(e.target).hasClass('selected')) { // 이미 선택된 좌석을 골랐을 때
				seatArr.splice(seatArr.indexOf($(e.target).text()),1);
				$("div#seat-choice").text(seatArr.sort().join(","));
				$(e.target).removeClass('selected');
				
				selected_seat_cnt -= 1;
				$("div#selected_seat_cnt").text(selected_seat_cnt);
				
				let html = ``;
								
				if(selected_seat_cnt <= adult_cnt) {
					html += `<div>${adult_ticket_price} 원 X ${selected_seat_cnt} 명</div>`;
					total_price = adult_ticket_price * selected_seat_cnt;
				}
				else if(selected_seat_cnt <= adult_cnt + adolescent_cnt) {
					html += `<div>${adult_ticket_price} 원 X ${adult_cnt} 명</div>`;
					html += `<div>${adolescent_ticket_price} 원 X ${selected_seat_cnt - adult_cnt}</div>`;
					total_price = adult_ticket_price * adult_cnt + adolescent_ticket_price * (selected_seat_cnt - adult_cnt);
				}
				else {
					html += `<div>${adult_ticket_price} 원 X ${adult_cnt} 명</div>`;
					html += `<div>${adolescent_ticket_price} 원 X ${adolescent_cnt}</div>`;
					html += `<div>${youth_ticket_price} 원 X ${selected_seat_cnt - adult_cnt - adolescent_cnt}</div>`;
					total_price = adult_ticket_price * adult_cnt
								+ adolescent_ticket_price * adolescent_cnt 	
								+ youth_ticket_price * (selected_seat_cnt - adult_cnt - adolescent_cnt);
				}
				
				html += `<div>총금액 ${total_price}</div>`
				
				$("div#pay-choice").html(html);
			}
			
			else { // 빈 좌석을 골랐을 때
				seatArr.push($(e.target).text());
				$("div#seat-choice").text(seatArr.sort().join(","));
				$(e.target).addClass('selected');
				
				selected_seat_cnt += 1;
				$("div#selected_seat_cnt").text(selected_seat_cnt);
				
				let html = ``;
				
				if(selected_seat_cnt <= adult_cnt) {
					html += `<div>${adult_ticket_price} 원 X ${selected_seat_cnt} 명</div>`;
					total_price = adult_ticket_price * selected_seat_cnt;
				}
				else if(selected_seat_cnt <= adult_cnt + adolescent_cnt) {
					html += `<div>${adult_ticket_price} 원 X ${adult_cnt} 명</div>`;
					html += `<div>${adolescent_ticket_price} 원 X ${selected_seat_cnt - adult_cnt}</div>`;
					total_price = adult_ticket_price * adult_cnt + adolescent_ticket_price * (selected_seat_cnt - adult_cnt);
				}
				else {
					html += `<div>${adult_ticket_price} 원 X ${adult_cnt} 명</div>`;
					html += `<div>${adolescent_ticket_price} 원 X ${adolescent_cnt}</div>`;
					html += `<div>${youth_ticket_price} 원 X ${selected_seat_cnt - adult_cnt - adolescent_cnt}</div>`;
					total_price = adult_ticket_price * adult_cnt
								+ adolescent_ticket_price * adolescent_cnt 	
								+ youth_ticket_price * (selected_seat_cnt - adult_cnt - adolescent_cnt);
				}
				
				html += `<div>총금액 ${total_price}</div>`
				
				$("div#pay-choice").html(html);
				
			}
		}
	});
	
}

// 결제 내역 만들기
function makePayment(ctxPath, userid, ticketPrice, imp_uid) {
	
	const seq_showtime_no = $("div#seq_showtime_no").text();
	
	$.ajax({
		url:ctxPath + "/reservation/makePayment.mp",
		data: {
             "userid": userid,
             "ticketPrice": ticketPrice,
             "imp_uid": imp_uid,
             "seq_showtime_no": seq_showtime_no
        },
		type:"post",
		dataType:"json",
        success: function(json){
        	if(json.n == 1) {
				console.log("결제 내역 만들기 성공")
			}
			else {
				console.log("결제 내역 만들기 실패")
			}
		},
		error: function(){
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
}

// 티켓 만들기
function makeTicket(ctxPath, imp_uid) {
	
	const seat_arr = $("div#seat-choice").text();
	
	$.ajax({
		url:ctxPath + "/reservation/makeTicket.mp",
		data: {
             "imp_uid":imp_uid,
			 "adult_cnt":adult_cnt,
			 "adolescent_cnt":adolescent_cnt,
			 "youth_cnt":youth_cnt,
			 "seat_arr":seat_arr
        },
		type:"post",
		dataType:"json",
        success: function(json){
			if(json.n == 1) {
				console.log("티켓 만들기 성공")
			}
			else {
				console.log("티켓 만들기 실패")
			}
		},
		error: function(){
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
}

// 상영 영화 좌석배열 수정 및 남은 좌석 수정
function updateShowtime(ctxPath) {
	
	const seat_arr = $("div#seat-choice").text();
	const seq_showtime_no = $("div#seq_showtime_no").text();
		
	$.ajax({
		url:ctxPath + "/reservation/updateShowtime.mp",
		data: {
			 "seat_arr":seat_arr,
			 "seq_showtime_no": seq_showtime_no
        },
		type:"post",
		dataType:"json",
        success: function(json){
			if(json.n == 1) {
				console.log("상영 영화 수정 성공")
			}
			else {
				console.log("상영 영화 수정 실패")
			}
		},
		error: function(){
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
}

// 포인트 내역 만들기
function makePoint(ctxPath, userid, using_point, ticketPrice, imp_uid ) {
	
	$.ajax({
		url:ctxPath + "/reservation/makePoint.mp",
		data: {
             "userid": userid,
             "using_point": using_point,
             "ticketPrice": ticketPrice,
             "imp_uid": imp_uid
        },
		type:"post",
		dataType:"json",
        success: function(json){
        	if(json.n == 1) {
				console.log("포인트 내역 만들기 성공")
			}
			else {
				console.log("포인트 내역 만들기 실패")
			}
		},
		error: function(){
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
	
}
