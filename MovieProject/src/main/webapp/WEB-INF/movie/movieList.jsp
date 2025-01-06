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
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }
        .movie-card {
            text-align: center;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 1rem;
            margin: 0.5rem;
            transition: transform 0.2s ease-in-out;
        }
        .movie-card:hover {
            transform: translateY(-5px);
        }
        .movie-card img {
            max-width: 100%;
            border-radius: 10px;
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
        .reservation-btn {
            background-color: #e63b4c;
            color: #fff;
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
        .rank {
            background-color: #333;
            color: #fff;
            padding: 0.3rem 0.7rem;
            border-radius: 5px;
            font-weight: bold;
            position: absolute;
            top: 10px;
            left: 10px;
        }
        .hidden {
            display: none;
        }
        .show-more {
            text-align: center;
            margin: 2rem 0;
        }
        .show-more button {
            padding: 0.5rem 1.5rem;
            font-size: 1rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f8f8f8;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .show-more button:hover {
            background-color: #e8e8e8;
        }
        .no-data {
            text-align: center;
            font-size: 1.2rem;
            color: #999;
            margin-top: 2rem;
        }
        .genre-filter {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            margin-bottom: 20px;
        }
        .genre-filter select {
            padding: 5px 10px;
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <jsp:include page="../header1.jsp" />

    <div class="container mt-4">
        <div class="genre-filter">
            <a class="nav-link menufont_size" href="<%= ctxPath %>/movie/runningMovies.mp" style="margin-right: 20px;">상영중인 영화</a>
            <a class="nav-link menufont_size" href="<%= ctxPath %>/movie/upcomingMovies.mp" style="margin-right: 20px;">상영 예정작</a>
            <form id="genreSearchForm" action="<%= ctxPath %>/movie/allfilterByGenre.mp" method="get">
                <select name="genreCode">
                    <option value="">장르</option>
                    <c:forEach var="cg" items="${cgList}">
                        <option value="${cg.category_code}" ${cg.category_code == selectedGenre ? "selected" : ""}>${cg.category}</option>
                    </c:forEach>
                </select>
                <button type="button" class="round gray" onclick="submitGenreForm()">GO</button>
            </form>
        </div>

        <div class="row" id="movie-list">
            <c:if test="${movies != null && not empty movies}">
                <c:forEach var="movie" items="${movies}" varStatus="status">
                    <div class="col-md-4 mb-4 movie-card-container ${status.index >= 15 ? 'hidden' : ''}">
                        <div class="movie-card position-relative">
                            <div class="rank">No. ${status.index + 1}</div>
                            <div class="poster">
                                <a href="<%= ctxPath %>/movie/movieDetail.mp?seq_movie_no=${movie.seq_movie_no}">
                                    <img src="${movie.poster_file}" alt="${movie.movie_title}">
                                </a>
                            </div>
                            <div class="movie-details">
                                <div class="movie-title">
                                    <img src="<%= ctxPath %>/images/admin/movie_grade/${movie.movie_grade}.png" 
                                         alt="${movie.movie_grade}" 
                                         style="width: 30px; height: 30px; vertical-align: middle; margin-right: 10px;">
                                    ${movie.movie_title}
                                </div>
                                <p>예매율: ${movie.bookingRate}<br>개봉일: ${movie.start_date}</p>
                                <button class="reservation-btn">
                                    <a href="<%= ctxPath %>/reservation/reservation.mp?seq_movie_no=${movie.seq_movie_no}" style="color: white; text-decoration: none;">예매하기</a>
                                </button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${movies == null || empty movies}">
                <p class="no-data">해당 장르의 영화가 없습니다.</p>
            </c:if>
        </div>

        <c:if test="${movies != null && movies.size() > 15}">
            <div class="show-more">
                <button onclick="showMoreMovies()">더보기</button>
            </div>
        </c:if>
    </div>

    <jsp:include page="../footer1.jsp" />

    <script>
        function submitGenreForm() {
            const form = document.getElementById('genreSearchForm');
            form.submit();
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
