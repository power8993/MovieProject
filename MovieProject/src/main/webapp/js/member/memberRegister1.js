
let b_idcheck_click = false;
// "아이디중복확인" 을 클릭했는지 클릭 하지 않았는지 여부를 알아오기 위한 용도
let b_emailcheck_click = false;
// "아이디중복확인" 을 클릭했는지 클릭 하지 않았는지 여부를 알아오기 위한 용도

$(document).ready(function(){
	
	$("#authPassElmt").hide();
	
	/* --------------- 생년월일 선택하기 시작 ---------------- */
	// '출생 연도', '출생 월', '출생 일' 엘리먼트 가져오기
	const birthYearEl = document.querySelector('#birth-year');
	const birthMonthEl = document.querySelector('#birth-month');
	const birthDayEl = document.querySelector('#birth-day');

	// option 목록 생성 여부 확인 변수
	let isYearOptionExisted = false;
	let isMonthOptionExisted = false;

	// 연도 선택 옵션 동적 생성
	birthYearEl.addEventListener('focus', function () {
	  if (!isYearOptionExisted) {
	    isYearOptionExisted = true;
	    for (let i = 1940; i <= 2025; i++) {
	      const yearOption = document.createElement('option');
	      yearOption.setAttribute('value', i);
	      yearOption.innerText = i;
	      this.appendChild(yearOption);
	    }
	  }
	});

	// 월 선택 옵션 동적 생성
	birthMonthEl.addEventListener('focus', function () {
	  if (!isMonthOptionExisted) {
	    isMonthOptionExisted = true;
	    for (let i = 1; i <= 12; i++) {
	      const monthOption = document.createElement('option');
	      monthOption.setAttribute('value', i);
	      monthOption.innerText = i;
	      this.appendChild(monthOption);
	    }
	  }
	});

	// 일 선택 옵션 동적 생성 및 업데이트
	function updateDays() {
	  // 선택된 연도와 월 가져오기
	  const selectedYear = parseInt(birthYearEl.value);
	  const selectedMonth = parseInt(birthMonthEl.value);

	  // 일 선택지 초기화
	  birthDayEl.innerHTML = '<option disabled selected>일</option>';

	  // 선택된 연도와 월에 따라 일 수 계산
	  if (selectedYear && selectedMonth) {
	    const daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate(); // 마지막 날 계산
	    for (let i = 1; i <= daysInMonth; i++) {
	      const dayOption = document.createElement('option');
	      dayOption.setAttribute('value', i);
	      dayOption.innerText = i;
	      birthDayEl.appendChild(dayOption);
	    }
	  }
	}

	// 연도 또는 월이 변경될 때 일 업데이트
	birthYearEl.addEventListener('change', updateDays);
	birthMonthEl.addEventListener('change', updateDays);

	
	
	/* --------------- 생년월일 선택하기 끝  ---------------- */	
	
	
	
	$("span.error").hide();
	$("input#name").focus();
	
	
	$("input#name").on("input", function (e) {
	    const name = $(e.target).val().trim();

	    // 정규식: 한글 또는 알파벳만 허용 (숫자와 특수문자 금지)
	    const regExp_Name = /^[가-힣\s]{2,10}$/;

	    if (name === "" || !regExp_Name.test(name)) { // 이름이 공백이거나 정규식을 통과하지 못한 경우
	        $(e.target).next().text("이름은 한글만 입력 가능하며 2~10자여야 합니다.").show();
	    } else { // 이름이 유효한 경우
	        $(e.target).next().hide();
	    }
	});


	
	$("input#userid").on("input", function (e) {
	    
	    const regExp_Userid = /^[a-z][a-z0-9]{3,14}$/; // 정규식: 소문자, 숫자만 허용, 길이는 4~15자

	    const bool = regExp_Userid.test($(e.target).val());

	    if (!bool) { // 아이디가 정규표현식에 위배된 경우
	        $("#idcheckResult").text(""); // 중복확인이 성공한 경우의 문구가 있을 수 있으니 사전에 제거
	        $(e.target).parent().next().show();
	    } else {
	        $(e.target).parent().next().hide();
	    }
	});
		
		
	$("input#pwd").on("input", function (e) {
				
		const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
		
		const bool = regExp_pwd.test($(e.target).val());
		
		if(!bool) { // 암호가 정규표현식에 위배된 경우
			
			$(e.target).next().show();
		}
		else { // 암호가 정규표현식에 부합하는 경우
			$(e.target).next().hide();
		}
		
	}); 
	
	
	$("input#pwdcheck").on("input", function (e) {
					
		if( $("input#pwd").val() != $(e.target).val() ) { // 암호와 암호확인값이 일치하지 않는 경우
			
			$(e.target).next().show();
		}
		else { // 암호와 암호확인값이 일치하는 경우
			$(e.target).next().hide();
		}
		
	}); 
	
	
	$("input#email").on("input", function (e) {
		const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
		// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
		
		const bool = regExp_email.test($(e.target).val());
		
		if(!bool) { // 이메일이 정규표현식에 위배된 경우
			
			
			$("#emailCheckResult").text(""); // 중복확인이 성공한 경우의 문구가 있을 수 있으니 사전에 제거
			$(e.target).parent().next().show();
		}
		else { // 이메일이 정규표현식에 부합한 경우
			$(e.target).parent().next().hide();
		}
		
	}); 
	
	
	$("input#hp2").on("input", function (e) {
		
		$("#authMsg").hide(); // 인증번호 전송 버튼 클릭 시 유효성 검증 메세지 출력되어있는 것 숨기기.
	    const regExp_hp2 = /^[1-9][0-9]{3}$/; // 첫 번째 숫자는 1-9, 나머지는 0-9로 이루어진 4자리 숫자

	    const hp2Value = $(e.target).val(); // hp2 입력값 가져오기
	    const bool = regExp_hp2.test(hp2Value); // 정규표현식 검사

	    if (!bool) { // hp2 값이 정규표현식에 맞지 않는 경우
	        $("#hp_error").text("전화번호 중간 자리를 정확하게 입력해 주세요.").show();
	        return;
	    }

	    // hp3 값이 비어 있는지 확인
	    const hp3Value = $("input#hp3").val(); // hp3 입력값 가져오기
	    if (hp3Value === "") {
	        $("#hp_error").text("전화번호 마지막 자리를 입력해주세요.").show();
			$("input#hp3").focus();
	        return;
	    }

	    // hp3 값의 길이가 4자리인지 확인
	    if (hp3Value.length !== 4) {
	        $("#hp_error").text("전화번호 마지막 자리는 정확히 4자리여야 합니다.").show();
			$("input#hp3").focus();
	        return;
	    }

	    // hp3 값이 숫자 4자리인지 확인
	    const regExp_hp3 = /^[1-9][0-9]{3}$/; // 첫 번째 숫자는 1-9, 나머지는 0-9로 이루어진 4자리 숫자
	    if (!regExp_hp3.test(hp3Value)) {
	        $("#hp_error").text("전화번호 마지막 자리는 4자리 숫자여야 합니다.").show();
	        return;
	    }

	    // 모든 조건을 만족한 경우
	    $("#hp_error").hide();
	});

	
	$("input#hp3").on("input", function (e) {
		$("#authMsg").hide();
	    const regExp_hp3 = /^[1-9][0-9]{3}$/; // 첫 번째 숫자는 1-9, 나머지는 0-9로 이루어진 4자리 숫자
	    
	    const hp3Value = $(e.target).val(); // 입력된 값 가져오기
	    const bool = regExp_hp3.test(hp3Value);

	    if (!bool) { // 연락처 마지막 4자리가 정규표현식에 위배된 경우
	        $("#hp_error").text("전화번호 형식이 올바르지 않습니다.").show();
	        return;
	    }

	    // hp2 값이 비어 있는지 확인
	    const hp2Value = $("input#hp2").val();
	    if (hp2Value === "") {
	        $("#hp_error").text("전화번호 중간 자리를 입력해주세요.").show();
	        return;
	    }

	    // hp3 값의 길이를 확인
	    if (hp3Value.length !== 4) {
	        $("#hp_error").text("전화번호 마지막 4자리를 입력해주세요.").show();
	        return;
	    }

	    // 모든 조건을 통과한 경우
	    $("#hp_error").hide();
	});
	
	
	$("input#hp2").on("input", (e) => {
		    const input = $(e.target);
		    const sanitizedValue = input.val().replace(/[^0-9]/g, ""); // 숫자가 아닌 문자를 제거
		    input.val(sanitizedValue); // 필드에 정제된 값 설정
	});
	$("input#hp3").on("input", (e) => {
	    const input = $(e.target);
	    const sanitizedValue = input.val().replace(/[^0-9]/g, ""); // 숫자가 아닌 문자를 제거
	    input.val(sanitizedValue); // 필드에 정제된 값 설정
	});

	 
	 // === jQuery UI 의 datepicker === //
	 $("input#datepicker").datepicker(	 {
	          dateFormat: 'yy-mm-dd'  //Input Display Format 변경
	         ,showOtherMonths: true   //빈 공간에 현재월의 앞뒤월의 날짜를 표시
	         ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
	         ,changeYear: true        //콤보박스에서 년 선택 가능
	         ,changeMonth: true       //콤보박스에서 월 선택 가능
	     //  ,showOn: "both"          //button:버튼을 표시하고,버튼을 눌러야만 달력 표시됨. both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시됨.  
	     //  ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
	     //  ,buttonImageOnly: true   //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
	     //  ,buttonText: "선택"       //버튼에 마우스 갖다 댔을 때 표시되는 텍스트
	         ,yearSuffix: "년"         //달력의 년도 부분 뒤에 붙는 텍스트
	         ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
	         ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
	         ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
	         ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
	     //  ,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
	     //  ,maxDate: "+1M" //최대 선택일자(+1D:하루후, +1M:한달후, +1Y:일년후)
	 });
	 
	 // 초기값을 오늘 날짜로 설정
	 // $('input#datepicker').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
	 
	 
	 // === 전체 datepicker 옵션 일괄 설정하기 ===  
	 //     한번의 설정으로 $("input#fromDate"), $('input#toDate')의 옵션을 모두 설정할 수 있다.
     $(function() {
		//모든 datepicker에 대한 공통 옵션 설정
		$.datepicker.setDefaults({
        	dateFormat: 'yy-mm-dd' //Input Display Format 변경
			,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
			,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
			,changeYear: true //콤보박스에서 년 선택 가능
			,changeMonth: true //콤보박스에서 월 선택 가능                
			// ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시됨. both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시됨.  
			// ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
			// ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
			// ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
			,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
			,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
			,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
			,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
			,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
			// ,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
			// ,maxDate: "+1M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                    
		});
         
		// input을 datepicker로 선언
		$("input#fromDate").datepicker();                    
		$("input#toDate").datepicker();
		
		// From의 초기값을 오늘 날짜로 설정
		$('input#fromDate').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
         
		// To의 초기값을 3일후로 설정
		$('input#toDate').datepicker('setDate', '+3D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
	});
	  
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	  
	$("input.requiredInfo_radio").bind("change", e => {
				$("#gender_error").hide();
	}); // 성별을 선택한 경우 경고문고를 숨김.
	
	
	$("#birth-year").on("change", function () {
		$("#birth-year").css({"color":"black"});
	});
	$("#birth-month").on("change", function () {
		$("#birth-month").css({"color":"black"});
	});
	$("#birth-day").on("change", function () {
		$("#birth-day").css({"color":"black"});
	});  
	
	$("#birth-day").on("change", function() {
	    $("#birth_error").hide(); // 생년월일을 모두 선택했다면 에러 메시지 숨기기
	});

	
	//////////////////////////////////////////////////////////////////////////
	
	// "아이디중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$("button#idcheck").click(function(){
		b_idcheck_click = true;
		
		$.ajax({
			url : "idDuplicateCheck.mp", 
			data : {"userid": $("input#userid").val()}, 
			type : "post",	
							
			dataType : "json",	
								
			success : function(json){
				
				const regExp_Userid = /^[a-z][a-z0-9_-]{3,14}$/;
							
				const bool = regExp_Userid.test($("#userid").val());
				
				if(json.isExists) { // 입력한 userid 가 이미 사용중이라면
					$("span#idcheckResult").html("이미 사용 중인 아이디입니다.").css({"color":"red","fontSize":"10pt"});
					$("input#userid").val("");
					b_idcheck_click = false;
				}
				else if($("input#userid").val()==""){
					$("span#idcheckResult").html("").css({"color":"blue"});
					b_idcheck_click = false;
				}
				else if(!bool){
					$("span#idcheckResult").html("").css({"color":"blue"});
					b_idcheck_click = false;
				}
				else { // 입력한 userid 가 존재하지 않는 경우라면
					$("span#idcheckResult").html("사용 가능한 아이디입니다.").css({"color":"navy","fontSize":"10pt"});
				}
			},
			
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
			
		});
		
	});
	
	
	// 아이디값이 변경되면 가입하기 버튼을 클릭시 "아이디중복확인" 을 클릭했는지 클릭안했는지 알아보기 위한 용도 초기화 시키기
	$("input#userid").bind("change", function(){
		b_idcheck_click = false;
	});
	
	
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$("button#emailcheck").click(function(){
		b_emailcheck_click = true; // "이메일중복확인" 을 클릭했는지 클릭 하지 않았는지 여부를 알아오기 위한 용도
		
		$.ajax({
			url: "emailDuplicateCheck.mp",
			data: {"email":$("input#email").val()},  
			type: "post",	
			dataType: "json",		
			success:function(json){
				const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
				// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
				
				const bool = regExp_email.test($("#email").val());
				
				if(json.isExists) { // 입력한 email 이 이미 사용중이면
					$("span#emailCheckResult").html("이미 사용 중인 이메일입니다.").css({
					        "color": "red",
					        "fontSize": "10pt"
					    });
					$("input#email").val("");
					b_emailcheck_click = false;
				}
				else if($("input#email").val()==""){
					$("span#emailCheckResult").html("");
					b_emailcheck_click = false;
				}
				else if(!bool){
					$("span#emailCheckResult").html("");
					b_emailcheck_click = false;
				}
				else { // 입력한 email 이 존재하지 않는다면
					$("span#emailCheckResult").html("사용 가능한 이메일입니다.").css({"color":"navy","fontSize": "10pt"});
				}
			},
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
		
	});
	
	// 아이디값이 변경되면 가입하기 버튼을 클릭시 "이메일중복확인" 을 클릭했는지 클릭안했는지 알아보기 위한 용도 초기화 시키기
	$("input#email").bind("change", function(){
		b_emailcheck_click = false;
	});
	
	
	b_phoneCheck_click = false; // "전송" 버튼을 클릭했는지 클릭 하지 않았는지 여부를 알아오기 위한 용도(전화번호 중복확인 체크용)
	// === 전송 버튼 클릭 시 시작 === //
	$("#phoneCheckandAuth").click(function(){
		$("#phoneCheckandAuth").html("재전송");
		if($("#hp2").val()==""||$("#hp3").val()==""){
			$("#authMsg").html("<span style='color:red; font-size:10pt;'>전화번호를 입력해주세요.</span>");
			return false;
		}
		/*======= 전화번호 유효성 검사======*/
		
		const regExp_hp2 = /^[1-9][0-9]{3}$/; // 첫 번째 숫자는 1-9, 나머지는 0-9로 이루어진 4자리 숫자
		const hp2Value = $("#hp2").val(); // hp2 입력값 가져오기
		const isHp2Valid = regExp_hp2.test(hp2Value); // 정규표현식 검사
		
		if (!isHp2Valid) { // hp2 값이 정규표현식에 맞지 않는 경우
		    $("#hp_error").text("전화번호 중간 자리를 정확하게 입력해 주세요.").show();
		    return;
		}
		
		const regExp_hp3 = /^[1-9][0-9]{3}$/; // 첫 번째 숫자는 1-9, 나머지는 0-9로 이루어진 4자리 숫자
		const hp3Value = $("#hp3").val(); // 입력된 값 가져오기
		const isHp3Valid = regExp_hp3.test(hp3Value); // 정규표현식 검사
		
		if (!isHp3Valid) { // 연락처 마지막 4자리가 정규표현식에 위배된 경우
		    $("#hp_error").text("전화번호 형식이 올바르지 않습니다.").show();
		    return;
		}

		
				
		const phoneNumber = $("input#hp1").val() + $("input#hp2").val() + $("input#hp3").val(); 
		$.ajax({
			url: "phDuplicateCheck.mp",
			data: {"phoneNumber":phoneNumber,
			"randomNumberAuth":"",
			"authPassInput":""
			
			},  
			type: "post",	
			dataType: "json",		
			success:function(json){
				console.log(json.isExists);
				
				if(json.isExists) { //전화번호를 사용 중인 경우
					b_phoneCheck_click = false; 
					console.log("사용 중인 전화번호입니다.");
					$("#authMsg").html("<span style='color:red; font-size:10pt;'>사용 중인 전화번호입니다.</span>").show();
					$("#hp2").val("");
					$("#hp3").val("");
					$("#hp2").focus(); 
					return;
				}
				else if(json.phoneNumber == null || json.phoneNumber == undefined){// 전화번호가 중복되지 않은 경우
					b_phoneCheck_click = true; 
					console.log("사용 가능한 전화번호입니다.");
					$("#authMsg").html("<span style='color:navy; font-size:10pt;'>사용 가능한 전화번호입니다. 인증을 진행해 주세요.</span>").show();
					$("#authPassElmt").show();
				}
				
			},
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	}); // end of $("#phoneCheckandAuth").click(function(){})----------------------
	// === 전화번호 인증 버튼 클릭 시 끝 === //
	
}); // end of $(document).ready(function(){})---------------------------------------------------------------



// function declaration

////////////////// 전화번호 중복 통과 후 생기는 인증하기 버튼 클릭 시 //////////////////

var b_isAuth_click = false;

$(document).on('click', '#authPassBtn', function() {
		let randomNumberAuth ="randomNumberAuth";
		
		$.ajax({
			url: "phDuplicateCheck.mp",
			data: {"randomNumberAuth":randomNumberAuth,
					"authPassInput":$("input[name='authPassInput']").val(),
					"phoneNumber" : ""
			},  
			type: "post",	
			dataType: "json",		
			success:function(json){
				
				if(json.isAuthStatus) { //인증번호가 일치한 경우
					$("#authPassElmt").hide();
					$("#authMsg").html("<span style='color:navy; font-size:10pt;'>전화번호 인증이 완료되었습니다.</span>");
					b_isAuth_click = true;
				}
				else{// 인증번호가 일치하지 않은 경우
					$("#authMsg").html("<span style='color:red; font-size:10pt;'>전화번호 인증에 실패하였습니다.</span>");
					$("#authPassElmt").hide();
					$("#authPassValue").val("");
					b_phoneCheck_click = false; 
				}
			},
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
		
	});


// "가입하기" 버튼 클릭시 호출되는 함수
function goRegister() {

	// 가입하기 버튼 클릭 시 값 입력을 안한 곳으로 이동
	if($("#name").val().trim()==""){
		$("#name").focus();
		return;
		
	}
	const name = $("#name").val().trim();
    const regExp_Name = /^[가-힣\s]{2,10}$/;
    if (name === "" || !regExp_Name.test(name)) { // 이름이 공백이거나 정규식을 통과하지 못한 경우
		$("#name").focus();
		return;
    }
	if($("#userid").val().trim()==""){
			$("#userid").focus();
			return;	
	}
	if($("#pwd").val().trim()==""){
			$("#pwd").focus();
			return;	
	}
	if($("#pwdcheck").val().trim()==""){
			$("#pwdcheck").focus();
			return;	
	}
	
	const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
	// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
	
	const bool = regExp_pwd.test($("#pwd").val().trim());
	
	if(!bool) { // 암호가 정규표현식에 위배된 경우
		$("#pwd").focus();
		return;	
	}
	
	if($("#pwdcheck").val().trim()!=$("#pwd").val().trim()){
			$("#pwdcheck").focus();
			return;	
	}
	
	
	
	if($("#email").val().trim()==""){
			$("#email").focus();
			return;	
	}
	if($("#hp2").val().trim()==""){
			$("#hp2").focus();
			return;	
	}
	if($("#hp3").val().trim()==""){
			$("#hp3").focus();
			return;	
	}
	
	
	// **** "성별"을 선택했는지 검사하기 시작 **** //
	const radio_chechked_length = $("input:radio[name='gender']:checked").length;
	
	if(radio_chechked_length == 0) {
		//alert("성별을 선택하셔야 합니다.");
		$("#gender_error").show();
		return; // goRegister() 함수를 종료한다.
	}
	
	// **** "생년월일"을 선택했는지 검사하기 시작 **** //
	var year = $("#birth-year").val(); 
    var month = $("#birth-month").val(); 
    var day = $("#birth-day").val(); 

   if (!year || !month || !day) {  // 세 가지 중 하나라도 선택되지 않으면
       $("#birth_error").show();
	   return;
   } else {
       $("#birth_error").hide(); 
   }
		
	// **** 약관에 동의를 했는지 검사하기 시작 **** //
	const checkbox_chechked_length = $("input:checkbox[id='agree']:checked").length;
		
	if(checkbox_chechked_length == 0) {
		alert("이용약관에 동의하셔야 합니다.");
		return; // goRegister() 함수를 종료한다.
	}

	

		
	// **** "아이디 중복확인"을 클릭했는지 검사하기 시작 **** //
	if(!b_idcheck_click) {
		alert("아이디 중복확인을 클릭하셔야 합니다.");
		return;
	}
	
	// **** "이메일 중복확인"을 클릭했는지 검사하기 시작 **** //
	if(!b_emailcheck_click) {
		alert("이메일 중복확인을 클릭하셔야 합니다.");
		return; 
	}
	
	// **** "전송"을 클릭했는지 검사하기 시작 **** //
	if(!b_phoneCheck_click) {
		alert("전송 버튼을 클릭하셔야합니다.");
		return; 
	}
	
	// **** "인증"을 클릭했는지 검사하기 시작 **** //
	if(!b_isAuth_click){
		alert("인증 버튼을 클릭하셔야합니다.");
		return; 
	}
	
	const frm = document.registerFrm;
	frm.aciton = "memberRegister.mp";
	frm.method = "post";
	frm.submit();
}


