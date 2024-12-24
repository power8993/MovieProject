function golike(element, seq_movie_no) {
    // 현재 좋아요 상태를 확인 (클래스가 'liked'인지를 체크)
    var isLiked = $(element).hasClass("liked");

	console.log(isLiked);
	console.log(seq_movie_no);
	
    // AJAX 요청을 보낼 때, 현재 좋아요 상태에 따라 'add' 또는 'remove' 값을 전송
    $.ajax({
        url: "/MovieProject/movie/movieLike.mp",  // 서버로 요청을 보낼 URL
        data: {
            "seq_movie_no": seq_movie_no,   // 영화 번호
            "like": isLiked ? "remove" : "add" // 'remove' 또는 'add' 상태
        },
        type: "POST",  // POST 방식으로 요청
        dataType: "json",  // 서버 응답을 JSON 형식으로 받음
        success: function(json) {
            // 서버 응답이 성공일 경우
            if (json.status === "success") {
                // 좋아요 상태 변경 (클릭한 아이콘의 클래스와 아이콘 변경)
                if (isLiked) {
                    // 좋아요 취소 (클래스 제거, 아이콘 변경)
					$(element).removeClass("liked").css({"color":""});
                } else {
                    // 좋아요 추가 (클래스 추가, 아이콘 변경)
					$(element).addClass("liked").css({"color":"#ff2626"});
                }
            } else {
                // 서버에서 문제가 발생했을 때
                alert("문제가 발생했습니다: " + json.message);
            }
        },
        error: function(request, status, error) {
            // AJAX 요청이 실패했을 경우
            alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
        }
    });
}
