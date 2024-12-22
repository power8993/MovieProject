<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
   String ctxPath = request.getContextPath();
    //     /MyMVC
%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<jsp:include page="../header1.jsp" />

<%
	Calendar currentDate = Calendar.getInstance();
	
	int year_current = currentDate.get(Calendar.YEAR);
	
	int month_current = (currentDate.get(Calendar.MONTH) + 1);
	
	String[] dayname = {"일", "월", "화", "수", "목", "금", "토"};
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
%>

<script type="text/javascript">
	
$(document).ready(function(){
	
	$("div#step2").hide();
	$("button#goMovieChoice").hide();
	
	let seq_movie_no = "";
	let input_date = "";
	
	// 예약페이지에서 영화를 선택했을 때
	$("tr.movie-list").click(e => {
		$("tr.movie-list").css({'background-color':'','color':''});
		$(e.target).parent().css({'background-color':'black','color':'white'});
		
		$("div#movie-choice").html($(e.target).parent().find("td.movie-title").html());
		
		seq_movie_no = $(e.target).parent().find("td#seq_movie_no").text();
		
		if($("div#time-choice").text() == "시간선택") {
			return;
		}
		
		
		
	});
	
	// 예약페이지에서 날짜를 선택했을 때
	$("li#day").find("span").click(e => {
		$("li#day").css({'background-color':'','color':''});
        $(e.target).parent().css({'background-color':'black','color':'white'});
        $("div#time-choice").html("<div>" + $(e.target).parent().find("span#input_date").text() + "</div>");
        
        input_date = $(e.target).parent().find("span#input_date").text();
        
        if($("div#movie-choice").text() == "영화선택") {
        	return;
        }
        
        $("div#screen-info").html("<div>" + input_date + "</div>");
        
        $.ajax({
			url:"${pageContext.request.contextPath}/reservation/getScreenTime.mp",
			type:"post",
			data: {
	             "input_date": input_date,
	             "seq_movie_no": seq_movie_no
	        },
	        success: function(data){
	        	$(data).each(function(){
					console.log(this.start_time);
				});
			},
			error: function(){
				alert("request error!");
			}
		});
        
	});

	
}); // end of $(document).ready(function() {});;--------------------------------------------


function goSeatChoice() {
	$("div#step1").hide();
	$("div#step2").show();
	$("button#goMovieChoice").show();
	$("button#goSeatChoice").hide();
}

function goMovieChoice() {
	$("div#step1").show();
	$("div#step2").hide();
	$("button#goMovieChoice").hide();
	$("button#goSeatChoice").show();
}

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
										<td class="movie-grade">${movievo.movie_grade}</td>
										<td class="movie-title">${movievo.movie_title}</td>
										<td id="seq_movie_no" style="display: none">${movievo.seq_movie_no}</td>
									</tr>
			      				</c:forEach>
		      				</tbody>
		     			</table>
					</c:if>
		    		<c:if test="${empty requestScope.movieList }">
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
						<li>
							<div>
								<span class="year"><%= year_current %></span>
								<span class="month"><%= month_current %></span>
							</div>
						</li>
						<li class="day" id="day" data-index="0">
							<span class="dayweek" name="dd"><%= dayname[currentDate.get(Calendar.DAY_OF_WEEK)-1]%>&nbsp;&nbsp;</span>
							<span class="date"><%= currentDate.get(Calendar.DATE) %></span>
							<span id="input_date" style="display: none"><%= sdf.format(currentDate.getTime())%></span>
						</li>
						<% 
							for(int i=0; i<20; i++) {
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
								
								<li class="day" id="day">
									<span class="dayweek"><%= dayname[currentDate.get(Calendar.DAY_OF_WEEK)-1] %>&nbsp;&nbsp;</span>
									<span class="date"><%= currentDate.get(Calendar.DATE) %></span>
									<span id="input_date" style="display: none"><%= sdf.format(currentDate.getTime())%></span>
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
					<div id="numberOfPeople">
						<div>
							<label>일반</label>
							<button>a</button>
						</div>
						<div>
							<label>청소년</label>
							<button>a</button>
						</div>
						<div>
							<label>어린이</label>
							<button>a</button>
						</div>
					</div>
					<div id="screen-info">
						
					</div>
				</div>
			</div>
		</div>
		
		<%-- -------------------------------------------------------------------- --%>
		
		<div id="ticket-info-container" class="ticket-info-container">
			<div id="ticket-info" class="container ticket-info" style="align-content: center;">
				<button id="goMovieChoice" onclick="goMovieChoice()">-> 영화선택</button>
				<div id="movie-choice" class="movie-choice">영화선택</div>
				<div id="time-choice">시간선택</div>
				<div>> 좌석선택</div>
				<div>> 결제</div>
				<button id="goSeatChoice" onclick="goSeatChoice()">-> 좌석선택</button>
			</div>
		</div>
		
	</div>

</div>


<jsp:include page="../footer1.jsp" />