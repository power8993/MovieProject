
/*// 리뷰 삭제 함수
window.deleteReview = function(reviewId) {
	$.ajax({
		url: '/MovieProject/deleteReview', // 리뷰 삭제 엔드포인트
		method: 'POST',
		data: { seq_review_no: reviewId },
		success: function(response) {
			// 삭제 후, 리뷰 목록 다시 로드
			loadMovieReviews();
		},
		error: function(error) {
			console.error('리뷰 삭제 실패:', error);
		}
	});
};*/