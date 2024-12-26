
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

