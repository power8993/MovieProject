function golike(element, seq_movie_no) {
    var isLiked = $(element).hasClass("liked");

    $.ajax({
        url: "/MovieProject/movie/movieLike.mp",
        data: {
            "seq_movie_no": seq_movie_no,
            "like": isLiked ? "remove" : "add"
        },
        type: "POST",
        dataType: "json",
        success: function(json) {
            if (json.status === "success") {
                if (isLiked) {
                    $(element).removeClass("liked").css({"color": ""});
                } else {
                    $(element).addClass("liked").css({"color": "#ff2626"});
                }
            } else {
                alert("문제가 발생했습니다: " + json.message);
            }
        },
        error: function(request, status, error) {
            console.log("에러 발생", error);
        }
    });
}
