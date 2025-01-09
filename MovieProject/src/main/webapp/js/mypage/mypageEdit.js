function lastpwdchangedate() {
    $.ajax({
        url: "mylastpwdchangedateJSON.mp",  // 서버 URL
        data: { "userid": $("input#userid").val() },  // 사용자 ID를 서버에 전송
        dataType: "json",  // 응답 데이터 타입
		success: function(json) {
		    console.log("Received JSON: ", json);
		    if (json.pwdChangeDate) {
		        $("div#lastpwdchangedate").text(json.lastpwdchangedate);
		    } else {
		        $("div#lastpwdchangedate").text("비밀번호 변경 정보 없음");
		    }
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
        }
    });
};


let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_email_change = false;
// 이메일값을 변경했는지 여부를 알아오기 위한 용도

$(document).ready(function() {

	$("span.error").hide();

	$("input#name").blur((e) => {

		const name = $(e.target).val().trim();
		if (name == "") {

			$("table#tblMemberEdit :input").prop("disabled", true);
			$(e.target).prop("disabled", false);

			$(e.target).parent().find("span.error").show();
			$(e.target).val("").focus();

		}
		else {
			$("table#tblMemberEdit :input").prop("disabled", false);

			$(e.target).parent().find("span.error").hide();
		}

	});


	$("input#email").blur((e) => {

		const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);

		const bool = regExp_email.test($(e.target).val());

		if (!bool) {
			// 이메일이 정규표현식에 위배된 경우 

			$("table#tblMemberEdit :input").prop("disabled", true);
			$(e.target).prop("disabled", false);

			$(e.target).parent().find("span.error").show();
			$(e.target).val("").focus();
		}
		else {
			$("table#tblMemberEdit :input").prop("disabled", false);

			$(e.target).parent().find("span.error").hide();
		}

	});


	$("input#hp2").blur((e) => {

		const regExp_hp2 = new RegExp(/^[1-9][0-9]{3}$/);

		const bool = regExp_hp2.test($(e.target).val());

		if (!bool) {

			$("table#tblMemberEdit :input").prop("disabled", true);
			$(e.target).prop("disabled", false);

			$(e.target).parent().find("span.error").show();

			$(e.target).val("").focus();
		}
		else {
			$("table#tblMemberEdit :input").prop("disabled", false);

			$(e.target).parent().find("span.error").hide();
		}

	});


	$("input#hp3").blur((e) => {

		const regExp_hp3 = new RegExp(/^\d{4}$/);

		const bool = regExp_hp3.test($(e.target).val());

		if (!bool) {

			$("table#tblMemberEdit :input").prop("disabled", true);
			$(e.target).prop("disabled", false);

			$(e.target).parent().find("span.error").show();

			$(e.target).val("").focus();
		}
		else {

			$("table#tblMemberEdit :input").prop("disabled", false);

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
				if (json.isExists) {
					$("span#emailCheckResult").html($("input#email").val() + " 은 이미 사용중 이므로 다른 이메일을 입력하세요").css({ "color": "#eb5e28" });
					$("input#email").val("");
				}
				else {
					$("span#emailCheckResult").html($("input#email").val() + " 은 사용가능 합니다").css({ "color": "navy" });
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

	const modal_popup = `
	<div class="modal fade" id="passwdFind" tabindex="-1" aria-labelledby="passwdFindLabel" aria-hidden="true">
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
					         <div class="div_pwd" style="text-align: center;">
					            <span style="color: blue; font-size: 12pt;">새암호</span><br/> 
					            <input type="password" name="pwd" size="25" />
					         </div>
					         
					         <div class="div_pwd" style="text-align: center;">
					              <span style="color: blue; font-size: 12pt;">새암호확인</span><br/>
					            <input type="password" id="pwd2" size="25" />
					         </div>
					         
					         <input type="hidden" name="userid" value="${userid}" />
					   
					         <div style="text-align: center;">
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
	
	// 모달을 열 때 aria-hidden과 inert 설정
	  $('#passwdFind').on('shown.bs.modal', function () {
	      $(this).attr('aria-hidden', 'false').removeAttr('inert');
	      // 모달이 열릴 때 첫 번째 포커스 가능한 요소로 포커스를 이동
	      $(this).find('button:first').focus(); // 여기서 첫 번째 버튼에 포커스를 이동
	  });

	  // 모달을 닫을 때 aria-hidden과 inert 설정 및 포커스 관리
	  $('#passwdFind').on('hidden.bs.modal', function () {
	      $(this).attr('aria-hidden', 'true').attr('inert', 'true');
	      // 모달을 닫은 후 포커스를 원래 위치로 되돌림 (예시: 모달을 여는 버튼)
	      $('#openModalButton').focus(); // #openModalButton은 모달을 여는 버튼 ID로 가정
	  });

	// 모달을 표시
	$('#passwdFind').modal('show'); // 모달 띄우기

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
		
		// 실제 AJAX 요청 전에 로그로 확인
		   console.log("AJAX 요청 보내기", {
		       pwd: pwd,
		       userid: $("input:hidden[name='userid']").val()
		   });
		
		$.ajax({
			url: "/mypage/myuppwdEdit.mp",
			type: "post",
			data: {
				pwd: pwd,
				userid: $("input:hidden[name='userid']").val()
			},
			dataType: "json",
			success: function(json) {
				console.log("서버 응답 성공:", json); // 서버 응답 확인
				if (json.n == 1) {
					$('#passwdFind').modal('hide'); // 모달 닫기
					alert("비밀번호가 변경되었습니다.");
				}
			},
			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
	});

}


