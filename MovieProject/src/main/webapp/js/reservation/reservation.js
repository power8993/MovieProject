let adult_ticket_price = 0;
let adolescent_ticket_price = 0;
let youth_ticket_price = 0;
let total_price = 0;
let adult_cnt = 0;
let adolescent_cnt = 0;
let youth_cnt = 0;
let seq_movie_no = "";
let input_date = "";
let start_time = "";
let seat_str = "";
let fk_screen_no = "";
let movie_grade = "";

$(document).ready(function(){
	
	// 영화, 날짜 선택 외의 다른 div와 button 들 안보이게 하기
	$("div#step2").hide();
	$("div#step3").hide();
	$("div#goMovieChoice").hide();
	$("div#goPay").hide();
	$("div.loader").hide(); // CSS 로딩화면 보여주기
	$("div#movie-choice-poster").hide();
	
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
		$("tr.movie-list").removeClass("selected");
		$("div#movie-choice-poster").show();
		if($(e.target).hasClass('movie-title') || $(e.target).hasClass('movie-grade')) {
			$(e.target).parent().addClass("selected");
			seq_movie_no = $(e.target).parent().find("td#seq_movie_no").text();
			let v_html = '<img src="http://localhost:9090/MovieProject/images/admin/poster_file/미니언즈.jpg" style="width:auto; height:110px;">';
			$("div#movie-choice-poster").html(v_html);
			$("div#movie-choice").html($(e.target).parent().find("td.movie-title").html());
		}
		else if($(e.target).hasClass('movie_grade_img')) {
			$(e.target).parent().parent().addClass("selected");
			seq_movie_no = $(e.target).parent().parent().find("td#seq_movie_no").text();
			let v_html = '<img src="http://localhost:9090/MovieProject/images/admin/poster_file/미니언즈.jpg" style="width:auto; height:110px;">';
			$("div#movie-choice-poster").html(v_html);
			$("div#movie-choice").html($(e.target).parent().find("td.movie-title").html());
			// $("div#movie-choice").html($(e.target).parent().parent().find("td.movie-title").html());
		}
		
		movie_grade = $(e.target).parent().find("span").text();
		
		if($("div#date-choice").text() == "시간선택") {
			getScreenDate(seq_movie_no);
			return;
		}
		
		// 영화와 날짜를 선택했을 때 상영 시간이 보여주기
		getScreenTime(seq_movie_no, input_date, start_time, fk_screen_no);
		
	});
	
	// 예약페이지에서 날짜를 선택했을 때
	$("li.day").find("span").click(e => {
		$("li.day").removeClass("selected");
        $(e.target).parent().addClass("selected");
        $("div#date-choice").html($(e.target).parent().find("span.input_date").text());
        $("div#time-choice").empty();
        
        input_date = $(e.target).parent().find("span.input_date").text();
        
        $("div#screen-date-info").text("상영날짜 : " + input_date);
        
        if($("div#movie-choice").text() == "영화선택") {
        	return;
        }
        
		// 영화와 날짜를 선택했을 때 상영 시간이 보여주기
		getScreenTime(seq_movie_no, input_date, start_time, fk_screen_no);
		
        
	}); // end of $("li#day").find("span").click(e => {})-------------------------------------------
	
	// 인원 선택
	$("div#adult").find(":nth-child(2)").addClass('selected');
	$("div#adolescent").find(":nth-child(2)").addClass('selected');
	$("div#youth").find(":nth-child(2)").addClass('selected');
	
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
		$("div#adult").find("button").removeClass('selected');
		$(e.target).addClass('selected');
		$("div#total_seat_cnt").text(total_cnt);
		if(total_cnt == 0) {
			$("div#seat-screen").addClass('mouse_block');
		}
		else {
			$("div#seat-screen").removeClass('mouse_block');
		}
		
		showTotalPrice();
		
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
		$("div#adolescent").find("button").removeClass('selected');
		$(e.target).addClass('selected');
		$("div#total_seat_cnt").text(total_cnt);
		if(total_cnt == 0) {
			$("div#seat-screen").addClass('mouse_block');
		}
		else {
			$("div#seat-screen").removeClass('mouse_block');
		}
		
		showTotalPrice();
		
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
		$("div#youth").find("button").removeClass('selected');
		$(e.target).addClass('selected');
		$("div#total_seat_cnt").text(total_cnt);
		if(total_cnt == 0) {
			$("div#seat-screen").addClass('mouse_block');
		}
		else {
			$("div#seat-screen").removeClass('mouse_block');
		}
		
		showTotalPrice();
		
		
	});
	

}); // end of $(document).ready(function() {});;--------------------------------------------


