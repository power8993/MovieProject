<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
	String seq_movie_no = request.getParameter("seq_movie_no");
	String start_date = request.getParameter("start_date");
	String start_time = request.getParameter("start_time");
	String fk_screen_no = request.getParameter("screen_no");
%>



<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<jsp:include page="../header1.jsp" />

<%
	Calendar currentDate = Calendar.getInstance();
	currentDate.add(Calendar.DATE, -1);
	
	int year_current = 0;
	
	int month_current = 0;
	
	String[] dayname = {"일", "월", "화", "수", "목", "금", "토"};
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
%>

<script type="text/javascript" src="<%= ctxPath%>/js/reservation/reservation.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	
	let cnt = 0;
	
	// 영화에서 예매하기를 눌러서 상영 영화 번호를 넘겨줬을 때
	if(<%= seq_movie_no%> != null && cnt == 0) {
		cnt++; // 처음 페이지에 들어왔을 때만 실행되도록
		$("td#seq_movie_no").each(function(index, elmt) {
			if($(this).text() == <%= seq_movie_no%>) {
				$(this).parent().addClass("selected");
				$("div#movie-choice").text($(this).parent().find("td.movie-title").text());
				let v_html = '<img src="http://localhost:9090/MovieProject/images/admin/poster_file/미니언즈.jpg" style="width:auto; height:110px;">';
				$("div#movie-choice-poster").html(v_html);
				$("div#movie-choice-poster").show();
				
				$("div#empty_div").css('width','130px');
				return false;
			}
		});
		
		if(<%= start_date%> != null && '<%= start_time%>' != null) {
			$("span.input_date").each(function(index, elmt) {
				if($(this).text() == '<%= start_date%>') {
					$(this).parent().addClass("selected");
					$("div#date-choice").text($(this).parent().find("span.input_date").text());
					$("div#screen-date-info").text('상영날짜 : <%= start_date%>');
					
					// 영화와 날짜를 선택했을 때 상영 시간이 보여주기
					getScreenTime('<%= seq_movie_no%>', '<%= start_date%>', '<%= start_time%>', '<%= fk_screen_no%>');
					return false;
				}
			})
			
		}
	}
	
	
}); // end of $(document).ready(function(){})------------------------------


</script>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/reservation/reservation.css" />



