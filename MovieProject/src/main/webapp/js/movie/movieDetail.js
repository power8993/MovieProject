$(document).ready(function(){
    // 좋아요 버튼 클릭 이벤트
    $("#like").click(function() {
        var isLiked = $(this).hasClass("liked"); // 만약 이미 좋아요 상태라면
		$(this).css({"color" : "#ff2626"});
        // AJAX 요청
        $.ajax({
            url: "/movie/movieLike.mp",
            data: {
                userId: user_Id,
                movieNo: seq_movie_no,
                like: isLiked ? "remove" : "add" // 좋아요 추가/제거
            },
            type: "POST",
            dataType: "json",
            success: function(response) {
                // 응답 성공 시
                if (response.status === "success") {
                    // 상태 변경 (예: 좋아요를 누른 상태와 안 누른 상태)
                    if (isLiked) {
                        $("#like").removeClass("liked").html('<i class="fa-solid fa-heart"></i>'); // 좋아요 취소
                    } else {
                        $("#like").addClass("liked").html('<i class="fa-solid fa-heart-broken"></i>'); // 좋아요 추가
                    }
                } else {
                    alert("문제가 발생했습니다: " + response.message);
                }
            },
            error: function(request, status, error) {
                alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
            }
        });
    });
});