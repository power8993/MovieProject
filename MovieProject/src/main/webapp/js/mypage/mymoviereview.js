function mymoviereview_delete(event, seq_review_no) {
	// 영화 제목 가져오기
	const movietitle = $(event.target).parent().parent().find("strong.movie_title").text();

	if (confirm(`${movietitle}의 평점을 삭제하시겠습니까?`)) {
		// AJAX 요청
		$.ajax({
			url: "/MovieProject/mypage/mymoviereviewDelete.mp",
			type: "post",
			data: { "seq_review_no": seq_review_no },
			dataType: "json",
			success: function(json) {
				if (json.n == 1) {
					location.href = "/MovieProject/mypage/mymoviereview.mp"; // 성공 시 리다이렉트
				}
			},
			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
	} else {
		alert(`${movietitle}의 평점 삭제를 취소하셨습니다.`);
	}
}



function mymoviereview_update(seq_review_no) {
    const mymoviereview = $("div#mymoviereview_update_modal"); // 모달을 넣을 위치
    const movie_title = $(`#movie_title_${seq_review_no}`).text(); // 영화 제목
    const movie_rating = $(`#movie_rating_${seq_review_no}`).data('rating'); // 기존 평점 (data-rating 속성에서 가져옴)
    const review_content = $(`#review_content_${seq_review_no}`).text(); // 기존 리뷰 내용

    const modal_popup = `
    <div class="modal fade" id="mymoviereview_find" tabindex="-1" aria-labelledby="mymoviereview_find" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <!-- Modal Header -->
                <div class="modal-header">
                    <h3 class="modal-title" id="mymoviereview_find">평점수정</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <!-- Modal Body -->
                <div class="modal-body">
                    <h2>${movie_title}</h2>
                    <form name="review_contentFrm">
                        <div class="div_review_content" style="text-align: center;">
                            <input type="hidden" name="seq_review_no" value="${seq_review_no}" />
                            <input type="hidden" name="movie_rating" id="movie_rating" value="${movie_rating}" />
							<div id="movie_rating_input">
                            <!-- 별점 표시 및 수정 -->
                            <i class="fa-solid fa-star" data-rating="1" onclick="setRating(this)"></i>
                            <i class="fa-solid fa-star" data-rating="2" onclick="setRating(this)"></i>
                            <i class="fa-solid fa-star" data-rating="3" onclick="setRating(this)"></i>
                            <i class="fa-solid fa-star" data-rating="4" onclick="setRating(this)"></i>
                            <i class="fa-solid fa-star" data-rating="5" onclick="setRating(this)"></i>
                        </div>
                            <input type="text" name="review_content" value="${review_content}" />
                            
                            
                        </div>
                    </form>
                </div>
                <!-- Modal Footer -->
                <div class="modal-footer">
                    <button type="button" class="btn myreview_update">작성완료</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>`;

    mymoviereview.html(modal_popup); // 모달 HTML 삽입

    // 모달 표시
    $('#mymoviereview_find').modal('show'); // 모달 띄우기

    // 수정 완료 버튼 클릭
    $('button.myreview_update').on('click', function(e) {
        myreview_update_data(e, seq_review_no);
    });

    // 엔터 키에 대해서 기본 폼 제출 방지
    $("input[name='review_content']").keydown(function(e) {
        if (e.keyCode === 13) {
            e.preventDefault(); // 기본 폼 제출 방지
            $('button.myreview_update').click(); // 작성완료 버튼 클릭
        }
    });

    $("#movie_rating_input i").each(function() {
        const starRating = $(this).data("rating");
        if (starRating <= movie_rating) {
            $(this).css("color", "#eb5e28"); 
        } else {
            $(this).css("color", "#E0E0E0"); 
        }
    });
}


function setRating(star) {
    const rating = $(star).data("rating"); 
    $("#movie_rating").val(rating);  

    $("#movie_rating_input i").each(function() {
        const starRating = $(this).data("rating");  
        if (starRating <= rating) {
            $(this).css("color", "#eb5e28"); 
        } else {
            $(this).css("color", "#E0E0E0");  
        }
    });
}




function myreview_update_data(e, seq_review_no) {
    const modal = $('#mymoviereview_find'); // 모달 컨테이너 정의
    const movie_rating = modal.find("input[name='movie_rating']").val();
    const review_content = modal.find("input[name='review_content']").val();

    if (!review_content.trim()) {
        alert('공백만 입력할 수 없습니다.');
        e.preventDefault();
    }

    // AJAX 요청
    $.ajax({
        url: "/MovieProject/mypage/mymoviereviewUpdate.mp",
        type: "post",
        data: {
            "seq_review_no": seq_review_no,
            "movie_rating": movie_rating,
            "review_content": review_content
        },
        dataType: "json",
        success: function(json) {
            if (json.n == 1) {
                location.href = "/MovieProject/mypage/mymoviereview.mp"; // 성공 시 리다이렉트
            }
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
        }
    });
}
