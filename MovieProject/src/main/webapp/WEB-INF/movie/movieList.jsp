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
    <title>영화차트</title>
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
        .rating {
            font-size: 0.9rem;
            color: #555;
            margin-top: 0.3rem;
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
    </style>
</head>
<body>
    <jsp:include page="../header1.jsp" />   

    <!-- Movie Cards -->
    <div class="container mt-4">
        <div>
            <ul style="display: flex; gap: 20px; padding: 0; margin-top: 20px; justify-content: flex-end; list-style-type: none;">
                <li><a class="nav-link h5" href="#">상영중인영화</a></li>
                <li><a class="nav-link h5" href="#">상영예정작</a></li>
                <select name="searchType">
                    <option value="">장르검색</option>
                    <option value="action">액션</option>
                    <option value="comedy">코미디</option>
                    <option value="drama">드라마</option>
                    <option value="thriller">스릴러</option>
                    <option value="romance">로맨스</option>
                    <option value="sf">SF</option>
                    <option value="fantasy">판타지</option>
                    <option value="animation">애니메이션</option>
                    <option value="history">역사</option>
                    <option value="crime">범죄</option>
                    <option value="sports">스포츠</option>
                    <option value="noir">느와르</option>
                </select>
                <button type="button" class="round gray">
                    <span>GO</span>
                </button>
            </ul>
        </div>

        <div class="container mt-4">
            <div class="row">
                <!-- 데이터가 있는 경우 -->
                <c:if test="${movies != null && not empty movies}">
                    <c:forEach var="movie" items="${movies}" varStatus="status">
                        <div class="col-md-4 mb-4 ${status.index >= 15 ? 'movie-hidden hidden' : ''}" >
                            <div class="movie-card position-relative" onclick="<%= ctxPath%>/movie/movieDetail.up">
                                <div class="rank">No. ${status.index + 1}</div>
                                <div class="poster">
                                    <img src="${movie.poster_file}" alt="${movie.movie_title}">
                                </div>
                                                              
                                <div class="movie-details">
                                <div class="movie-title">${movie.movie_title}</div>                                     
                                    <p>좋아요 수: ${movie.like_count}</p>                             
                                    <p>개봉일: ${movie.start_date}</p>   
                                    <button>예매하러가기</button>                                
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>

                <!-- 데이터가 없는 경우 -->
                <c:if test="${movies == null || empty movies}">
                    <p class="no-data">현재 영화 데이터가 없습니다.</p>
                </c:if>
            </div>
        </div>
    </div>

   <jsp:include page="../footer1.jsp" />

</body>

</html>