<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    String ctxPath = request.getContextPath();   
%>    
       
<!DOCTYPE html>
<html>
<head>

<!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>상영시간표별영화 </title>
   <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css">
    
    <!-- Font Awesome 6 Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">


    <!-- Bootstrap 4.6.2 CSS -->
    <link href="bootstrap-4.6.2-dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { font-family: Arial, sans-serif; background-color: #f8f8f8; }
        .date { margin: 0 5px; padding: 5px 10px; cursor: pointer; border: 1px solid #ccc; border-radius: 5px; }
        .date.selected { background-color: #222; color: #fff; }
        .nav-button { cursor: pointer; padding: 5px 10px; margin: 0 5px; }
        .nav-button.disabled { cursor: not-allowed; background-color: #e9ecef; color: #adb5bd; }
        .movie-section { background: #fff; padding: 1rem; margin-bottom: 1.5rem; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
        .movie-title { font-weight: bold; font-size: 1.1rem; margin-bottom: 0.5rem; }
        .showtime { display: inline-block; margin: 5px; padding: 5px 10px; background-color: #f0f0f0; border-radius: 5px; cursor: pointer; }
        .showtime.available { background-color: #444; color: #fff; }
        .showtime:hover { background-color: #222; color: #fff; }
        .seat-info { color: #28a745; font-size: 0.9rem; margin-left: 10px; }
    </style>
</head>
<body>


<jsp:include page="/WEB-INF/header1.jsp" />

    <!-- Date Selector with Navigation -->
    <div class="container my-3">
     <hr>
        <div class="d-flex justify-content-center align-items-center">
       
            <button id="prevButton" class="nav-button btn btn-outline-secondary" onclick="prevDates()">&lt;</button>
            <div id="dateContainer" class="d-flex"></div>
            <button id="nextButton" class="nav-button btn btn-outline-secondary" onclick="nextDates()">&gt;</button>
            
             
        </div>
         <hr>
    </div>
    

    <!-- Movie Schedule -->
    <div class="container">
        <!-- Sample Movie Data -->
        <div class="movie-section">
            <div class="movie-title">소방관 | 드라마 | 106분</div>
            <div>
                <button class="showtime available">22:30<span class="seat-info"> (잔여석 123석)</span></button>
                <button class="showtime">23:50<span class="seat-info"> (마감)</span></button>
            </div>
        </div>
       <hr>
        <div class="movie-section">
            <div class="movie-title">대가족 | 코미디 | 107분</div>
            <div>
                <button class="showtime available">22:00<span class="seat-info"> (잔여석 98석)</span></button>
                <button class="showtime available">24:10<span class="seat-info"> (잔여석 55석)</span></button>
            </div>
        </div>
       <hr>
        <div class="movie-section">
            <div class="movie-title">위키드 | 뮤지컬 | 160분</div>
            <div>
                <button class="showtime available">23:40<span class="seat-info"> (잔여석 75석)</span></button>
            </div>
        </div>
         
    </div>

   

    <!-- Bootstrap 4.6.2 JS -->
    <script src="bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let startDay = 0;
        const daysToShow = 8;
        const today = new Date();
        const currentMonth = today.getMonth();
        const daysInMonth = new Date(today.getFullYear(), currentMonth + 1, 0).getDate();

        function renderDates() {
            let dateContainer = document.getElementById('dateContainer');
            let nextButton = document.getElementById('nextButton');
            let prevButton = document.getElementById('prevButton');
            dateContainer.innerHTML = '';

            for (let i = startDay; i < startDay + daysToShow && (today.getDate() + i) <= daysInMonth; i++) {
                let date = new Date(today.getFullYear(), currentMonth, today.getDate() + i);
                let dateDiv = document.createElement('div');
                dateDiv.className = 'date btn btn-outline-secondary';
                dateDiv.onclick = function() { alert(date.toISOString().split('T')[0] + " 선택됨"); };
                dateDiv.innerHTML = (date.getMonth() + 1) + '월 ' + date.getDate();
                dateContainer.appendChild(dateDiv);
            }

            prevButton.classList.toggle('disabled', startDay === 0);
            nextButton.classList.toggle('disabled', startDay + daysToShow >= daysInMonth - today.getDate() + 1);
        }

        function nextDates() {
            if (startDay + daysToShow < daysInMonth - today.getDate() + 1) {
                startDay += daysToShow;
                renderDates();
            }
        }

        function prevDates() {
            if (startDay - daysToShow >= 0) {
                startDay -= daysToShow;
                renderDates();
            }
        }

        window.onload = renderDates;
    </script>
<jsp:include page="/WEB-INF/footer1.jsp" />  
</body>
</html>