// 영화와 날짜를 선택했을 때 상영 시간 보여주기
function getScreenTime(seq_movie_no1, input_date1, start_time1, fk_screen_no1) {
	
	seq_movie_no = seq_movie_no1;
	input_date = input_date1;
	
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
        		$(".time-table").html(v_html);
			}
			else if(json.length > 0) {
			   
				v_html = "";
				
				let screen_no = 0;
				
				$.each(json, function(index, item){
					
					if(screen_no != item.fk_screen_no) {
						screen_no = item.fk_screen_no;
						v_html += `<tr class='screen_no'><td class='screen_no_data'>${screen_no}관</td><td>(총 40석)</td></tr>`;
					}
					if(start_time1 == (item.start_time).substr(0,2) + ':' + (item.start_time).substr(2,2) && fk_screen_no1 == item.fk_screen_no) {
						v_html += `<tr class='time-choice'><td class='time_data selected' 
											onclick='onScreenClick(this, ${item.start_time},${item.seq_showtime_no},${item.fk_screen_no},"${item.seat_arr}")'>
											${(item.start_time).substr(0,2)}:${(item.start_time).substr(2,2)}</td><td class='unused_seat'>${item.unused_seat}석</td></tr>`;
								
						$("div#time-choice").html(start_time1);
					    $("div#screen-time-info").text("상영시간 : " + start_time1);
						$("div#seq_showtime_no").html(item.seq_showtime_no);
						
						seat_str = item.seat_arr;
						fk_screen_no = item.fk_screen_no;
					}
					else {
						v_html += `<tr class='time-choice'><td class='time_data' 
									onclick='onScreenClick(this, ${item.start_time},${item.seq_showtime_no},${item.fk_screen_no},"${item.seat_arr}")'>
									${(item.start_time).substr(0,2)}:${(item.start_time).substr(2,2)}</td><td class='unused_seat'>${item.unused_seat}석</td></tr>`;
					}
					
						
				}); // end of $.each(json, function(index, item)--------------------------------------------------
				
						
				$(".time-table").html(v_html);
			}
		},
		error: function(){
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------

}


// 영화를 선택했을 때 상영 날짜 가져오기
function getScreenDate(seq_movie_no) {
	
}


// 좌석선택 버튼을 눌렀을 경우
function goSeatChoice(userid, birthday) {
	if($("div#movie-choice").text() == "영화선택" || $("div#date-choice").text() == "시간선택" || $("div#time-choice").text() == "") {
		alert("영화와 날짜와 시간을 모두 선택해주세요.");
		return;
	}
	else if(userid == "") {
		alert("로그인이 필요한 서비스입니다.");
		location.href = "/MovieProject/login/login.mp";
		return;
	}
	else if(movie_grade != "전체") {
		let today = new Date();   
		let year = today.getFullYear();
		let age = year - Number(birthday.substr(0, 4));
		let movie_age = Number(movie_grade.substr(0, 2));
		
		if(age < movie_age) {
			alert("상영 등급이 맞지 않아 예매가 불가능 합니다.");
			return;
		}
	}
	
	makeSeatArray();
	
	getHavingPoint(userid);

	$("div#step1").hide();
	$("div#step2").show();
	$("div#goMovieChoice").show();
	$("div#goSeatChoice").hide();
	$("div#goPay").show();
	
	let seatArr = []; // 선택한 좌석 배열 초기화
		
	// 좌석 선택
	$("button.seat").click(e => {
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
				
				showTotalPrice();
			}
			
			else { // 빈 좌석을 골랐을 때
				seatArr.push($(e.target).text());
				$("div#seat-choice").text(seatArr.sort().join(","));
				$(e.target).addClass('selected');
				
				selected_seat_cnt += 1;
				$("div#selected_seat_cnt").text(selected_seat_cnt);
				
				showTotalPrice();
				
			}
		}
	});
		
} // end of function goSeatChoice(userid)----------------------


