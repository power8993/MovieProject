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
