$(document).ready(function() {


	mymovielikeHIT("1");

	$("button#btnmymovielike").click(function() {
		mymovielikeHIT($(this).val());
	});

});//end of $(document).ready(function(){});----

let lenHIT = 9;

//Fuction Declaration
function mymovielikeHIT(start) {

	$.ajax({
		url: "mymovielikeJSON.mp",
		//  type:"get",
		data: {
			"userid": $("input#userid").val(),
			"start": start,
			"len": lenHIT
		},
		dataType: "json",
		success: function(json) {
			//console.log("~~ 확인용 json =>", json);
			let v_html = ``;

			if (start == "1" && json.length == 0) {

				v_html = `기대되는 영화가 존재하지 않습니다.`;
				$("p.empty").html(v_html);
				$("#btnmymovielike").hide();
			}

			else if (json.length > 0) {

				$.each(json, function(index, item) {
					v_html += `	<div class="col-md-6 col-lg-4">
					            <div class="card mb-3 movielikecss">
					                <div class="poster_relative">
									<a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${item.FK_SEQ_MOVIE_NO}" >
					                   <img src="/MovieProject/images/admin/poster_file/${item.poster_file}" class="card-img-top">
					                </a>
									<button type="button" class="movielikeDelBtn" onclick="movielikeDel('${item.FK_SEQ_MOVIE_NO}')">X</button>
									</div>
									   <div class="movielikecss1" >
					                    <ul class="list-unstyled">
					                        <li><label class="prodInfo movietitle">${item.movie_title}</label></li>
					                        <li><label class="prodInfo">${item.start_date}</label></li>
					                        <li class="text-center">
					                            <a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${item.FK_SEQ_MOVIE_NO}" 
					                                class="btn" role="button">예매하기</a>
					                        </li>
										</ul>
										  
					                </div>
					            </div>
					        </div>`;
				});//end of each ---

				$("div#mymovielikeHIT").append(v_html);

				$("button#btnmymovielike").val(Number(start) + lenHIT);

				$("span#mymovielikecount").text(Number($("span#mymovielikecount").text()) + json.length);

				if ($("span#totalmymovielike").text() == $("span#mymovielikecount").text()) {
					$("button#btnmymovielike").hide(); //mymovielikecount 값과 totalmymovielike 값이 일치하는 경우 버튼 없앰.
				}
			}

		},
		error: function(request, status, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}

	});



}; // end of function---


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
