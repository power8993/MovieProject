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
    <title>영화시간표 </title>
   <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css">
    
    <!-- Font Awesome 6 Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">


    <!-- Bootstrap 4.6.2 CSS -->
    <link href="bootstrap-4.6.2-dist/css/bootstrap.min.css" rel="stylesheet">
     <style>
        body { font-family: Arial, sans-serif; background-color: #f8f8f8; }
        .movie-card { text-align: center; background: #fff; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); padding: 1rem; margin: 0.5rem; }
        .movie-card img { max-width: 100%; border-radius: 10px; }
        .movie-title { font-weight: bold; margin-top: 1rem; }
        .reservation-btn { background-color: #e63b4c; color: #fff; font-weight: bold; padding: 0.5rem 1rem; border-radius: 5px; border: none; }
        .reservation-btn:hover { background-color: #c23041; color: #fff; }
        .rating { font-size: 0.9rem; color: #555; }
        .rank { background-color: #333; color: #fff; padding: 0.3rem 0.7rem; border-radius: 5px; font-weight: bold; position: absolute; top: 10px; left: 10px; }
        .hidden { display: none; }
    </style>
    <script>
        function showMore() {
            let hiddenMovies = document.querySelectorAll('.movie-hidden');
            hiddenMovies.forEach(movie => movie.classList.remove('hidden'));
            document.getElementById('showMoreBtn').style.display = 'none';
        }
    </script>
</head>
<body>
   <jsp:include page="/WEB-INF/header1.jsp" />
    
 
    <!-- Movie Cards -->
    <div class="container mt-4">
     <div>    
    <!-- 메뉴 항목들 -->    
    <h1>무비 차트</h1> 
    <ul style="display: flex; gap: 20px; padding: 0; margin-top: 20px; justify-content: flex-end; list-style-type: none;">    
        <li><a class="nav-link h5" href="#">상영중인영화</a></li>
        <li><a class="nav-link h5" href="#">상영예정작</a></li>
    </ul>
    
   </div>
 <hr>
 
 <select name="searchType">
         <option value="">장르검색</option>
         <option value="name">액션</option>
         <option value="userid">코미디</option>
         <option value="email">드라마</option>
         <option value="email">스릴러</option>
         <option value="email">로맨스</option>
         <option value="email">sf</option>
         <option value="email">판타지</option>
         <option value="email">애니메이션</option>
         <option value="email">역사</option>
         <option value="email">범죄</option>
         <option value="email">스포츠</option>
         <option value="email">느와르</option>
      </select>
  	  <button type="button" class="round gray">
  	  	<span>검색</span>
  	  </button>
 
       <div class="row">
            <% 
                String[] movieNames = {"a", "a", "a", "a", "a", "a", "a", "a","a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"};
                String[] ratings = {"24.2%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%","24.2%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%", "18.0%"};
                String[] thumbs = {"2024.12.24", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18","2024.12.24", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18", "2024.12.18"};
                String[] images = {"poster1.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg","poster1.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg", "poster2.jpg"};
                
                for (int i = 0; i < movieNames.length; i++) { 
            %>
            <div class="col-md-4 mb-4 <%= i >= 15 ? "movie-hidden hidden" : "" %>">
                <div class="movie-card position-relative">
                    <div class="rank">No.<%= i + 1 %></div>
                    <img src="<%= images[i] %>" alt="<%= movieNames[i] %>" class="img-fluid">
                    <div class="movie-title"><%= movieNames[i] %></div>
                    <div class="rating">예매율 <%= ratings[i] %> | <span>&#128077; <%= thumbs[i] %></span></div>
                    <button class="reservation-btn mt-2">예매하기</button>
                </div>
            </div>
            <% } %>
        </div>
        
        <!-- Show More Button -->
        <div class="text-center mb-4">
            <button id="showMoreBtn" class="btn btn-outline-secondary" onclick="showMore()">
                더 보기 <span class="ml-2">&#43;</span>
            </button>
        </div>
    </div>

  

<jsp:include page="/WEB-INF/footer1.jsp" />
</body>
</html>


    