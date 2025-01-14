//비밀번호 마지막 변경일 select
$(document).ready(function() {
	$.ajax({
		url: "mylastpwdchangedateJSON.mp",  // 서버 URL
		data: { "userid": $("input#userid").val() },  // 사용자 ID를 서버에 전송
		dataType: "json",  // 응답 데이터 타입
		success: function(json) {
			$("div#lastpwdchangedate").text("비밀번호 변경일: " + json.lastpwdchangedate);
		},
		error: function(request, status, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}
	});
});


let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_email_change = false;
// 이메일값을 변경했는지 여부를 알아오기 위한 용도

$(document).ready(function() {

	$("span.error").hide();

	$("input#email").blur((e) => {

		const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);

		const bool = regExp_email.test($(e.target).val());

		if (!bool) {
			// 이메일이 정규표현식에 위배된 경우 
			$(e.target).parent().find("span.error").show();
			$(e.target).val("").focus();
		}
		else {
			$(e.target).parent().find("span.error").hide();
		}

	});


	$("input#hp2").blur((e) => {

		const regExp_hp2 = new RegExp(/^[1-9][0-9]{3}$/);

		const bool = regExp_hp2.test($(e.target).val());

		if (!bool) {

			$(e.target).parent().find("span.error").show();

			$(e.target).val("").focus();
		}
		else {
			$(e.target).parent().find("span.error").hide();
		}

	});


	$("input#hp3").blur((e) => {

		const regExp_hp3 = new RegExp(/^\d{4}$/);

		const bool = regExp_hp3.test($(e.target).val());

		if (!bool) {

			$(e.target).parent().find("span.error").show();

			$(e.target).val("").focus();
		}
		else {

			$(e.target).parent().find("span.error").hide();
		}

	});

	$("span#emailcheck").click(function() {

		b_emailcheck_click = true;

		$.ajax({
			url: "emailDuplicateCheck2.mp",
			data: {
				"email": $("input#email").val()
				, "userid": $("input:hidden[name='userid']").val()
			},
			type: "post",

			async: true,
			dataType: "json",
			success: function(json) {
				const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
				// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성

				const bool = regExp_email.test($("#email").val());


				if (json.isExists) { // 입력한 email 이 이미 사용중이면
					$("span#emailCheckResult").html("이미 사용 중인 이메일입니다.").css({
						"color": "red",
						"fontSize": "10pt"
					});
					$("input#email").val("");
					b_emailcheck_click = false;
				}
				else if ($("input#email").val() == "") {
					$("span#emailCheckResult").html("");
					b_emailcheck_click = false;
				}
				else if (!bool) {
					$("span#emailCheckResult").html("");
					b_emailcheck_click = false;
				}
				else { // 입력한 email 이 존재하지 않는다면
					$("span#emailCheckResult").html("사용 가능한 이메일입니다.").css({ "color": "navy", "fontSize": "10pt" });
				}
			},

			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});

	});

	$("input#email").bind("change", function() {

		b_emailcheck_click = false;

		b_email_change = true;
	});


});// end of $(document).ready(function(){})----------------------



function goEdit() {

	let b_requiredInfo = false;

	const requiredInfo_list = document.querySelectorAll("input.requiredInfo");
	for (let i = 0; i < requiredInfo_list.length; i++) {
		const val = requiredInfo_list[i].value.trim();
		if (val == "") {
			alert("*표시된 필수입력사항은 모두 입력하셔야 합니다.");
			b_requiredInfo = true;
			break;
		}
	}// end of for-----------------------------


	if (b_requiredInfo) {
		return; // goRegister() 함수를 종료한다.
	}

	if (b_email_change && !b_emailcheck_click) {
		alert("이메일 중복확인을 클릭하셔야 합니다.");
		return; // goEdit() 함수를 종료한다.
	}

	const frm = document.editFrm;
	frm.action = "myupEditEnd.mp";
	frm.method = "post";
	frm.submit();
}


function mypasswdFind_update(userid, ctxPath) {
	const mypasswdFind = $("div#passwdFind_modal"); // 모달을 넣을 위치	

	const modal_popup = `<div class="modal fade" id="passwdFind" tabindex="-1" aria-labelledby="passwdFindLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <!-- Modal header -->
	            <div class="modal-header">
	                <h4 class="modal-title" id="passwdFindLabel">비밀번호 변경</h4>
	                <button type="button" class="close passwdFindClose" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <!-- Modal body -->
	            <div class="modal-body">
	                <div id="pwFind">
	                    <form name="pwdUpdateEndFrm">
	                        <div class="div_pwd">
	                            <span>새암호</span><br/> 
	                            <input type="password" name="pwd" size="25" />
	                        </div>
	                         
	                        <div class="div_pwd">
	                            <span>새암호확인</span><br/>
	                            <input type="password" id="pwd2" size="25" />
	                        </div>
	                         
	                        <input type="hidden" name="userid" value="${userid}" />
	                   
	                        <div>
	                            <button type="button" class="btn btn-success">암호변경하기</button>
	                        </div>
	                    </form>
	                </div>
	            </div>
	            <!-- Modal footer -->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-danger passwdFindClose" data-dismiss="modal">Close</button>
	            </div>
	        </div>
	    </div>
	</div>`;

	mypasswdFind.html(modal_popup); // 모달 HTML 삽입

	// 모달을 표시
	$('#passwdFind').modal('show'); // 모달 띄우기

	// 수정 완료 버튼 클릭
	$('button.btn-success').on('click', function(e) {
		passwdFind_update_data(e);
	});

	$("button.btn-success").click(function() {
		const pwd = $("input:password[name='pwd']").val();
		const pwd2 = $("input:password[id='pwd2']").val();

		if (pwd !== pwd2) {
			alert("암호가 일치하지 않습니다.");
			$("input:password[name='pwd']").val("");
			$("input:password[id='pwd2']").val("");
			return;
		}

		const regExpPwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/;

		if (!regExpPwd.test(pwd)) {
			alert("암호는 8글자 이상 15글자 이하에 영문자, 숫자, 특수기호가 혼합되어야만 합니다.");
			$("input:password[name='pwd']").val("");
			$("input:password[id='pwd2']").val("");
			return;
		}


	});

};


function passwdFind_update_data(e) {
	const modal = $('#passwdFind'); // 모달 컨테이너 정의
	const pwd = modal.find("input[name='pwd']").val();
	const userid = modal.find("input:hidden[name='userid']").val();

	// AJAX 요청
	$.ajax({
		url: "/MovieProject/mypage/myuppwdEdit.mp",
		type: "post",
		data: {
			"pwd": pwd,
			"userid": userid
		},
		dataType: "json",
		success: function(json) {
			//console.log("서버 응답 성공:", json); // 서버 응답 확인
			if (json.n == 1) {
				alert("비밀번호가 변경되었습니다.");
				location.href = "/MovieProject/mypage/myupEdit.mp"; // 성공 시 리다이렉트 
			}
		},
		error: function(request, status, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}
	});
}
