<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
    java.time.LocalDate today = java.time.LocalDate.now();
    String selectedDate = request.getParameter("selectedDate");
    if (selectedDate == null || selectedDate.trim().isEmpty()) {
        selectedDate = today.toString(); // 페이지가 처음 로드될 때 오늘 날짜로 초기화
    }
    int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 0;

    int startDay = today.getDayOfMonth() + (pageIndex * 7); 
    int daysInMonth = today.lengthOfMonth(); 
    int endDay = Math.min(startDay + 6, daysInMonth); 
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>영화 상영 시간표</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <style>
        /* 동일한 스타일 유지 */
        body { font-family: Arial, sans-serif; background-color: #f8f8f8; }
        .date-selector { display: flex; justify-content: center; margin-bottom: 20px; }
        .date-button { margin: 0 5px; padding: 10px 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #f8f8f8; cursor: pointer; font-size: 16px; transition: background-color 0.3s ease; }
        .date-button.active { background-color: #007bff; color: white; border-color: #007bff; }
        .date-button:hover { background-color: #0056b3; color: white; }
        .movie-section { background: #fff; padding: 1rem; margin-bottom: 1.5rem; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
        .movie-title { font-weight: bold; font-size: 1.2rem; margin-bottom: 10px; color: #333; }
        .showtime-row { display: flex; gap: 10px; flex-wrap: wrap; margin-top: 10px; }
        .showtime-button { padding: 10px 15px; background-color: #444; color: #fff; border-radius: 5px; cursor: pointer; text-decoration: none; white-space: nowrap; transition: background-color 0.3s ease; }
        .showtime-button:hover { background-color: #222; }
        .showtime-button.disabled { pointer-events: none; background-color: #ccc; color: #666; }
        .screen-title { font-weight: bold; margin-top: 10px; font-size: 1rem; color: #555; }
        .pagination-buttons { display: flex; justify-content: center; margin-top: 20px; }
        .pagination-button { margin: 0 5px; padding: 10px 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #f8f8f8; cursor: pointer; font-size: 16px; transition: background-color 0.3s ease; }
        .pagination-button.disabled { background-color: #e9ecef; color: #6c757d; cursor: not-allowed; }
        .pagination-button:hover:not(.disabled) { background-color: #007bff; color: white; }
        .no-data { text-align: center; color: #999; font-size: 1rem; margin-top: 20px; }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container my-4">
    <!-- 날짜 버튼 선택 UI -->
    <div class="date-selector">
        <% if (pageIndex > 0) { %>
            <button type="button" class="pagination-button" onclick="changePage(<%= pageIndex - 1 %>)">&lt; 이전</button>
        <% } %>
        <% 
        for (int day = startDay; day <= endDay && day <= daysInMonth; day++) {
            java.time.LocalDate date = today.withDayOfMonth(day);
            String dateString = date.toString();
            String displayDate = date.getMonthValue() + "월 " + date.getDayOfMonth() + "일";
            boolean isSelected = dateString.equals(selectedDate);
        %>
            <button 
                type="button" 
                class="date-button <%= isSelected ? "active" : "" %>" 
                onclick="selectDate('<%= dateString %>')">
                <%= displayDate %>
            </button>
        <% } %>
        <% if (endDay < daysInMonth) { %>
            <button type="button" the class="pagination-button" onclick="changePage(<%= pageIndex + 1 %>)">다음 &gt;</button>
        <% } %>
    </div>

    <form id="dateForm" action="<%= ctxPath %>/movie/searchDate.mp" method="post" style="display: none;">
        <input type="hidden" id="selectedDate" name="selectedDate" value="<%= selectedDate %>">
        <input type="hidden" id="pageIndex" name="pageIndex" value="<%= pageIndex %>">
    </form>

    <!-- 상영 시간표 표시 -->
    <c:if test="${not empty movieTimeList}">
        <c:set var="lastTitle" value="" />
        <c:forEach var="movie" items="${movieTimeList}">
            <c:if test="${lastTitle != movie.movie_title}">
                <div class="movie-section">
                    <div class="movie-title">${movie.movie_title} | ${movie.running_time}분 | ${movie.start_date} 개봉</div>
                    <!-- 1관 -->
                    <c:if test="${not empty movieTimeList_o}">
                        <div class="screen-title">1관</div>
                        <div class="showtime-row">
                            <c:forEach var="movie1" items="${movieTimeList_o}">
                                <c:if test="${movie1.movie_title == movie.movie_title}">
                                    <a href="<%= ctxPath %>/reservation/reservation.mp?seq_movie_no=${movie1.seq_movie_no}&start_time=${movie1.svo.start_time}&start_date=${movie1.start_date}&screen_no=${movie1.svo.fk_screenNO}"
                                        class="showtime-button ${movie1.svo.unused_seat == 0 ? 'disabled' : ''}"
                                        ${movie1.svo.unused_seat == 0 ? 'onclick="return false;"' : ''}>
                                        ${movie1.svo.start_time} <br> ${movie1.svo.unused_seat}석
                                    </a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                    <!-- 2관 -->
                    <c:if test="${not empty movieTimeList_t}">
                        <div class="screen-title">2관</div>
                        <div class="showtime-row">
                            <c:forEach var="movie2" items="${movieTimeList_t}">
                                <c:if test="${movie2.movie_title == movie.movie_title}">
                                    <a href="<%= ctxPath %>/reservation/reservation.mp?seq_movie_no=${movie2.seq_movie_no}&start_time=${movie2.svo.start_time}&start_date=${movie2.start_date}&screen_no=${movie2.svo.fk_screenNO}"
                                        class="showtime-button ${movie2.svo.unused_seat == 0 ? 'disabled' : ''}"
                                        ${movie2.svo.unused_seat == 0 ? 'onclick="return false;"' : ''}>
                                        ${movie2.svo.start_time} <br> ${movie2.svo.unused_seat}석
                                    </a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
                <c:set var="lastTitle" value="${movie.movie_title}" />
            </c:if>
        </c:forEach>
    </c:if>

    <!-- 데이터가 없을 때 메시지 표시 -->
    <c:if test="${movieTimeList == null || empty movieTimeList}">
        <p class="no-data">현재 선택한 날짜에 상영 중인 영화가 없습니다.</p>
    </c:if>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

<script>
function selectDate(date) {
    document.getElementById('selectedDate').value = date;
    document.getElementById('dateForm').submit();
}

function changePage(page) {
    document.getElementById('pageIndex').value = page;
    document.getElementById('dateForm'). submit();
}
</script>

</body>
</html>
