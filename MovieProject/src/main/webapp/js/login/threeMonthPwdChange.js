$(document).ready(function(){

		
	$(document).on('blur', "input[name='newPwd1']", function(){
	
	 const currentPwd = $("input[name='currentPwd']").val().trim(); //현재비번
     const newPwd1 = $("input[name='newPwd1']").val().trim();	// 새 비번
		
		
		// 현재 비밀번호와 새 비밀번호가 동일한 경우
    if (currentPwd === newPwd1) {
        $("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>현재와 동일한 비밀번호는 사용하실 수 없습니다.</p>");
        $("input[name='newPwd1']").val(""); // 입력된 새 비밀번호 초기화
        return;
    }
		
		
		const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
		
		
		const bool = regExp_pwd.test($("input[name='newPwd1']").val());
		
		if(!bool) {
			// 새 비밀번호가 정규표현식에 위배된 경우
			$("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>숫자,문자,특수문자 포함 형태의 8~15자리 이내의 비밀번호를 입력해 주세요</p>");
			$("input[name='newPwd1']").val("");
		}
		else if($("input[name='currentPwd']").val()==$("input[name='newPwd1']").val()){ // 새 비밀번호가 현재 비밀번호와 같은 경우
			$("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>현재와 동일한 비밀번호는 사용하실 수 없습니다.</p>");
			$("input[name='newPwd1']").val("");
		}
		else {
			// 새 비밀번호가 정규표현식에 부합하는 경우
			$("#newPwdMent").html("");
		}
			
	}); // end of $(document).on('blur', "input[name='newPwd1']", function(){}) -------------------
	
	
	$(document).on('blur', "input[name='newPwd2']", function(){
					
		if( $("input[name='newPwd2']").val() != $("input[name='newPwd1']").val() ) {
			// 새 비밀번호와 새 비밀번호 확인값이 일치하지 않는 경우
			$("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>새 비밀번호와 일치하지않습니다.</p>");
			$("input[name='newPwd2']").val("");
		}
		else {
			// 암호와 암호확인값이 일치하는 경우
			$("#newPwdMent").html("");
		}
		
	}); //end of $("#newPwd2").blur((e) => {})
	

});// end of $(document).ready(function(){})------------------------


///////////////////////////////////////////////////
///////////// Function Declaration ////////////////
///////////////////////////////////////////////////


// 현재 비밀번호 입력란에서 커서가 벗어날 경우 
$(document).on('blur', "input[name='currentPwd']", function(){
	
	/*
	var form = document.threeMonthFrm;
	form.action = $("#path").text() + "/login/threeMonthPwdChange.mp";
	form.method = "POST";
	form.submit();
	*/
	
	$.ajax({
	    url: "threeMonthPwdChange.mp", // 서버의 컨트롤러 매핑 주소
	    data: {
	        "inputPwd": $("input:password[name='currentPwd']").val().trim(),
	        "number": "0"
	    },
	    type: "post",
	    dataType: "json", // 서버에서 JSON 형식의 응답을 받음
	    success: function(json) {
	        console.log("서버 응답:", json);
	        if(json.statusPwd){
	        	//현재비밀번호와 사용자가 입력한 비밀번호가 일치함
	        	$("#currentPwdMent").html("")
	        }
	        else{
	        	//현재비밀번호와 사용자가 입력한 현재 비밀번호가 일치하지않음
	        	$("#currentPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>비밀번호를 정확하게 입력해주세요.<p>")
	        	$("input[name='currentPwd']").val("");
	        }
	    },
	    error: function(request, status, error) {
	        alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
	    }
	});
});





//비밀번호 변경 클릭 시
$(document).on('click', '#submitBtn', function(){
/*
	alert("/login/threeMonthPwdChange.mp로 url을 이동합니다.");
	const form = document.threeMonthFrm;
	form.action = $("#path").text() + "/login/threeMonthPwdChange.mp";
	form.method = "post";
	form.submit();
	//location.href = $("#path").text() + "/index.mp";// 로그인 페이지로 이동
*/

const currentPwd = $("input[name='currentPwd']").val().trim();
    const newPwd1 = $("input[name='newPwd1']").val().trim();
    const newPwd2 = $("input[name='newPwd2']").val().trim();

	// 현재 비밀번호 검증
    if (currentPwd === "") {
        $("input[name='currentPwd']").focus();
        $("#currentPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>비밀번호를 정확하게 입력해주세요.</p>");
        return;
    }

    // 새 비밀번호 검증
    if (newPwd1 === "") {
        $("input[name='newPwd1']").focus();
        $("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>숫자,문자,특수문자 포함 형태의 8~15자리 이내의 비밀번호를 입력해 주세요.</p>");
        return;
    }

    // 새 비밀번호와 현재 비밀번호가 동일한 경우
    if (currentPwd === newPwd1) {
        $("input[name='newPwd1']").focus();
        $("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>기존과 동일한 비밀번호는 사용하실 수 없습니다.</p>");
        return;
    }

    // 새 비밀번호 확인 검증
    if (newPwd2 === "") {
        $("input[name='newPwd2']").focus();
        $("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>새 비밀번호와 일치하지 않습니다.</p>");
        return;
    }

    // 비밀번호 확인 일치 여부 검증
    if (newPwd1 !== newPwd2) {
        $("input[name='newPwd2']").focus();
        $("#newPwdMent").html("<p style='color:red; font-size:14px;margin-top: 5px;'>새 비밀번호와 일치하지 않습니다.</p>");
        return;
    }
	
	$.ajax({
	    url: "threeMonthPwdChange.mp", // 서버의 컨트롤러 매핑 주소
	    data: {
	        "number": "1",
	        "pwd": $("input[name='newPwd1']").val().trim()
	    },
	    type: "post",
	    dataType: "json", // 서버에서 JSON 형식의 응답을 받음
	    success: function(json) {
	        console.log("서버 응답:", json);
	       if(json.updatePwd!=0){
	       	alert("비밀번호가 변경되었습니다.");
	       	location.href = $("#path").text() + "/index.mp";
	       }
	       else{
	       alert("비밀번호 변경에 실패하였습니다.");
	       location.href = $("#path").text() + "/index.mp";
	       }
	    },
	    error: function(request, status, error) {
	        alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
	    }
	});
});//end of $(document).on('click', '#submitBtn', function(){}) ----------------









$(document).on('click', '#button', function(){
	location.href = $("#path").text() + "/index.mp";// 로그인 페이지로 이동
});//end of $(document).on('click', '#submit', function(){}) ----------------



