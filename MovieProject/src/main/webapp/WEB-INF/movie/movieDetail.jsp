<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            font-size: 16px;
            margin-bottom: 10px;
        }
        .synopsis {
            border: 0px solid black;
            margin-top: 20px;
            font-size: 16px;
            width: 95%;
            margin: 0 auto;
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
        
		.star.filled {
		    color: gold;  /* 채워진 별 색상 */
		}
        .review-section {
            margin-top: 20px;
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
		    text-decoration: none;
		    border-radius: 2px;
		    border: 1px solid #ddd;
		    color: #403D39;
		    background-color: #fff;
        }
        
        .review-section button:hover {
        	background-color: #eb5e28;
		    color: #403D39;
        }
          
        .reviews-list {
		    width: 95%;
		    margin: 0 auto;
		    margin-top: 10px;
        }
        
        .reviews-list ul {
            list-style-type: none;
		    padding: 0;
		    margin: 0;
		    display: flex;
		    flex-wrap: wrap;  /* 항목을 여러 줄로 배치 */		    
        }
        
        .reviews-list li {
            width: 48%;  /* 각 항목의 너비를 48%로 설정 */
		    padding: 15px;
		    border-radius: 5px;
		    border: 1px solid #ddd;
		    margin: 0 auto;
		    margin-bottom: 15px;
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
            color: gold;
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

        .graph {
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
        }

        .graph li {
            list-style: none;
            width: 48%;
        }

        .chart {
            width: 100%;
            height: 160px;
        }

		.v-line{
		 border-left: thick solid #000;
		 height:100%;
		 left: 50%;
		 position: absolute;
		}

		.pagination {
		    display: flex;  
		    justify-content: center;  /* 수평 중앙 정렬 */
		    margin-top: 20px;  /* 상단 여백 추가 */
		}
		.pagination a {
		    padding: 10px 20px;
		    margin: 0 3px;  /* 각 페이지 링크 사이에 간격을 추가 */
		    text-decoration: none;
		    border-radius: 5px;
		    border: 1px solid #ddd;
		    color: #403D39;
		}
		
		.pagination a:hover {
		    background-color: #eb5e28;
		    color: #403D39;
		}
		
		.pagination .page-item.active .page-link {
		    background-color: #eb5e28;
		    border-color: #EB5E28;
		    color: white;
		}
    </style>

</head>
<body>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>
<script type="text/javascript" src="<%= ctxPath%>/js/movie/movieDetail.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<jsp:include page="/WEB-INF/header1.jsp" />

<script type="text/javascript">
    $(document).ready(function() {
    	
    	// 별점 클릭 이벤트
        $(".rating-stars span").click(function() {
            var selectedRating = $(this).data("value");
            $(".rating-stars span").removeClass("selected");
            $(this).prevAll().addClass("selected");
            $(this).addClass("selected");

            $("#rating").val(selectedRating);
        });

        $(".rating-stars span").click(function() {
            var rating = $(this).data("value");
            $("#rating").val(rating); // 이 부분에서 #rating의 값을 설정
        });
    	
    	// 페이지가 로드될 때 좋아요 상태를 확인하는 함수
        $.ajax({
            url: "/MovieProject/movie/movieDetail.mp",
            type: "POST",
            dataType: "json",
            data: {
                "seq_movie_no": ${mvo.seq_movie_no}  // JSP에서 Java 변수를 출력
            },
            success: function(json) {
                // 좋아요 상태에 따라 하트 아이콘 색상 변경
                if (!json.isLiked) {  // 좋아요가 아니라면
                    $("#like").removeClass("liked").css({"color": ""});
                } 
                else {  
                    $("#like").addClass("liked").css({"color": "#ff2626"});
                }
            },
            error: function(request, status, error) {
                alert("에러 발생: " + error);  // 에러 메시지를 더 자세히 표시
            }
        });
    	
    	// 초기 로드 시 페이지 1을 요청
		loadReviews(1);  // 첫 번째 페이지 로드
    	
    	// 페이지 바에서 특정 페이지를 클릭하면 해당 페이지 로드
		$(document).on("click", ".page-link", function(event) {
		    event.preventDefault();
		    var pageNo = $(this).data("page");  // data-page 속성에서 페이지 번호를 가져옴
		    loadReviews(pageNo);  // 해당 페이지 번호로 리뷰 목록 로드
		});

    	function loadReviews(pageNo) {
    	    $.ajax({
    	        url: "/MovieProject/movie/reviwDetail.mp",
    	        type: "POST",
    	        dataType: "json",
    	        data: {
    	            "seq_movie_no": ${mvo.seq_movie_no},
    	            "currentShowPageNo": pageNo  // 페이지 번호를 추가
    	        },
    	        success: function(json) {
    	        	console.log(json);
    	            const mrList = json.mrList;
    	            var reviewList = $("#reviewsList");
    	            reviewList.empty();  // 이전 리뷰 목록을 초기화

    	            if (mrList.length > 0) {
    	                mrList.forEach(mrList => {
    	                    var stars = "";
    	                    for (var i = 1; i <= 5; i++) {
    	                        if (i <= mrList.movie_rating) {
    	                            stars += '<span class="star filled">★</span>';
    	                        } else {
    	                            stars += '<span class="star">★</span>';
    	                        }
    	                    }

    	                    var reviewHtml = `<li>
					                             <div class="reviewuser">
					                                 <span class="author"> 별점: \${stars}</span><br>
					                                 <p class="content"> \${mrList.review_content}</p><br>
					                                 <span class="date"> 작성자: \${mrList.userid} \${mrList.review_write_date}</span><br>
					                             </div>
						                      </li>`;
    	                    reviewList.append(reviewHtml);
    	                });
    	            }

    	            // 페이지 바 업데이트
    	            $("#pageBar").html(json.pageBar);   	            
    	        },
    	        error: function(request, status, error){
    	            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
    	        }
    	    });
    	}
	
        var genderCtx = document.getElementById('genderChart').getContext('2d');
        var genderChart = new Chart(genderCtx, {
            type: 'pie',
            data: {
                labels: ['남성', '여성'],
                datasets: [{
                    label: '성별 예매 분포',
                    data: [60, 40],
                    backgroundColor: ['#36A2EB', '#FF6384'],
                    hoverOffset: 4
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                    	 display: false,
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ' + tooltipItem.raw + '%';
                            }
                        }
                    }
                }
            }
        });

        var ageCtx = document.getElementById('ageChart').getContext('2d');
        var ageChart = new Chart(ageCtx, {
            type: 'bar',
            data: {
                labels: ['10대', '20대', '30대', '40대', '50대 이상'],
                datasets: [{
                    label: '',
                    data: [15, 40, 25, 10, 10],
                    backgroundColor: 'Navy',
                    borderColor: 'Navy',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        beginAtZero: true
                    },
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                	 legend: {
                         display: false
                     },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ' + tooltipItem.raw + '명';
                            }
                        }
                    }
                }
            }
        });

    });

    // 후기 작성하는 json
    function submitReview() {
    	
    	var rating = $("#rating").val();  // 선택된 별점
        var review = $("#reviewText").val().trim();  // 리뷰 내용
		
        if(${not empty sessionScope.loginuser}) {
        	
        	// 1. 리뷰 내용이 비어 있거나 별점이 선택되지 않았을 경우
            if (review === "" || !rating || rating === "0") {
                alert("별점과 후기내용을 모두 작성하여야 합니다.");  // 둘 다 작성되지 않았을 때 경고 메시지
                return;  // 함수 종료
            }

            // 2. 리뷰 내용이 50글자 이하인지 확인
            if (review.length > 50) {
                alert("후기 내용은 50글자 이하이어야 합니다.");
                return;  // 50글자 미만이면 함수 종료
            }
        	
	        // AJAX 요청을 보내서 리뷰 데이터를 서버에 제출
	        $.ajax({
	            url: "/MovieProject/movie/moviereview.mp",  // 리뷰를 처리하는 서블릿 URL
	            type: "POST",
	            dataType: "json",
	            data: {
	                "seq_movie_no": ${mvo.seq_movie_no},  // 영화 번호
	                "rating": rating,  // 별점
	                "review": review  // 리뷰 내용
	            },
	            success: function(json) {
	            	if (json.n == 1) {
	            		
	                    alert("리뷰가 성공적으로 제출되었습니다.");  // 리뷰 제출 성공 메시지

	                    var reviewList = $("#reviewsList");
	                    
	                    var stars = "";
	                    
	                    for (var i = 1; i <= 5; i++) {
	                        if (i <= json.review.movie_rating) {
	                            stars += '<span class="star filled">★</span>';
	                        } else {
	                            stars += '<span class="star">★</span>';
	                        }
	                    }
	                    
	                    var reviewHtml = `<li>
	                                         <div class="reviewuser">
	                                         	 <span class="author">별점: \${stars}</span><br>
	                                             <p class="content"> \${json.review.review_content}</p><br>
	                                             <span class="date"> 작성자: \${json.review.user_id} \${json.review.review_write_date}</span><br>
	                                         </div>
	                                     </li>`;
	                    
	                    reviewList.prepend(reviewHtml); // prepend 맨위로 생성된다.
	                    
	                    // 리뷰 제출 후 입력 필드 초기화
	                    $("#rating").val("0");  // 별점 초기화
	                    $("#reviewText").val("");  // 후기 내용 초기화
	                    
	                    $(".rating-stars span").removeClass("selected");
	                } 
	                else if (json.n == 2) {
	                    alert("결제된 회원만 후기를 작성할 수 있습니다.");
	                }	                
	                else {
	                	alert("후기 작성을 실패 하셨습니다.");
	                }
	            },
	            error: function(request, status, error){
	                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	            }
	        });
        }
        else {
        	alert("로그인 후 리뷰를 작성할 수 있습니다.");
        }
    }

