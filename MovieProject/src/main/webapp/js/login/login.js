
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
	
	const frm = document.loginFrm;
	frm.submit();
	
} // end of function goLogin()---------------------------


// === 로그아웃 처리 함수 === //
function goLogOut(ctx_Path) {
	
	// 로그아웃을 처리해주는 페이지로 이동
	location.href=`${ctx_Path}/login/logout.up`;
	
} // end of function goLogOut()-----------------------------