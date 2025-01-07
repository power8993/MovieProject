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
    /* 전체 페이지의 기본 글꼴과 배경 색상 설정 */
    body { 
        font-family: Arial, sans-serif; /* 글꼴을 Arial로 설정 */
        background-color: #f8f8f8; /* 배경 색상을 연한 회색으로 설정 */
    }

    /* 날짜 선택 버튼 컨테이너 스타일 */
    .date-selector { 
        display: flex; /* 버튼들을 가로로 정렬 */
        justify-content: center; /* 버튼들을 컨테이너 중앙에 정렬 */
        margin-bottom: 20px; /* 아래쪽 여백 추가 */
    }

    /* 개별 날짜 버튼 스타일 */
    .date-button { 
        margin: 0 5px; /* 좌우 여백 설정 */
        padding: 10px 20px; /* 버튼 안쪽 여백 설정 */
        border: 1px solid #EB5E28; /* 테두리를 회색으로 설정 */
        border-radius: 5px; /* 버튼의 모서리를 둥글게 설정 */
        background-color: #FFFCF2; /* 버튼 배경 색상을 연한 회색으로 설정 */
        cursor: pointer; /* 커서를 포인터로 변경 */
        font-size: 16px; /* 버튼 텍스트 크기 설정 */
        transition: background-color 0.3s ease; /* 배경 색상 변화에 부드러운 전환 효과 추가 */
        color: #EB5E28;
    }

    /* 선택된 날짜 버튼 스타일 */
    .date-button.active { 
        background-color: #EB5E28; /* 활성화된 버튼의 배경 색상 설정 (파란색) */
        color: #FFFCF2; /* 텍스트 색상을 흰색으로 변경 */
        border-color: #FFFCF2; /* 테두리 색상도 파란색으로 설정 */
    }

    /* 날짜 버튼에 마우스를 올릴 때 스타일 */
    .date-button:hover { 
        background-color: #EB5E28; /* 더 진한 파란색 배경 */
        color: #FFFCF2; /* 텍스트를 흰색으로 변경 */
        transform: translateY(-5px);
    }

    /* 영화 섹션 박스 스타일 */
    .movie-section { 
        background: #FFFCF2; /* 흰색 배경 */
        /* padding: 1rem; /* 안쪽 여백 설정 */
        margin-bottom: 1.5rem; /* 아래쪽 여백 추가 */ */
        border-radius: 5px; /* 모서리를 둥글게 설정 */
       /*  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 가벼운 그림자 효과 추가 */ */
    }

    /* 영화 제목 스타일 */
    .movie-title { 
        font-weight: bold; /* 텍스트를 굵게 설정 */
        font-size: 1.2rem; /* 텍스트 크기 설정 */
        margin-bottom: 10px; /* 아래쪽 여백 추가 */
        color: #333; /* 짙은 회색 텍스트 색상 */
    }

    /* 상영 시간 버튼 컨테이너 스타일 */
    .showtime-row { 
        display: flex; /* 버튼들을 가로로 정렬 */
        gap: 10px; /* 버튼 간의 간격 설정 */
        flex-wrap: wrap; /* 버튼이 줄바꿈될 수 있도록 설정 */
        margin-top: 10px; /* 위쪽 여백 추가 */
    }

    /* 상영 시간 버튼 스타일 */
    .showtime-button { 
        padding: 10px 15px; /* 버튼 안쪽 여백 설정 */
        background-color: #FFFCF2; /* 어두운 회색 배경 */
        color: #EB5E28; /* 텍스트 색상을 흰색으로 설정 */
        border-radius: 5px; /* 모서리를 둥글게 설정 */
        cursor: pointer; /* 커서를 포인터로 변경 */
        text-decoration: none; /* 텍스트 밑줄 제거 */
        white-space: nowrap; /* 텍스트가 줄바꿈되지 않도록 설정 */
        transition: background-color 0.3s ease; /* 배경 색상 변화에 부드러운 전환 효과 추가 */
    }

    /* 상영 시간 버튼에 마우스를 올릴 때 스타일 */
    .showtime-button:hover { 
        background-color: #EB5E28; /* 더 진한 회색 배경 */
        color: #FFFCF2;
    }

    /* 예약 불가 버튼 스타일 */
    .showtime-button.disabled { 
        pointer-events: none; /* 버튼 클릭을 비활성화 */
        background-color: #ccc; /* 버튼 배경 색상을 연한 회색으로 설정 */
        color: #666; /* 텍스트 색상을 어두운 회색으로 설정 */
    }

    /* 상영관 제목 스타일 */
    .screen-title { 
        font-weight: bold; /* 텍스트를 굵게 설정 */
        margin-top: 10px; /* 위쪽 여백 추가 */
        font-size: 1rem; /* 텍스트 크기 설정 */
        color: #555; /* 짙은 회색 텍스트 색상 */
    }

    /* 페이지 이동 버튼 컨테이너 스타일 */
    .pagination-buttons { 
        display: flex; /* 버튼들을 가로로 정렬 */
        justify-content: center; /* 버튼들을 컨테이너 중앙에 정렬 */
        margin-top: 20px; /* 위쪽 여백 추가 */
    }

    /* 개별 페이지 이동 버튼 스타일 */
    .pagination-button { 
        margin: 0 5px; /* 좌우 여백 설정 */
        padding: 10px 20px; /* 버튼 안쪽 여백 설정 */
        border: 1px solid #EB5E28; /* 테두리를 회색으로 설정 */
        border-radius: 5px; /* 버튼의 모서리를 둥글게 설정 */
        background-color: #FFFCF2; /* 버튼 배경 색상을 연한 회색으로 설정 */
        cursor: pointer; /* 커서를 포인터로 변경 */
        font-size: 16px; /* 버튼 텍스트 크기 설정 */
        transition: background-color 0.3s ease; /* 배경 색상 변화에 부드러운 전환 효과 추가 */
    }

    /* 비활성화된 페이지 이동 버튼 스타일 */
    .pagination-button.disabled { 
        background-color: #FFFCF2; /* 연한 회색 배경 */
        color: #FFFCF2; /* 텍스트 색상을 더 연한 회색으로 설정 */
        cursor: not-allowed; /* 커서를 비활성화 상태로 변경 */
    }

    /* 페이지 이동 버튼에 마우스를 올릴 때 스타일 */
    .pagination-button:hover:not(.disabled) { 
        background-color: #EB5E28; /* 파란색 배경 */
        color: white; /* 텍스트를 흰색으로 변경 */
    }

    /* 데이터가 없을 때 메시지 스타일 */
    .no-data { 
        text-align: center; /* 텍스트를 중앙 정렬 */
        color: #EB5E28; /* 텍스트 색상을 연한 회색으로 설정 */
        font-size: 1rem; /* 텍스트 크기 설정 */
        margin-top: 20px; /* 위쪽 여백 추가 */
    }

    /* 영화 제목 외 세부 정보 텍스트 스타일 */
    .details { 
        font-size: 0.8rem; /* 글자 크기를 작게 설정 */
        font-weight: normal; /* 텍스트 굵기를 일반적으로 설정 */
        color: #FFFCF2; /* 텍스트 색상을 어두운 회색으로 설정 */
        margin-right: 10px; /* 오른쪽 여백 추가 */
    }
    .info-btn {
            background-color: transparent;
            border: 1px solid #FFFCF2;          
            cursor: pointer;
            font-size: 1rem;
            color : #EB5E28;
        }
        .info-btn:hover {            
            border: 1px solid #EB5E28;          
            cursor: pointer;
            font-size: 1rem;     
        }

        /* 모달 스타일 */
        .modal {
            display: none; /* 기본적으로 보이지 않음 */
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
            justify-content: center;
            align-items: center;
            color: #EB5E28;
        }

        .modal-content {
            background-color: #FFFCF2;
            /* border-radius: 10px; */
            width: 90%;
            max-width: 500px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: #EB5E28;
            background-color: #FFFCF2;
        }

        .modal-header h2 {
            margin: 20px;
            font-size: 1.5rem;
            color: #EB5E28;
            background-color: #FFFCF2;
        }

        .close-btn {
            background-color: transparent;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
        }

     
        .modal-body table {
            width: 100%;
            border-collapse: collapse;
        }

         .modal-body th, .modal-body td {
            padding: 15.4px;
            text-align: left;
            background-color: #FFFCF2;
            color: #EB5E28;    
        }
        .modal-body tr:first-child {
            font-weight: bold;         
        }
    </style>

