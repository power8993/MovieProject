<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>영화 목록</title>
    <style>
        /* 전체 페이지 스타일 */
        body {
           user-select: none;
           font-family: Arial, sans-serif;
           background-color: #f8f8f8;
           margin: 0;
           padding: 0;
        }

        /* 제목과 필터를 하나의 행으로 배치 */
        .filter-container {
           display: flex;
           justify-content: space-between;
           align-items: center;
           margin-bottom: 20px;
        }

        /* 상단 제목 스타일 */
        .page-title {
           text-align: left;
           margin-top: 20px;
           margin-bottom: 10px;
           font-size: 1.5rem;
           font-weight: bold;
           color: #333;
        }

        /* 장르 필터 스타일 */
        .genre-filter {
           display: flex;
           justify-content: flex-end;
           align-items: center;
        }

        .genre-filter a {
           margin-right: 20px;
           color: black;
           text-decoration: none;
        }

        .genre-filter a:hover {          
           color: #EB5E28;
           transform: translateY(-5px);    
           border: 1px solid #EB5E28;
        }

        /* 장르 필터 폼 스타일 */
        form#ucgetMoviesByGenre {
           display: flex;
           justify-content: flex-end;
           align-items: center;
        }

        form#ucgetMoviesByGenre select {
           padding: 10px;
           margin-right: 10px;
           background-color: #FFFCF2;
           color: #495057;
           border: 1px solid #EB5E28;
           border-radius: 5px;
        }

        form#ucgetMoviesByGenre button {
           padding: 10px 20px;
           background-color: #FFFCF2;
           color: #EB5E28;
           border: 1px solid #EB5E28;
           border-radius: 5px;
           cursor: pointer;
           transition: background-color 0.3s ease-in-out, color 0.3s ease-in-out;
        }

        form#ucgetMoviesByGenre button:hover {
           background-color: #EB5E28;
           color: white;
        }

        /* 영화 카드 스타일 */
        .movie-card {
           text-align: center;
           background: #FFFCF2;
           border-radius: 10px;
           padding: 1rem;
           margin: 0.5rem;
           transition: transform 0.2s ease-in-out;
           position: relative;
        }

        .movie-card:hover {
           transform: translateY(-5px);
        }

        /* 이미지 스타일 */
        .movie-card img {
           max-width: 100%;
           border-radius: 10px;
           opacity: 1;
           transition: opacity 0.3s ease;
        }

        /* 이미지가 불투명해지게 설정 */
        .movie-card:hover img {
           opacity: 0.5;
        }

        /* '상세보기' 버튼 스타일 */
        .view-details-btn {
           display: none;
           position: absolute;
           top: 40%;
           left: 50%;
           transform: translate(-50%, -50%);
           background-color: #FFFCF2;
           color: #EB5E28;
           font-size: 1rem;
           font-weight: bold;
           padding: 10px 20px;
           border: none;
           border-radius: 5px;
           cursor: pointer;
           transition: background-color 0.3s ease-in-out;
        }

        /* 마우스를 올리면 상세보기 버튼 보이기 */
        .movie-card:hover .view-details-btn {
           display: block;
        }

        .movie-title {
           font-weight: bold;
           margin-top: 1rem;
           font-size: 1.1rem;
        }

        .movie-details {
           text-align: left;
           font-size: 0.9rem;
           color: #555;
           margin-top: 1rem;
        }

        .movie-details p {
           margin: 0;
        }

        .movie-details p + p {
           margin-top: 6px; /* 예매율과 개봉일 간의 간격 */
        }

        /* 예매 버튼 스타일 */
        .reservation-btn {
           background-color: #EB5E28;
           color: #FFFCF2;
           font-weight: bold;
           padding: 0.5rem 1rem;
           border-radius: 5px;
           border: none;
           margin-top: 0.5rem;
           transition: background-color 0.2s ease-in-out;
        }

        .reservation-btn:hover {
           background-color: #c23041;
        }

        /* 영화 순위 스타일 */
        .rank {
           background-color: #EB5E28;
           color: #fff;
           padding: 0.3rem 0.7rem;
           border-radius: 5px;
           font-weight: bold;
           position: absolute;
           top: 10px;
           left: 10px;
           z-index: 10; /* 다른 요소 위로 표시되도록 설정 */
        }

        /* 숨김 클래스 */
        .hidden {
           display: none;
        }

        /* 더보기 버튼 스타일 */
        .show-more {
           text-align: center;
           margin: 2rem 0;
        }

        .show-more button {
           padding: 0.5rem 1.5rem;
           font-size: 1rem;
           border: 2px solid #EB5E28;
           border-radius: 5px;
           background-color: #FFFCF2;
           cursor: pointer;
           color: #EB5E28;
        }

        .show-more button:hover {
           background-color: #EB5E28;
           color: #FFFCF2;
        }

        /* 데이터가 없을 때 메시지 스타일 */
        .no-data {
           text-align: center;
           font-size: 1.2rem;
           color: #999;
           margin-top: 2rem;
           margin: auto;
        }
        .genre-filter a.bold {
	        background-color: #FFFCF2;
	        color: #EB5E28;
	        text-decoration: underline;
        
	    }
	   .movie-details p {
   		 margin: 0;
		}

		.movie-details p + p {
		    margin-top: 4px; /* 예매율과 개봉일 간의 간격 */
		}
	    
    </style>