<div class="container">
	
	<div id="ticket" class="ticket">
		
		<div id="step1" class="steps">
			
			<div class="section movie">
				
				<div class="col-head">
					<h3 class="title">영화</h3>
				</div>

				<div class="col-body">
					<c:if test="${not empty requestScope.movieList}">
						<table id="movie-list">
							<tbody>
			      				<c:forEach var="movievo" items="${requestScope.movieList}" varStatus="status">
									<tr class="movie-list">
										<td class="movie-grade ml-2" style="vertical-align: middle;">
											<span style="display: none;">${movievo.movie_grade}</span>
											<img class="movie_grade_img" src="<%= ctxPath %>/images/admin/movie_grade/${movievo.movie_grade}.png" alt="${movievo.movie_grade}">
										</td>
										<td class="movie-title">${movievo.movie_title}</td>
										<td id="seq_movie_no" style="display: none">${movievo.seq_movie_no}</td>
									</tr>
			      				</c:forEach>
		      				</tbody>
		     			</table>
					</c:if>
		    		<c:if test="${empty requestScope.movieList}">
		      			<p>상영중인 영화가없습니다.</p>
		      		</c:if>
				</div>
			</div>

			<div class="section date">
				
				<div class="col-head">
					<h3 class="title">날짜</h3>
				</div>

				<div class="col-body">
					<ul>
						<% 
							for(int i=0; i<40; i++) {
								currentDate.add(Calendar.DATE, 1); %>
								<% 
									if(month_current != (currentDate.get(Calendar.MONTH) + 1)) {
										month_current = (currentDate.get(Calendar.MONTH) + 1);
										year_current = currentDate.get(Calendar.YEAR);
										%>
										<li>
											<div>
												<span class="year"><%= year_current %></span>
												<span class="month"><%= month_current %></span>
											</div>
										</li>
										<%
									}
								%>
								
								<li class="day">
									<span class="dayweek"><%= dayname[currentDate.get(Calendar.DAY_OF_WEEK)-1] %>&nbsp;&nbsp;</span>
									<span class="date"><%= currentDate.get(Calendar.DATE) %></span>
									<span class="input_date" style="display: none"><%= sdf.format(currentDate.getTime())%></span>
								</li>
								<%
							} // end of for---------------------------------------
						
						%>
					</ul>
				</div>
			</div>
			
			<div class="section time">
				
				<div class="col-head">
					<h3 class="title">시간</h3>
				</div>

				<div class="col-body">
					<table class="mt-3 ml-4 time-tab">
						<tbody class="time-table">
							<tr class="time-choice">
								<td class="time_data" style="display: none"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
		</div>
		
		<%-- -------------------------------------------------------------------- --%>
		
		<div id="step2" class="steps">
			<div class="col-head">
				<h3 class="title">인원 / 좌석</h3>
			</div>
			<div class="col-body">
				<div id="person-screen">
					<div id="numberOfPeople" class="mt-3">
						<div id="adult" class="btn-group">
							<label>성인</label>
							<button type="button" class="btn adult" value="0">0</button>
							<button type="button" class="btn adult" value="1">1</button>
							<button type="button" class="btn adult" value="2">2</button>
							<button type="button" class="btn adult" value="3">3</button>
							<button type="button" class="btn adult" value="4">4</button>
							<button type="button" class="btn adult" value="5">5</button>
						</div>
						<div id="adolescent" class="btn-group">
							<label>청소년</label>
							<button type="button" class="btn adolescent" value="0">0</button>
							<button type="button" class="btn adolescent" value="1">1</button>
							<button type="button" class="btn adolescent" value="2">2</button>
							<button type="button" class="btn adolescent" value="3">3</button>
							<button type="button" class="btn adolescent" value="4">4</button>
							<button type="button" class="btn adolescent" value="5">5</button>
						</div>
						<div id="youth" class="btn-group">
							<label>어린이</label>
							<button type="button" class="btn youth" value="0">0</button>
							<button type="button" class="btn youth" value="1">1</button>
							<button type="button" class="btn youth" value="2">2</button>
							<button type="button" class="btn youth" value="3">3</button>
							<button type="button" class="btn youth" value="4">4</button>
							<button type="button" class="btn youth" value="5">5</button>
						</div>
					</div>
					<div id="point">
						<div>포인트 사용</div>
						<input id="using-point" type="number" step="100" min="0" value="0" placeholder="point를 입력해주세요" />
						<div>보유중인 point : <label id="having-point"></label></div>
					</div>
					<div id="screen-info">
						<div id="screen-date-info"></div>
						<div id="screen-time-info"></div>
						<div id="total_seat_cnt" style="display: none;">0</div>
						<div id="selected_seat_cnt" style="display: none;">0</div>
						<div id="totalPrice" style="display: none;"></div>
					</div>
				</div>
				<div id="seat-screen" class="text-center">
				</div>
			</div>
		</div>
		
		<%-- -------------------------------------------------------------------- --%>
		
		<div id="ticket-info-container" class="ticket-info-container">
			<div id="ticket-info" class="container ticket-info" style="align-content: center;">
				<div id="goMovieChoice" class="bg-dark" onclick="goMovieChoice()" style="border: solid 2px gray; border-radius: 10px; width:110px; cursor: pointer;">
					<i class="fa-solid fa-circle-left fa-3x my-2"></i>
					<div>영화선택</div>
				</div>
				<div id="movie-choice-poster" class="movie-choice-poster" style="width: 100px;"></div>
				<div id="movie-choice" class="movie-choice">영화선택</div>
				<div>
					<div id="date-choice">시간선택</div>
					<div id="time-choice"></div>
					<div id="seq_showtime_no" style="display: none;"></div>
				</div>
				<div id="seat-choice">좌석선택</div>
				<div id="pay-choice">예약정보</div>
				<div id="empty_div" style="width: 230px"></div>
				
				<div id="goSeatChoice" class="bg-dark" onclick="goSeatChoice('${sessionScope.loginuser.userid}', '${sessionScope.loginuser.birthday}')" style="border: solid 2px gray; border-radius: 10px; width:110px; cursor: pointer;">
					<i class="fa-solid fa-circle-right fa-3x my-2"></i>
					<div>좌석선택</div>
				</div>
				<div id="goPay" class="bg-dark" onclick="goPay('<%= ctxPath%>', '${sessionScope.loginuser.userid}')" style="border: solid 2px gray; border-radius: 10px; width:110px; cursor: pointer;">
					<i class="fa-solid fa-circle-right fa-3x my-2"></i>
					<div>결제하기</div>
				</div>
				
			</div>
		</div>
		
	</div>
	
	<%-- CSS 로딩화면 구현한것--%>
	<div style="display: flex; position: absolute; top: 50%; left: 47%; border: solid 0px blue;">
		<div class="loader" style="margin: auto"></div>
	</div>
    

</div>
<form name="payment">
	<input type="hidden" name="imp_uid"/>
	<input type="hidden" name="userid"/>
</form>

<jsp:include page="../footer1.jsp" />