function showTotalPrice() {
	
	let selected_seat_cnt = Number($("div#selected_seat_cnt").text());
	
	let html = ``;
	if(selected_seat_cnt == 0) {
		$("div#pay-choice").html(html);
		return;
	}
	else if(selected_seat_cnt <= adult_cnt) {
		html += `<div>${adult_ticket_price} 원 X ${selected_seat_cnt} 명</div>`;
		total_price = adult_ticket_price * selected_seat_cnt;
	}
	else if(selected_seat_cnt <= adult_cnt + adolescent_cnt) {
		if(adult_cnt != 0) {
			html += `<div>${adult_ticket_price} 원 X ${adult_cnt} 명</div>`;
		}
		html += `<div>${adolescent_ticket_price} 원 X ${selected_seat_cnt - adult_cnt} 명</div>`;
		total_price = adult_ticket_price * adult_cnt + adolescent_ticket_price * (selected_seat_cnt - adult_cnt);
	}
	else {
		if(adult_cnt != 0) {
			html += `<div>${adult_ticket_price} 원 X ${adult_cnt} 명</div>`;
		}
		if(adolescent_cnt != 0) {
			html += `<div>${adolescent_ticket_price} 원 X ${adolescent_cnt} 명</div>`;
		}
		html += `<div>${youth_ticket_price} 원 X ${selected_seat_cnt - adult_cnt - adolescent_cnt} 명</div>`;
		total_price = adult_ticket_price * adult_cnt
					+ adolescent_ticket_price * adolescent_cnt 	
					+ youth_ticket_price * (selected_seat_cnt - adult_cnt - adolescent_cnt);
	}
	
	html += `<div>총금액 ${total_price.toLocaleString()}원</div>`
	$("div#totalPrice").text(total_price);
	$("div#pay-choice").html(html);
	
}

// 영화선택 버튼을 눌렀을 경우 (좌석선택에서 뒤로 돌아가는 경우)
function goMovieChoice() {
	$("div#step1").show();
	$("div#step2").hide();
	$("div#goMovieChoice").hide();
	$("div#goSeatChoice").show();
	$("div#goPay").hide();
	
	$("div#adult").find("button").removeClass('selected');
	$("div#adolescent").find("button").removeClass('selected');
	$("div#youth").find("button").removeClass('selected');
	
	$("div#adult").find(":nth-child(2)").addClass('selected');
	$("div#adolescent").find(":nth-child(2)").addClass('selected');
	$("div#youth").find(":nth-child(2)").addClass('selected');
	
	adult_cnt = 0;
	adolescent_cnt = 0;
	youth_cnt = 0;
	
	$("div#pay-choice").html("예약정보");
	$("div#seat-choice").html("좌석선택");
	
	$("div#total_seat_cnt").text("0");
	$("div#selected_seat_cnt").text("0");
	
} // end of function goMovieChoice()-----------------------------------------------