</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container my-4">
    <!-- 날짜 버튼 선택 UI -->
    <hr>
    <div class="date-selector">
        <% if (pageIndex > 0) { %>
            <button type="button" class="pagination-button" onclick="changePage(<%= pageIndex - 1 %>)">&lt;</button>
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
            <button type="button" the class="pagination-button" onclick="changePage(<%= pageIndex + 1 %>)">&gt;</button>
        <% } %>
    </div>

    <form id="dateForm" action="<%= ctxPath %>/movie/searchDate.mp" method="post" style="display: none;">
        <input type="hidden" id="selectedDate" name="selectedDate" value="<%= selectedDate %>">
        <input type="hidden" id="pageIndex" name="pageIndex" value="<%= pageIndex %>">
    </form>
     <hr>
    
    <!-- 관람 등급 안내 버튼 추가 -->
<div class="container my-4">
    <button class="info-btn" onclick="openModal()">관람등급 안내</button>
</div>

<!-- 관람 등급 안내 모달 추가 -->
<div id="modal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>관람 등급 안내</h2>
            <button class="close-btn" onclick="closeModal()">&times;</button>
        </div>
        <div class="modal-body">
            <table>
                <thead>
                    <tr>
                        <th>구분</th>
                        <th>설명</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><img src="<%= ctxPath %>/images/admin/movie_grade/전체.png" style="width: 30px; height: 30px; margin-right: 10px; align-items: center;"></td>
                        <td>모든 연령의 고객님께서 관람하실 수 있습니다.</td>
                    </tr>
                    <tr>
                        <td><img src="<%= ctxPath %>/images/admin/movie_grade/12세.png" style="width: 30px; height: 30px; margin-right: 10px;"></td>
                        <td>만 12세 미만의 고객님은 보호자를 동반하여야 관람하실 수 있습니다.</td>
                    </tr>
                    <tr>
                        <td><img src="<%= ctxPath %>/images/admin/movie_grade/15세.png" style="width: 30px; height: 30px; margin-right: 10px;"></td>
                        <td>만 15세 미만의 고객님은 보호자를 동반하여야 관람하실 수 있습니다.</td>
                    </tr>
                    <tr>
                        <td><img src="<%= ctxPath %>/images/admin/movie_grade/19세.png" style="width: 30px; height: 30px; margin-right: 10px;"></td>
                        <td>만 19세 미만은 관람이 불가능합니다. 신분증을 지참하시기 바랍니다.</td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
    </div>
