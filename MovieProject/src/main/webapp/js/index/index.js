/**
 * 
 */
 $(document).ready(function(){
 
 
 // === 카드에 마우스를 올렸을 시 상세보기,예매하기 버튼 생성=== //
$(".movieCard").hover(
    function () {
        // 마우스를 올렸을 때 실행
        const buttonsHtml = `
            <div id="movieDetailElmt" style="position: absolute; top: 45%; left: 50%; transform: translate(-50%, -50%); display: flex; flex-direction: column; gap: 10px;">
                <button id="movieDetailBtn">상세보기</button>
            </div>
            <div id="movieReservationElmt" style="position: absolute; top: 60%; left: 50%; transform: translate(-50%, -50%); display: flex; flex-direction: column; gap: 10px;">
            	<button id="movieReservationBtn">예매하기</button>
            </div>
        `;
        $(this).css("position", "relative").append(buttonsHtml);
    },
    function () {
        // 마우스를 뗐을 때 실행
        $(this).find("#movieDetailElmt").remove();
        $(this).find("#movieReservationBtn").remove();
    }
);



// === 카드의 버튼에 마우스를 올렸을 시 === //

 
 });// end of $(document).ready(function(){});---------------------
 
 //=== 카드의 버튼에 마우스 올릴 시 시작 ===//
 $(document).on("mouseenter", "#movieDetailBtn", function (e) {
     $(e.target).css("opacity", "0.1"); // .poster 요소를 50% 투명하게
});
$(document).on("mouseleave", "#movieDetailBtn", function () {
    $(".poster").css("opacity", ""); // 원래 상태로 복구
});


$(document).on("mouseenter", "#movieReservationBtn", function () {
    $(".poster").css("opacity", "0.5"); // .poster 요소를 50% 투명하게
});
$(document).on("mouseleave", "#movieReservationBtn", function () {
    $(".poster").css("opacity", ""); // 원래 상태로 복구
});
//=== 카드의 버튼에 마우스 올릴 시 끝 ===//

//== 상세보기 또는 예매하기 버튼에 마우스 올릴 시 시작 ==//

//== 상세보기 또는 예매하기 버튼에 마우스 올릴 시 끝 ==//



//예매하기 버튼 클릭 시
 $(document).on("click", "#movieReservationBtn", function () {
	
	var ctxPath = $("input[name='forJavaScript']").val();
	    console.log(ctxPath);
	const seq_showtime_no = $("input[name='seq_showtime_no']").val();
	console.log(seq_showtime_no);
	alert("seq_showtime_no : " + seq_showtime_no);
    location.href= ctxPath + "/reservation/reservation.mp?seq_movie_no="+seq_showtime_no;
});