// 포인트 사용 버튼을 눌렀을 경우
function getHavingPoint(userid) {
	
	let havingPoint = 0;
		
	$.ajax({
		url:"/MovieProject/reservation/getHavingPoint.mp",
		data: {
             "userid": userid
        },
		type:"post",
		dataType:"json",
        success: function(json){
        	if(json.length != 0) {
				console.log("포인트 가져오기 성공");
				$("label#having-point").text(json.havingPoint);
				havingPoint = json.havingPoint;
			}
			else {
				console.log("포인트 가져오기 실패")
			}
		},
		error: function(){
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
	
	$("input#using-point").bind("change", function(e) {
		
		if($(e.target).val() < 0) {
			alert("음수 입력은 불가능합니다.");
			$(e.target).val(0);
		}
		else if($(e.target).val() % 10 != 0) {
			alert("포인트는 10 단위로 사용 가능합니다.");
			$(e.target).val( Math.floor($(e.target).val() / 10) * 10 );
		}
		else if($(e.target).val() > havingPoint) {
			alert("보유하신 point 만큼만 사용 가능합니다.");
			$(e.target).val(havingPoint);
		}
		else if($(e.target).val() > total_price) {
			alert("결제 금액보다 낮은 포인트만 사용 가능합니다.");
			$(e.target).val(total_price);
		}
	});
	
	
}

// 결제하기 버튼을 눌렀을 때
function goPay(ctxPath, userid) {
	if($("div#total_seat_cnt").text() == 0) {
		alert("관람인원은 0명일 수 없습니다.");
	}
	else if($("div#total_seat_cnt").text() != $("div#selected_seat_cnt").text()) {
		alert("관람인원과 선택 좌석 수가 동일하지 않습니다.");
	}
	else {
		
		if(confirm("결제하시겠습니까?")) {
			
			$("div.loader").show(); // CSS 로딩화면 보여주기
			
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
		else {
			$("div.loader").hide(); // CSS 로딩화면 숨기기
			alert("결제를 취소하였습니다.");
		}
		
		
	}
}

// 좌석 배열 만들기
function makeSeatArray() {
	
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
	
	html = `<br><div style='background:gray; color: white; font-weight:bold; font-size:14pt; width:60%; height:30px; margin: 0 auto;'>screen</div><br>`;
	
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

}

// 상영시간을 눌렀을 때
function onScreenClick(element, start_time, seq_showtime_no, fk_screen_no1, seat_str1) {
	
	seat_str = seat_str1;
	fk_screen_no = fk_screen_no1;
	
	$("td.time_data").removeClass("selected");
	$(element).addClass("selected");
	
	$("div#time-choice").html(String(start_time).substr(0,2) + ":" + String(start_time).substr(2,2));
	
    $("div#screen-time-info").text("상영시간 : " + String(start_time).substr(0,2) + ":" + String(start_time).substr(2,2));
	$("div#seq_showtime_no").html(seq_showtime_no);
	
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
		async:false,
        success: function(json){
        	if(json.n == 1) {
				console.log("결제 내역 만들기 성공");
			}
			else {
				console.log("결제 내역 만들기 실패");
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
			if(json.sum == 1) {
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


// 예약 확인 문자
function sendReservationSMS(ctxPath, name, ticketInfo, ticketPrice, mobile) {
	
	$.ajax({
		url:ctxPath + "/reservation/sendReservationSMS.mp",
		data: {
             "name": name,
             "ticketInfo": ticketInfo,
             "ticketPrice": ticketPrice,
             "mobile": mobile
        },
		type:"post",
		dataType:"json",
        success: function(json){
			console.log("문자 보내기 성공");
		},
		error: function() {
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
}

function sendReservationMail(ctxPath, userid, name, imp_uid) {
	$.ajax({
		url:ctxPath + "/reservation/sendReservationMail.mp",
		data: {
			"userid":userid,
			"imp_uid":imp_uid,
			"name":name
        },
		type:"post",
		dataType:"json",
        success: function(json){
			console.log("메일 보내기 성공");
		},
		error: function() {
			alert("request error!");
		}
	}); // end of $.ajax({})---------------------------------------------------------------------
}

function reservationEnd(ctxPath, userid, imp_uid, name) {
	
	const frm = document.payment;
	frm.imp_uid.value = imp_uid;
	frm.userid.value = userid;
	
	frm.action = ctxPath+"/reservation/reservationEnd.mp";
	frm.method = "POST";
	frm.submit();
}

function stopCSSLoader() {
	$("div.loader").hide();
}