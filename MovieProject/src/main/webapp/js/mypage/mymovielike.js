$(document).ready(function() {


	mymovielikeHIT("1");

	$("button#btnmymovielike").click(function() {
		mymovielikeHIT($(this).val());
	});

});//end of $(document).ready(function(){});----

let lenHIT = 9;


function mymovielikeHIT(start) {
    $.ajax({
        url: "mymovielikeJSON.mp",
        data: {
            "userid": $("input#userid").val(),
            "start": start,
            "len": lenHIT
        },
        dataType: "json",
        success: function(json) {
            let v_html = ``;

            if (start == "1" && json.length == 0) {
                v_html = `기대되는 영화가 존재하지 않습니다.`;
                $("p.empty").html(v_html);
                $("#btnmymovielike").hide();
            }
            else if (json.length > 0) {
                $.each(json, function(index, item) {
      
                    let currentDate = new Date(); // 현재 날짜
                    let startDate = new Date(item.start_date); // 상영 시작 날짜
                    let endDate = new Date(item.end_date); // 상영 종료 날짜

                    // 버튼 텍스트와 상태 결정
                    let likeButtonType = "";
                    let buttonClass = "btn Detbto";
                    let isButtonDisabled = false;

                    if (currentDate < startDate) {
                        // 상영 시작 전 -> 상영 예정작 버튼
                        likeButtonType = `<a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${item.FK_SEQ_MOVIE_NO}" class="scheduled-movie-link">상영 예정작</a>`;
                    } else if (currentDate >= startDate && currentDate <= endDate) {
                        // 상영 중 -> 예매하기 버튼
                         likeButtonType = "예매하기";
                    } else {
                        // 상영 종료 후 -> 상영 종료
                        likeButtonType = "상영 종료";
                        isButtonDisabled = true;
                    }

                    v_html += `
                        <div class="col-md-6 col-lg-4">
                            <div class="mb-3 movielikecss">
                                <div class="poster_relative">
                                    <a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${item.FK_SEQ_MOVIE_NO}">
                                        <img src="/MovieProject/images/admin/poster_file/${item.poster_file}.jpg" alt="영화 포스터" class="card-img-top"/>
                                    </a>
                                    <button type="button" class="movielikeDelBtn" onclick="movielikeDel('${item.FK_SEQ_MOVIE_NO}')">X</button>
                                </div>
                                <div class="movielikecss1">
                                    <ul class="list-unstyled">
                                        <li><label class="prodInfo movietitle">${item.movie_title}</label></li>
                                        <li><label class="prodInfo">${item.start_date}</label></li>
                                        <input type="hidden" value="${item.end_date}" />
										<li class="text-center">
										    <button class="${buttonClass}" type="button likebtncss" onclick="window.location.href='/MovieProject/reservation/reservation.mp?seq_movie_no=${item.FK_SEQ_MOVIE_NO}'" ${isButtonDisabled ? 'disabled style="cursor: not-allowed; opacity: 0.6;"' : ''}>
										        ${likeButtonType}
										    </button>
										</li>

                                    </ul>
                                </div>
                            </div>
                        </div>`;
                });

                $("div#mymovielikeHIT").append(v_html);
                $("button#btnmymovielike").val(Number(start) + lenHIT);
                $("span#mymovielikecount").text(Number($("span#mymovielikecount").text()) + json.length);

                $("span#totalmymovielike").text(json.totalmymovielike);

                if (parseInt($("span#totalmymovielike").text()) === parseInt($("span#mymovielikecount").text())) {
                    $("button#btnmymovielike").hide(); // 남은 개수랑 전체 개수랑 맞으면 버튼 없앰
                }
            }
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
        }
    });
};




//특정 기대되는 영화 삭제할 때
function movielikeDel(FK_SEQ_MOVIE_NO) {
	const movietitle = $(event.target).parent().parent().find("label.movietitle").text();

	if (confirm(`${movietitle}을(를) 삭제하시겠습니까?`)) {
		$.ajax({
			url: "/MovieProject/mypage/mymovielikeDelete.mp",
			type: "post",
			data: { "FK_SEQ_MOVIE_NO": FK_SEQ_MOVIE_NO },
			dataType: "json",
			success: function(json) {
				if (json.n == 1) {
					location.href = "/MovieProject/mypage/mymovielike.mp";
				}
			},
			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
	} else {
		alert(`${movietitle}을(를) 삭제 취소하셨습니다.`);
	}
}