</head>
<body>
    <jsp:include page="../header1.jsp" />

    <div class="container mt-4">
        <div class="filter-container">
            <div class="page-title">상영 예정작</div>
            <div class="genre-filter">
                <a href="<%= ctxPath %>/movie/movieList.mp">전체 영화</a>
                <a href="<%= ctxPath %>/movie/runningMovies.mp">상영중인 영화</a>
                <a href="<%= ctxPath %>/movie/upcomingMovies.mp" class="bold">상영 예정작</a>
            </div>
        </div>

        <hr>

        <form id="ucgetMoviesByGenre" action="<%= ctxPath %>/movie/ucfilterByGenre.mp" method="get">
            <select name="genreCode">
                <option value="">장르</option>
                <c:forEach var="cg" items="${cgList}">
                    <option value="${cg.category_code}" ${cg.category_code == selectedGenre ? "selected" : ""}>${cg.category}</option>
                </c:forEach>
            </select>
            <button type="button" class="round gray" onclick="submitGenreForm()">GO</button>
        </form>

        <div class="row" id="movie-list">
            <c:if test="${movies != null && not empty movies}">
                <c:forEach var="movie" items="${movies}" varStatus="status">
                    <div class="col-md-4 mb-4 movie-card-container ${status.index >= 15 ? 'hidden' : ''}">
                        <div class="movie-card position-relative">
                            
                            <div class="poster">
                                <img src="<%= ctxPath %>/images/admin/poster_file/${movie.poster_file}.jpg">
                            </div>
                            <button class="view-details-btn" onclick="location.href='<%= ctxPath %>/movie/movieDetail.mp?seq_movie_no=${movie.seq_movie_no}&bookingRate=${movie.bookingRate}'">상세보기</button>
                            <div class="movie-details">
                                <div class="movie-title">
                                    <img src="<%= ctxPath %>/images/admin/movie_grade/${movie.movie_grade}.png" 
                                         alt="${movie.movie_grade}" 
                                         style="width: 30px; height: 30px; margin-right: 10px;">
                                    ${movie.movie_title}
                                </div>
                                <p>예매율: ${movie.bookingRate}%</p>
                                <p>개봉일: ${movie.start_date}</p>
                                <button class="reservation-btn">
                                    <a href="<%= ctxPath %>/reservation/reservation.mp?seq_movie_no=${movie.seq_movie_no}" 
                                       style="color: white; text-decoration: none;">예매하기</a>
                                </button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${movies == null || empty movies}">
                <p class="no-data" style="color: #EB5E28; font-weight: bold; font-size: 2rem;">해당 장르의 영화가 없습니다.<br><img src="<%= ctxPath %>/images/index/logo.png"></p>
            </c:if>
        </div>

        <c:if test="${movies != null && movies.size() > 15}">
            <div class="show-more">
                <button onclick="showMoreMovies()">더보기+</button>
            </div>
        </c:if>
    </div>

    <jsp:include page="../footer1.jsp" />

    <script>
        function submitGenreForm() {
            document.getElementById('ucgetMoviesByGenre').submit();
        }

        function showMoreMovies() {
            const hiddenMovies = document.querySelectorAll('.movie-card-container.hidden');
            const maxToShow = 15;
            let count = 0;
            hiddenMovies.forEach(movie => {
                if (count < maxToShow) {
                    movie.classList.remove('hidden');
                    count++;
                }
            });

            if (document.querySelectorAll('.movie-card-container.hidden').length === 0) {
                document.querySelector('.show-more').style.display = 'none';
            }
        }
    </script>
</body>
</html>
