$(document).ready(function() {

	$("span#totalmymovielike").hide();
	$("span#mymovielikecount").hide();

	mymovielikeHIT("1");

	$("button#btnmymovielike").click(function() {
		if ($(this).text() == "처음으로") {
			$("div#mymovielikeHIT").empty();
			$("span#end").empty();
			mymovielikeHIT("1");
			$(this).text("더보기...");
		} else {
			mymovielikeHIT($(this).val());
		}
	});

});//end of $(document).ready(function(){});----

let lenHIT = 9;

//Fuction Declaration
function mymovielikeHIT(start) {	

	$.ajax({
		url: "mymovielikeJSON.mp",
		//  type:"get",
		data: { "userid": $("input#userid").val(),
		"start": start, 	
		"len": lenHIT
	},	
		dataType: "json",
		success: function(json) {
			//console.log("~~ 확인용 json =>", json);
			let v_html = ``;

			if (start == "1" && json.length == 0) {
				
				v_html = `기대되는 영화가 존재하지 않습니다.`;
				$("div#mymovielikeHIT").html(v_html);
			}

			else if (json.length > 0) {
			
				$.each(json, function(index, item) {
					v_html += `	<div class="col-md-6 col-lg-4">
					            <div class="card mb-3 movielikecss">
					                <div class="poster_relative">
					                   <img src="/MovieProject/images/admin/poster_file/${item.poster_file}" class="card-img-top">
					                </div>
					                <div class="movielikecss1" >
					                    <ul class="list-unstyled">
					                        <li><label class="prodInfo mylike">${item.movie_title}</label></li>
					                        <li><label class="prodInfo mylike">${item.start_date}</label></li>
					                        <li class="text-center">
					                            <a href="/MovieProject/movie/movieDetail.mp?seq_movie_no=${item.FK_SEQ_MOVIE_NO}" 
					                                class="btn stretched-link mybtnlike" role="button">예매하기</a>
					                        </li>
										</ul>
										
					                </div>
					            </div>
					        </div>`;
				});//end of each ---

				$("div#mymovielikeHIT").append(v_html);

				$("button#btnmymovielike").val(Number(start) + lenHIT);
				
				$("span#mymovielikecount").text(Number($("span#mymovielikecount").text()) + json.length);

				if ($("span#mymovielikecount").text() == $("span#totalmymovielike").text()) {
					$("button#btnmymovielike").text("처음으로");
					$("span#mymovielikecount").text("0");
				}


			}

		},
		error: function(request, status, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}
	
});



}; // end of function---