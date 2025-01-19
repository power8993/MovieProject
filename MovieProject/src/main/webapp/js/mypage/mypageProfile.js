/* 프로필 사진 편집 */
function profile_Edit(userid, ctxPath) {

	const width = 550;
	const height = 550;

	const left = Math.ceil((window.screen.width - width) / 2); // 정수로 만듬
	const top = Math.ceil((window.screen.height - height) / 2); // 정수로 만듬

	const url = `${ctxPath}/mypage/mypageProfileEdit.mp?userid=${userid}`;

	window.open(url, "Receipt_Printing",
		`left=${left}, top=${top}, width=${width}, height=${height}`);


}//end of function Receipt_Printing(ctxPath, userid)---



$(document).ready(function() {
	  var contextPath = $("#lastprofile_Edit").data("context-path"); //전체 경로 정의

	$.ajax({
		url: "mylastprofileEditJSON.mp",  // 서버 URL
		dataType: "json",  // 응답 데이터 타입
		success: function(json) {
			//console.log(json);
			var imagePath = contextPath + "/Profile_image/" + json.profile; // 전체경로+이미지경로+user 이미지 이름
			//console.log("프로필 이미지 경로: " + imagePath);
			$("#profileimage").attr("src", imagePath);
		},
		error: function(request, status, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}
	});
}); 
