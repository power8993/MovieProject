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
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>
<jsp:include page="/WEB-INF/header1.jsp" />
<script type="text/javascript" src="<%= ctxPath%>/js/movie/movieDetail.js"></script>


<div class="container">
    <div class="movie-header">
    	<div style="position: relative; width: 185px; height: 260px;">
        	<img src="https://i.namu.wiki/i/F9-VHNMvy23qFNV9moV7bsaQsSHzREVkry2ZZTCOhEdDXKoaYR3G89gpHwBh_YpR82DraNnHgUscE5_GQed9uPS2ADEKYc9nitowed_U4zuRyeE94P-4XbXbPKYj-Jlu0Ij-NRMX-Q5tZZa-QG48Pw.webp" alt="영화 포스터" class="movie-poster" style="border: 0px solid red; width:100%; height: 260px; display: block; position: absolute; top:0px; left: 0px;">
        	<i id="like" class="fa-solid fa-heart fa-bounce" style="color:#252422; position: absolute; top: 10px; right: 10px; z-index: 5; font-size: 20pt; "></i>
        </div>
        <div class="movie-details" style="margin: 0 15px">
            <div class="movie-title">${mvo.movie_title} </div>
            <div class="movie-info">
                <div><strong>예매율:</strong> 0%</div>
                <div><strong>감독:</strong> ${mvo.director}</div>
                <div><strong>배우:</strong> ${mvo.actor}</div>
                <div><strong>장르:</strong> ${mvo.cvo.category}</div>         
                <div><strong>상영시간:</strong> ${mvo.running_time} 분</div>
                <div><strong>개봉일:</strong> ${mvo.start_date}</div>
            </div>
            <!-- 예매하기 버튼 -->
            <button class="reservation-btn" onclick="">
                예매하기
            </button>
        </div>
    </div>

    <div class="synopsis">
        <strong>줄거리</strong>
        <p>${mvo.content}</p>
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


</body>
</html>