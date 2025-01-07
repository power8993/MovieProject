
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
	
//	$("input#name").bind("blur", function(e){ alert("name 에 있던 포커스를 읽어버렸습니다") });
//	또는
//	$("input#name").blur(function(e){ alert("name 에 있던 포커스를 읽어버렸습니다") });
//	또는
	$("input#name").blur((e) => {
		
		const name = $(e.target).val().trim();
		if(name == "") {
			// 입력하지 않거나 공백만 입력했을 경우
			
			
			$(e.target).next().show();
		//	또는
			//$(e.target).parent().find("span.error").show();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			
			$("table#tblMemberRegister :input").prop("disabled", false);
			$(e.target).next().hide();
		//	또는
		//	$(e.target).parent().find("span.error").hide();
		}
		
	}); // 아이디가 name 인 것이 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.

	
	$("input#userid").blur((e) => {
			
		//const userid = $(e.target).val().trim();
		
		const regExp_Userid = /^[a-zA-Z][a-zA-Z0-9_-]{3,14}$/;
			
		const bool = regExp_Userid.test($(e.target).val());
		
		if(!bool) {
			// 아이디가 정규표현식에 위배된 경우
			
			$("#idcheckResult").text(""); // 중복확인이 성공한 경우의 문구가 있을 수 있으니 사전에 제거
			$(e.target).parent().next().show();
			
		//	또는
			//$(e.target).parent().find("span.error").show();
		}
		else {
			// 아이디가 정규표현식에 부합하는 경우
			
			$("table#tblMemberRegister :input").prop("disabled", false);
			$(e.target).parent().next().hide();
		//	또는
		//	$(e.target).parent().find("span.error").hide();
		}
		
	}); // 아이디가 userid 인 것이 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
		
		
	$("input#pwd").blur((e) => {
				
	//	const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
	//	또는
		const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
		// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
		
		const bool = regExp_pwd.test($(e.target).val());
		
		if(!bool) {
			// 암호가 정규표현식에 위배된 경우
			
			$(e.target).next().show();
		//	또는
		//	$(e.target).parent().find("span.error").show();
		}
		else {
			// 암호가 정규표현식에 부합하는 경우
			
			$("table#tblMemberRegister :input").prop("disabled", false);
		//	$(e.target).next().show();
		//	또는
			$(e.target).next().hide();
		}
		
	}); // 아이디가 pwd 인 것이 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$("input#pwdcheck").blur((e) => {
					
		if( $("input#pwd").val() != $(e.target).val() ) {
			// 암호와 암호확인값이 일치하지 않는 경우
			
			
			$(e.target).val("");
			
		//	$(e.target).next().show();
		//	또는
			$(e.target).next().show();
		}
		else {
			// 암호와 암호확인값이 일치하는 경우
			
			$("table#tblMemberRegister :input").prop("disabled", false);
		//	$(e.target).next().show();
		//	또는
			$(e.target).next().hide();
		}
		
	}); // 아이디가 pwdcheck 인 것이 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$("input#email").blur((e) => {
					
	//	const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	//	또는
		const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
		// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
		
		const bool = regExp_email.test($(e.target).val());
		
		if(!bool) {
			// 이메일이 정규표현식에 위배된 경우
			
			
			$("#emailCheckResult").text(""); // 중복확인이 성공한 경우의 문구가 있을 수 있으니 사전에 제거
			$(e.target).parent().next().show();
		//	또는
		//	$(e.target).parent().find("span.error").show();
		}
		else {
			// 이메일이 정규표현식에 부합한 경우
			
			$(e.target).parent().next().hide();
		//	또는
		//	$(e.target).parent().find("span.error").hide();
		}
		
	}); // 아이디가 email 인 것이 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$("input#hp2").blur((e) => {
						
	//	const regExp_hp2 = /^[1-9][0-9]{3}$/;
	//	또는
		const regExp_hp2 = new RegExp(/^[1-9][0-9]{3}$/);
		// 연락처 국번( 숫자 4자리인데 첫번째 숫자는 1-9 이고 나머지는 0-9) 정규표현식 객체 생성
		
		const bool = regExp_hp2.test($(e.target).val());
		
		if(!bool) {
			// 연락처 국번이 정규표현식에 위배된 경우
		//	$(e.target).next().show();
		//	또는
			$(e.target).next().next().show();
		}
		else {
			// 연락처 국번이 정규표현식에 부합하는 경우
			if($("input#hp3").val()==""){
					$("#hp_error").show();
					return;
				}
			
		//	$(e.target).next().show();
		//	또는
			$("#hp_error").hide();
		}
		
	}); // 아이디가 hp2 인 것이 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.

	
	$("input#hp3").blur((e) => {
		const regExp_hp3 = new RegExp(/^\d{4}$/); 
		// 연락처 국번( 숫자 4자리인데 첫번째 숫자는 1-9 이고 나머지는 0-9) 정규표현식 객체 생성
		
		const bool = regExp_hp3.test($(e.target).val());
		
		if(!bool) {
			// 연락처 마지막 4자리가 정규표현식에 위배된 경우
			$("#hp_error").show();
		}
		else {
			if($("input#hp2").val()==""){
					$("#hp_error").show();
					return;
				}
			// 연락처 마지막 4자리가 정규표현식에 부합하는 경우
		$("#hp_error").hide();
		}
		
	}); // 아이디가 hp3 인 것이 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
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
	  
	  
	/*$("input#datepicker").bind("keyup", e => {
		$(e.target).val("").next().show();
	}); // 생년월일을 키보드로 입력하는 경우
	
	$("input#datepicker").bind("change", e => {
		if( $(e.target).val() != "" ) {
			$(e.target).next().hide();
		}
	}); // 생년월일을 마우스로 입력하는 경우*/
	
	$("input.requiredInfo_radio").bind("change", e => {
				$("#gender_error").hide();
	}); // 성별을 선택한 경우 경고문고를 숨김.
	
	/* 생년월일 선택시 진해지게(원래 회색이었던 것을 검정색으로) */
	$("#birth-year").on("change", function () {
		$("#birth-year").css({"color":"black"});
	});
	$("#birth-month").on("change", function () {
		$("#birth-month").css({"color":"black"});
	});
	$("#birth-day").on("change", function () {
		$("#birth-day").css({"color":"black"});
	});
	
	
	/////
	
	
	$("#birth-day").on("change", function() {
	    $("#birth_error").hide(); // 생년월일을 모두 선택했다면 에러 메시지 숨기기
	});

			
		



	
	//////////////////////////////////////////////////////////////////////////
	
	// "아이디중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$("button#idcheck").click(function(){
		b_idcheck_click = true;
		
		$.ajax({
			url : "idDuplicateCheck.mp", // js 파일이므로 상대경로를 사용할 수 없다.
			data : {"userid": $("input#userid").val()}, // data 속성은 http://localhost:9090/MyMVC/member/idDuplicateCheck.mp 로 전송해야할 데이터를 말한다.
			type : "post",	
							
			dataType : "json",	
								
			success : function(json){
				console.log(json);
				
				
				console.log("~~~ json 의 데이터타입 : ", typeof json);
				// ~~~ json 의 데이터타입 : object
				
				const regExp_Userid = /^[a-zA-Z][a-zA-Z0-9_-]{3,14}$/;
							
				const bool = regExp_Userid.test($("#userid").val());
				
				if(json.isExists) {
					// 입력한 userid 가 이미 사용중이라면
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
				else {
					// 입력한 userid 가 존재하지 않는 경우라면
					$("span#idcheckResult").html("사용 가능한 아이디입니다.").css({"color":"navy","fontSize":"10pt"});
				}
			},
			
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
			
		});
		
	});
	// "아이디중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	
	// 아이디값이 변경되면 가입하기 버튼을 클릭시 "아이디중복확인" 을 클릭했는지 클릭안했는지 알아보기 위한 용도 초기화 시키기
	$("input#userid").bind("change", function(){
		b_idcheck_click = false;
	});
	
	
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 시작 //
	$("button#emailcheck").click(function(){
		b_emailcheck_click = true;
		// "이메일중복확인" 을 클릭했는지 클릭 하지 않았는지 여부를 알아오기 위한 용도
		
		$.ajax({
			url: "emailDuplicateCheck.mp",
			data: {"email":$("input#email").val()},  
			type: "post",	
			dataType: "json",		
			success:function(json){
				const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
				// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
				
				const bool = regExp_email.test($("#email").val());
				
				if(json.isExists) {
					// 입력한 email 이 이미 사용중이면
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
				else {
					// 입력한 email 이 존재하지 않는다면
					$("span#emailCheckResult").html("사용 가능한 이메일입니다.").css({"color":"navy","fontSize": "10pt"});
				}
			},
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
		
	});
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //
	
	// 아이디값이 변경되면 가입하기 버튼을 클릭시 "이메일중복확인" 을 클릭했는지 클릭안했는지 알아보기 위한 용도 초기화 시키기
	$("input#email").bind("change", function(){
		b_emailcheck_click = false;
	});
	
	
	b_phoneCheck_click = false; // "인증" 버튼을 클릭했는지 클릭 하지 않았는지 여부를 알아오기 위한 용도(전화번호 중복확인 체크용)
	// === 전화번호 인증 버튼 클릭 시 시작 === //
	$("#phoneCheckandAuth").click(function(){
		
				
				const phoneNumber = $("input#hp1").val() + $("input#hp2").val() + $("input#hp3").val(); 
				
				$.ajax({
					url: "phDuplicateCheck.mp",
					data: {"phoneNumber":phoneNumber},  
					type: "post",	
					dataType: "json",		
					success:function(json){
						
						if(json.isExists) { //전화번호를 사용 중인 경우
							b_phoneCheck_click = false; 
							$("#errortest").html("사용 중인 전화번호입니다.");
						}
						else{// 전화번호가 중복되지 않은 경우
							b_phoneCheck_click = true; 
							$("#errortest").html("사용 가능한 전화번호이므로 인증을 진행해 주세요.");
							$("#authPassElmt").show();
						}
					},
					error: function(request, status, error){
		                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		            }
				});
	});// end of $("button#emailcheck").click(function(){
	// === 전화번호 인증 버튼 클릭 시 끝 === //
	
}); // end of $(document).ready(function(){})---------------------------------------------------------------



// function declaration

////////////////// 전화번호 중복 통과 후 생기는 인증하기 버튼 클릭 시 //////////////////
$(document).on('click', '#authPassBtn', function() {
		//location.href = json.ctxPath + "/member/smsSend.mp?authType="+"registerMobileAuth";
		alert("인증하기 버튼 클릭 됨.");
	});


// "가입하기" 버튼 클릭시 호출되는 함수
function goRegister() {

	// 가입하기 버튼 클릭 시 값 입력을 안한 곳으로 이동
	
	if($("#name").val().trim()==""){
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
	
	// **** "성별"을 선택했는지 검사하기 끝 **** //
	
	
	
	//alert($("#birth-day > option").text()); // 값 선택 x 시 출생연도/ 그외 출생연도 + select의 text값들 모두
	
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

	// **** 약관에 동의를 했는지 검사하기 끝 **** //
	

		
	// **** "아이디 중복확인"을 클릭했는지 검사하기 시작 **** //
	if(!b_idcheck_click) {
		// "아이디중복확인"을 클릭 하지 않았을 경우
		alert("아이디 중복확인을 클릭하셔야 합니다.");
		return; // goRegister() 함수를 종료한다.
	}
	
	// **** "아이디 중복확인"을 클릭했는지 검사하기 끝 **** //
	
	// **** "이메일 중복확인"을 클릭했는지 검사하기 시작 **** //
	if(!b_emailcheck_click) {
		// "이메일중복확인"을 클릭 하지 않았을 경우
		alert("이메일 중복확인을 클릭하셔야 합니다.");
		return; // goRegister() 함수를 종료한다.
	}
	
	// **** "이메일 중복확인"을 클릭했는지 검사하기 끝 **** //
	
	// **** "인증버튼"을 클릭했는지 검사하기 시작 **** //
	if(!b_phoneCheck_click) {
		// "전화번호 인증"을 클릭 하지 않았을 경우
		alert("인증 버튼을 클릭하셔야합니다.");
		return; 
	}
	// **** "인증버튼"을 클릭했는지 검사하기 끝 **** //
	
	const frm = document.registerFrm;
	frm.aciton = "memberRegister.mp";
	frm.method = "post";
	frm.submit();
	
	
}

/* 취소하기 => 없앰
function goReset() {

    history.back();
}// end of function goReset()---------------------
*/


