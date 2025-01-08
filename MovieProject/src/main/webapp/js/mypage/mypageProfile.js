/* 프로필 사진 편집 */
function profile_Edit(userid, ctxPath) {

	const width = 550;
	const height = 550;

	const left = Math.ceil((window.screen.width - width) / 2); // 정수로 만듬
	const top = Math.ceil((window.screen.height - height) / 2); // 정수로 만듬

	const url = `${ctxPath}/mypage/mypageProfileEdit.mp?userid=${userid}`;

	window.open(url, "Receipt_Printing",
		`left=${left}, top=${top}, width=${width}, height=${height}`);

	// 팝업 객체를 부모창에 저장하여 나중에 제어할 수 있도록 함
	window.popupWindow = popupWindow;

}//end of function Receipt_Printing(ctxPath, userid)---
