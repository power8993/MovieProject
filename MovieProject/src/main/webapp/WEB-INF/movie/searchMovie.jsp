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
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f8f8;
            font-family: Arial, sans-serif;
        }
        .movie-card {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 1rem;
            text-align: center;
            transition: transform 0.2s ease-in-out;
        }
        .movie-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .movie-card img {
            max-width: 100%;
            border-radius: 10px;
            height: 200px;
            object-fit: cover;
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

<div class="container mt-4">
    <h1 class="mb-4 text-center">영화 검색 결과</h1>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <!-- 데이터가 있는 경우 -->
        <c:if test="${not empty movies}">
            <c:forEach var="movie" items="${movies}" varStatus="status">
                <div class="col">
                    <div class="movie-card position-relative">
                    <a href="<%= ctxPath %>/movie/movieDetail.mp?seq_movie_no=${movie.seq_movie_no}">
                        <div class="rank">No. ${status.index + 1}</div>
                        <img src="${movie.poster_file}" alt="${movie.movie_title}">
                        <div class="movie-details">
                            <h5>${movie.movie_title}</h5>
                            <p>예매율: ${movie.bookingRate}%</p>
                            <p>개봉일: ${movie.start_date}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>

        <!-- 데이터가 없는 경우 -->
        <c:if test="${empty movies}">
            <div class="col-12">
                <p class="no-data">검색된 영화가 없습니다.</p>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="../footer1.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