</script>

<div class="container">
    <div class="movie-header">
        <div style="position: relative; width: 185px; height: 260px;">
            <img src="<%= ctxPath %>/images/admin/poster_file/미니언즈.jpg" alt="영화 포스터" class="movie-poster" style="border: 0px solid red; width:100%; height: 260px; display: block; position: absolute; top:0px; left: 0px;">
            <i id="like" class="fa-solid fa-heart fa-bounce" onclick="golike(this, ${mvo.seq_movie_no})"  style="color:${isLiked ? '#ff2626' : '#252422'}; position: absolute; top: 10px; right: 10px; z-index: 5; font-size: 20pt;"></i>
        </div>
        <div class="movie-details" style="margin: 0 15px">
            <div class="movie-title" style="color:#eb5e28;"><img src="<%= ctxPath%>/images/admin/movie_grade/${mvo.movie_grade}.png" alt="${mvo.movie_grade}" style="width:35px; height:auto; margin-right: 10px; margin-bottom: 10px;">${mvo.movie_title} </div>
            <div class="movie-info">
                <div style="border-bottom: solid 2px #ccc5b9; padding-bottom: 10px; width: 100%;"><strong>예매율:</strong> <%= request.getParameter("bookingRate")%>%</div>             
                <div style="width: 20%;"><strong>감독:</strong> ${mvo.director}</div>
                <div style="width: 75%;"><strong>배우:</strong> ${mvo.actor}</div>
                <div style="width: 20%;"><strong>장르:</strong> ${mvo.cvo.category}</div>         
                <div style="width: 35%;"><strong>상영시간:</strong> ${mvo.running_time} 분</div>
                <div style="width: 32%;"><strong>개봉일:</strong> ${mvo.start_date}</div>
            </div>
            <!-- 예매하기 버튼 -->
            <button class="reservation-btn">
                <a href="<%= ctxPath %>/reservation/reservation.mp?seq_movie_no=${mvo.seq_movie_no}">예매하기</a>
            </button>
        </div>
    </div>

    <div class="synopsis">
        <div style="margin-bottom: 20px; color: #eb5e28;">
            <strong>영화정보</strong>
        </div>
        <p>${mvo.content}</p>
    </div>

    <hr style="width: 95%; border: 1.5px solid #ccc5b9; margin-bottom: 30px;">
    
    <div class="embed-responsive" style="margin-top: 30px; margin-bottom: 30px; width: 95%; height: 500px; margin-left: auto; margin-right: auto; ">
    	<iframe class="embed-responsive-item" src="${mvo.video_url}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen style="width: 100%; height: 100%;"></iframe>
	</div>

    
    <hr style="width: 95%; border: 1.5px solid #ccc5b9; margin-bottom: 30px;">
    
    <div id="graphdiv" style="border-top: 1px solid #ccc5b9; border-bottom: 1px solid #ccc5b9; width: 95%; margin: 0 auto;">
	    <ul class="graph" style="list-style-type: none; padding: 0; margin: 0; display: flex; justify-content: space-between;">
	        <li style="flex: 1; border-right: 1px solid #ccc5b9; padding-right: 10px; margin-right: 10px; text-align: center;">
	            <strong style="display: block; border-bottom: 1px solid #ccc5b9; padding: 10px 0 10px 0; margin-bottom: 25px; color: #eb5e28;">성별 예매 분포</strong>
	            <div class="chart" style="display: flex; justify-content: center; margin-top: 15px; margin-bottom: 15px;">
	                <canvas id="genderChart" width="380" height="160"></canvas>
	            </div>
	        </li>
	        
	        <li style="flex: 1; text-align: center;">
	            <strong style="display: block; border-bottom: 1px solid #ccc5b9; padding: 10px 0 10px 0; margin-bottom: 25px; color: #eb5e28;">연령별 예매 분포</strong>
	            <div class="chart" style="display: flex; justify-content: center; margin-top: 15px; margin-bottom: 15px;">
	                <canvas id="ageChart" width="380" height="160"></canvas>
	            </div>
	        </li>
	    </ul>
	</div>

    <!-- 후기 작성 -->
    <div class="review-section" style="margin-top: 20px; width: 95%; margin: 0 auto;">       
        <!-- 별점 선택 -->
        <div class="rating-stars">
            <label for="reviewText"><i class="fa-solid fa-circle-user" style="color: #252422;" aria-hidden="true"> 별점을 선택해주세요.</i></label>
            <span data-value="1">&#9733;</span>
            <span data-value="2">&#9733;</span>
            <span data-value="3">&#9733;</span>
            <span data-value="4">&#9733;</span>
            <span data-value="5">&#9733;</span>
        </div>
        <textarea id="reviewText" placeholder="영화에 대한 후기를 작성해주세요..." style="-webkit-border-radius: 0; outline: 0; resize: none;"></textarea><br>
        
        <!-- 별점값을 가져오기 위함 -->
        <input type="hidden" id="rating" name="rating" value="">
        
        <button onclick="submitReview()" style="margin-top: 20px; width: 100%; margin: 0 auto;">등록 하기</button>
    </div>

    <hr style="width: 95%; border: 1.5px solid #ccc5b9;">

    <!-- 작성된 후기들 -->
    <div class="reviews-list" style="width: 95%;">
        <ul id="reviewsList">        	
            <!-- 후기가 추가될 곳 -->
        </ul>
    </div>

    <!-- 페이지네이션 추가 -->
	<div>
       <nav>
          <ul id="pageBar" class="pagination"></ul>
       </nav>
   </div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>
</html>
