/* 예매내역 영수증 출력 */
function Receipt_Printing(imp_uid,ctxPath){

		const width = 550;
		const height = 700;
	 
		 const left = Math.ceil( (window.screen.width - width)/2 ); // 정수로 만듬
		 const top = Math.ceil( (window.screen.height - height)/2 ); // 정수로 만듬
		 
		 const url = `${ctxPath}/mypage/myreservationReceipt.mp?imp_uid=${imp_uid}`;      
	 
		 window.open(url, "Receipt_Printing",
					`left=${left}, top=${top}, width=${width}, height=${height}`);
}//end of function Receipt_Printing(ctxPath, userid)---