</div>
    

    
	<hr>
    <!-- 상영 시간표 표시 -->
    <c:if test="${not empty movieTimeList}">
        <c:set var="lastTitle" value="" />
        <c:forEach var="movie" items="${movieTimeList}">
            <c:if test="${lastTitle != movie.movie_title}">
                <div class="movie-section">
                    <div class="movie-title">  
                    	<img src="<%= ctxPath %>/images/admin/movie_grade/${movie.movie_grade}.png" 
                             alt="${movie.movie_grade}" 
                             style="width: 30px; height: 30px; margin-right: 10px;"> ${movie.movie_title} |${movie.cg.category} | ${movie.running_time}분 | ${movie.start_date} 개봉 </p></div>
                   <!-- 1관 -->
                   
                   <div class="screen-title">▶ 1관 | 총 40좌석 </div>
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
             
	               <!-- 2관 -->
	               
	               
                   <div class="screen-title">▶ 2관 | 총 40좌석 </div>
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
                   
                </div>
                 <hr>
                <c:set var="lastTitle" value="${movie.movie_title}" />
            </c:if>
        </c:forEach>
    </c:if>


    <!-- 데이터가 없을 때 메시지 표시 -->
    <c:if test="${movieTimeList == null || empty movieTimeList}">
         
          <p class="no-data" style="color: #EB5E28;  font-weight: bold; font-size: 2rem; /* 더 큰 글씨 크기 */"> 현재 선택한 날짜에 상영 중인 영화가 없습니다.<br><img src="<%= ctxPath %>/images/index/logo.png"></p>
        
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
// 모달 열기
function openModal() {
    document.getElementById('modal').style.display = 'flex';
}

// 모달 닫기
function closeModal() {
    document.getElementById('modal').style.display = 'none';
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    const modal = document.getElementById('modal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};
</script>

</body>
</html>
