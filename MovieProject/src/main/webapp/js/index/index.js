/**
 * 
 */
 $(document).ready(function(){
 
 
 // === 카드에 마우스를 올렸을 시 상세보기,예매하기 버튼 생성=== //
$(".movieCard").hover(
    function () {
        // 카드에 마우스를 올렸을 때
        const buttonsHtml = `
            <div id="movieDetailElmt" style="position: absolute; top: 45%; left: 50%; transform: translate(-50%, -50%); display: flex; flex-direction: column; gap: 10px;">
                <button type="submit" id="movieDetailBtn">상세보기</button>
            </div>
            <div id="movieReservationElmt" style="position: absolute; top: 60%; left: 50%; transform: translate(-50%, -50%); display: flex; flex-direction: column; gap: 10px;">
                <button type="button" id="movieReservationBtn">예매하기</button>
            </div>
        `;

        if ($(this).find("#movieDetailElmt").length == 0) {
            // 버튼이 이미 생성되지 않았다면 추가
            $(this).css("position", "relative").append(buttonsHtml);
        }
        // 카드 이미지 투명도 적용
        $(this).find(".poster").css("filter", "brightness(50%)");
        $(this).find(".movieRank").hide();
        $(this).find(".movieGradeChoose").hide();
        $(this).find(".remaining_day").hide();
    },
    function () {
        // 카드에서 마우스를 뗐을 때
        $(this).find("#movieDetailElmt").remove();
        $(this).find("#movieReservationElmt").remove();
        // 투명도 원래 상태로 복구
        $(this).find(".movieRank").show();
        $(this).find(".movieGradeChoose").show();
        $(this).find(".remaining_day").show();
        $(this).find(".poster").css("filter", "");
    }
);

	
	
});// end of $(document).ready(function(){});---------------------




////////////////////Function Declare////////////////////

//"예고편영상"의 상세보기 클릭 시
$(document).on("click", "#trailerShowDetailBtn", function () {
	const ctxPath = $("input[name='forJavaScript']").val();
    
    location.href= ctxPath + "/movie/movieDetail.mp?seq_movie_no="+144;
});

//////////////////동영상 컨트롤러 커스텀 시작//////////////////
document.addEventListener("DOMContentLoaded", function () {
    const video = document.getElementById("customVideo");
    const playPauseBtn = document.getElementById("playPauseBtn");

    // 재생/일시정지
    playPauseBtn.addEventListener("click", function () {
        if (video.paused) {
            video.play();
            playPauseBtn.innerHTML = '<i class="fa-solid fa-pause" style="color:white"></i>'; // 일시정지 아이콘
        } else {
            video.pause();
           playPauseBtn.innerHTML = '<i class="fa-solid fa-play"  style="color:white"></i>'; // 재생 아이콘
        }
    });
     // 2. 볼륨 켜기/끄기 버튼
    muteBtn.addEventListener("click", function () {
    video.muted = !video.muted; // 음소거 상태를 토글
    muteBtn.innerHTML = video.muted
        ? '<i class="fa-solid fa-volume-xmark" style="color:white"></i>' // 음소거 아이콘
        : '<i class="fa-solid fa-volume-high" style="color:white"></i>'; // 볼륨 켜짐 아이콘
	});

});
//////////////////동영상 컨트롤러 커스텀 끝//////////////////

 

//예매하기 버튼 클릭 시
 $(document).on("click", "#movieReservationBtn", function () {
	
	const ctxPath = $("input[name='forJavaScript']").val();
	
	// 현재 클릭된 버튼에서 가장 가까운 .card 요소 안의 seq_showtime_no 값을 가져옴
    const seq_movie_no = $(this).closest(".card").find("input[name='seq_movie_no']").val();
    
    location.href= ctxPath + "/reservation/reservation.mp?seq_movie_no="+seq_movie_no;
	 // 투명도 원래 상태로 복구
    $(this).closest(".card").find(".poster").css("opacity", "");
});

//상세보기 버튼 클릭 시
 $(document).on("click", "#movieDetailBtn", function() {
    //const ctxPath = $("input[name='forJavaScript']").val();
    const form = $(this).closest("form")[0]; // 해당 버튼의 부모 폼을 찾음. 폼이 두개라서 클릭 한 버튼의 폼을 찾아옴

    // 투명도 원래 상태로 복구
    $(this).closest(".card").find(".poster").css("opacity", "");
    form.submit();
});


//공지사항의 제목 클릭 시
 $(document).on("click", ".notice_subject", function() {
    const ctxPath = $("input[name='forJavaScript']").val();
	
    const seq_notice_no = $(this).closest("td").find("input[name='seq']").val();
	location.href= ctxPath + "/notice/noticeDetail.mp?seq="+seq_notice_no;
});







///////////////////// Top 버튼 /////////////////////
const scrollTop = function () {
      // create HTML button element
      const scrollBtn = document.createElement("button");
      scrollBtn.innerHTML = '<span style="color: #403d39; font-size:12pt">TOP</span>';;
      scrollBtn.setAttribute("id", "scroll-btn");
      document.body.appendChild(scrollBtn);
      // hide/show button based on scroll distance
      const scrollBtnDisplay = function () {
        window.scrollY > 200
          ? scrollBtn.classList.add("show")
          : scrollBtn.classList.remove("show");
      };
      window.addEventListener("scroll", scrollBtnDisplay);
      // scroll to top when button clicked
      const scrollWindow = function () {
        if (window.scrollY != 0) {
          setTimeout(function () {
            window.scrollTo(0, window.scrollY - 50);
            scrollWindow();
          }, 10);
        }
      };
      scrollBtn.addEventListener("click", scrollWindow);
    };
    scrollTop();






 











