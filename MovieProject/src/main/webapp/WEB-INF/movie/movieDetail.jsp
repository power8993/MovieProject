<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();   
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title><%= ctxPath %> 영화 상세보기</title>
    <style>
        body {
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            border-radius: 8px;
        }
        .movie-header {
            display: flex;
            margin-bottom: 20px;
        }
        .movie-poster {
            width: 200px;
            height: auto;
            margin-right: 30px;
        }
        .movie-details {
            flex: 1;
        }
        .movie-title {
            font-size: 32px;
            font-weight: bold;
            margin-bottom: 15px;
        }
        .movie-info {
            display: flex;
            flex-wrap: wrap;
            margin-bottom: 10px;
        }
        .movie-info div {
            width: 48%; /* 두 개씩 배치 */
            font-size: 16px;
            margin-bottom: 10px;
        }
        .synopsis {
            margin-top: 20px;
            font-size: 16px;
        }
        .rating-stars {
            font-size: 24px;
            margin-top: 20px;
        }
        .rating-stars span {
            cursor: pointer;
        }
        .rating-stars span.selected {
            color: gold;
        }
        .review-section {
            margin-top: 30px;
        }
        .review-section textarea {
            width: 100%;
            height: 120px;
            padding: 10px;
            margin-bottom: 10px;
            font-size: 16px;
            border-radius: 8px;
            border: 1px solid;
        }
        .review-section button {
            padding: 10px 20px;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }       
        .reviews-list {
            margin-top: 20px;
        }
        .reviews-list ul {
            list-style-type: none;
            padding: 0;
        }
        .reviews-list li {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
        }
        .review-item {
            margin-bottom: 10px;
        }
        .review-item .author {
            font-weight: bold;
            font-size: 14px;
        }
        .review-item .timestamp {
            font-size: 12px;
        }
        .review-item .content {
            font-size: 16px;
            margin-top: 5px;
        }
        .review-item .rating {
            font-size: 16px;
            color: gold; /* 별점 색상 */
            margin-top: 5px;
        }
        .reservation-btn {
            display: block;
            width: auto;
            padding: 8px 15px;
            color: white;
            font-size: 14px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            margin-top: 10px;
        }       
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container">
    <div class="movie-header">
        <img src="<%= ctxPath %>" alt="영화 포스터" class="movie-poster">
        <div class="movie-details">
            <div class="movie-title">타이틀<%= ctxPath %></div>
            <div class="movie-info">
                <div><strong>예매율:</strong> <%= ctxPath %>%</div>
                <div><strong>장르:</strong> <%= ctxPath %></div>
                <div><strong>감독:</strong> <%= ctxPath %></div>
                <div><strong>배우:</strong> <%= ctxPath %></div>
                <div><strong>상영시간:</strong> <%= ctxPath %></div>
                <div><strong>개봉일:</strong> <%= ctxPath %></div>
            </div>            
            <!-- 예매하기 버튼 -->
            <button class="reservation-btn" onclick="">
                예매하기
            </button>
        </div>
    </div>

    <div class="synopsis">
        <strong>줄거리</strong>
        <p><%= ctxPath%></p>
    </div>

    <!-- 후기 작성 -->
    <div class="review-section">       
        <!-- 별점 선택 -->
    	<div class="rating-stars">
    		<label for="reviewText">후기</label>
	        <span data-value="1">&#9733;</span>
	        <span data-value="2">&#9733;</span>
	        <span data-value="3">&#9733;</span>
	        <span data-value="4">&#9733;</span>
	        <span data-value="5">&#9733;</span>
    	</div>
        <textarea id="reviewText" placeholder="영화에 대한 후기를 작성해주세요..."></textarea><br>
        <button onclick="submitReview()">후기 제출</button>
    </div>
	
	<hr style="">
	
    <!-- 작성된 후기들 -->
    <div class="reviews-list">
        <ul id="reviewsList">
            <!-- 후기가 추가될 곳 -->
        </ul>
    </div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

<script>
    let selectedRating = 0; // 선택된 별점 값

    // 별점 선택
    const stars = document.querySelectorAll('.rating-stars span');
    stars.forEach(star => {
        star.addEventListener('click', () => {
            selectedRating = star.getAttribute('data-value'); // 선택한 별점 값 저장
            stars.forEach(s => s.classList.remove('selected'));
            for (let i = 0; i < selectedRating; i++) {
                stars[i].classList.add('selected');
            }
        });
    });

    // 후기 제출
    function submitReview() {
        const reviewText = document.getElementById('reviewText').value;
        if (reviewText.trim() === '') {
            alert('후기를 작성해주세요.');
            return;
        }
        if (selectedRating === 0) {
            alert('별점을 선택해주세요.');
            return;
        }

        const reviewsList = document.getElementById('reviewsList');
        const newReview = document.createElement('li');
        newReview.classList.add('review-item');

        // 예시로 임의의 작성자와 시간을 추가
        const author = document.createElement('div');
        author.classList.add('author');
        author.textContent = "작성자: 사용자";
        
        const timestamp = document.createElement('div');
        timestamp.classList.add('timestamp');
        timestamp.textContent = "2024-12-12 10:30"; // 시간 예시

        const content = document.createElement('div');
        content.classList.add('content');
        content.textContent = reviewText;

        // 별점 표시
        const rating = document.createElement('div');
        rating.classList.add('rating');
        rating.textContent = `별점: ${selectedRating} 점`;

        newReview.appendChild(author);
        newReview.appendChild(timestamp);
        newReview.appendChild(content);
        newReview.appendChild(rating);

        reviewsList.appendChild(newReview);
        document.getElementById('reviewText').value = ''; // 후기 입력란 비우기
        selectedRating = 0; // 별점 초기화
        stars.forEach(s => s.classList.remove('selected')); // 별점 초기화
    }
</script>

</body>
</html>
