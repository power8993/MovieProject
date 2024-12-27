
$(document).ready(function(){
	
	$("button#btnSubmit").click( () => {
		goLogin(); // 로그인 시도한다.
	});
	
	$("input#loginPwd").bind("keydown", (e) => {
		if(e.keyCode == 13) { // 암호입력란에 엔터를 했을 경우
			goLogin(); // 로그인 시도한다.
		}
	});
	
}); // end of $(document).ready(function(){})---------------------------------


// Function Declaration

// === 로그인 처리 함수 === //
function goLogin() {
	
	// alert("ㄹ그인 한다");
	
	if( $("input#loginUserid").val().trim() == "" ) {
		alert("아이디를 입력하세요!!");
		$("input#loginUserid").val("").focus();
		return; // goLogin() 함수 종료
	}
	
	if( $("input#loginPwd").val().trim() == "" ) {
		alert("암호를 입력하세요!!");
		$("input#loginPwd").val("").focus();
		return; // goLogin() 함수 종료
	}
	
	if( $("input:checkbox[id='saveid']").prop("checked") ) {
		// alert("아이디저장 체크를 하셨네요~~^^");
		
		localStorage.setItem('saveid', $("input#loginUserid").val());
	}
	
	else {
		// alert("아이디저장 체크를 해제하셨네요~~^^");
		
		localStorage.removeItem('saveid');
	}
	
	/*const frm = document.loginFrm;
	frm.submit();*/
	
	$.ajax({
	    url: "login.mp", // 서버의 컨트롤러 매핑 주소
	    data: {
	        "pwd": $("#loginPwd").val().trim(),
	        "userid": $("#loginUserid").val().trim()
	    },
	    type: "post",
	    dataType: "json", // 서버에서 JSON 형식의 응답을 받음
	    success: function(json) {
	        //console.log("서버 응답:", json);
	        let resultHtml = "";
			//console.log("겟아이들 : ",json.getIdle);
	        if (json.getIdle == 0 ) { //휴면계정일 경우 (휴면계정이 아니라면 99임)
				//console.log("확인용 : " + json.getIdle);
				resultHtml = `				<div style="width:500px; margin:0 auto; margin-top:100px;">
					<h1 style="font-size: 32px; font-weight: bold;">휴면 계정 안내</h1>
					<p>
						<span>안녕하세요!
				        	<br>
						회원님은<span style="font-weight:700px;">HGV계정에 1년 이상 로그인하지 않아</span> 관련 법령에 따라<br>
						휴면 상태로 전환되었습니다. </span>
					</p>
					
					<div style="background-color:#e6ccb3; padding:20px 0 5px 10px;">

							<p>마지막 접속일 : (마지막 로그인 날짜데이터)</p> 
							<p>휴면 전환일 : (마지막 로그인 날짜데이터+ 1년 후)</p>
					</div>
					
					<p style="color:gray; font-size:13px; margin-top:20px;" >HGV 계정 서비스를 계속 이용하시려면 <span style="font-weight:700px;">[휴면 해제하기] 버튼을 클릭해주세요.</span></p>

				<hr>
				<div style="display:flex; justify-content:space-between; margin-top:30px;">
				<button type="button" id="idleCancel" style="border:solid 1px #cccccc;background-color:transparent; height:40px;width:100%;">취소</button> 
				<button type="button" id="idleClear" style="border:solid 1px #cccccc; height:40px;width:100%;">휴면 해제하기</button> 
				</div>
				</div>`;
				$("#form_container").html(resultHtml);
	        }
	        else if (json.getIdle == 99 ) {
	            //로그인이 완료된 경우 && 휴면계정이 아닐 경우 (getIdle이 있다는 것은 로그인이 되었다는 것.)
				//console.log(json.ctxPath+"/index.mp");

				location.href = json.ctxPath+"/index.mp";

	        } 
			else if(json.loginuser==null){
				resultHtml ="아이디 또는 비밀번호가 일치하지 않습니다.";
				$("#loginUserid").val("");
				$("#loginPwd").val("");
				$("#login_error").html(resultHtml);
			}
	        // 결과를 div에 삽입
	        
	    },
		error: function(request, status, error) {
		    console.error("AJAX 요청 실패:", status, error);
		    alert("로그인 요청에 실패했습니다. 다시 시도해주세요.");
		}

	});
	
} // end of function goLogin()---------------------------


// === 로그아웃 처리 함수 === //
function goLogOut(ctx_Path) {
	
	// 로그아웃을 처리해주는 페이지로 이동
	location.href=`${ctx_Path}/login/logout.mp`;
	
} // end of function goLogOut()-----------------------------