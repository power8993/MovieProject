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
	
%>

<script type="text/javascript">
	
$(document).ready(function(){
	
	$("tr.movie-list").click(e => {
		$("tr.movie-list").css({'background-color':'','color':''});
        $(e.target).parent().css({'background-color':'black','color':'white'});
        $("div#movie-choice").html($(e.target).parent().find("td.movie-title").html());
	});
	
	$("li#day").find("span").click(e => {
		$("li#day").css({'background-color':'','color':''});
        $(e.target).parent().css({'background-color':'black','color':'white'});
        $("div#movie-choice").html($(e.target).parent().find("td.movie-title").html());
	});
	
	
	
}); // end of $(document).ready(function() {});;--------------------------------------------

</script>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/reservation/reservation.css" />



<div class="container">
	
	<div id="ticket" class="ticket">
		
		<div class="steps">
			
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
		
		<div id="ticket-info-container" class="ticket-info-container">
			<div id="ticket-info" class="container ticket-info" style="align-content: center;">
				<div id="movie-choice" class="movie-choice">영화선택</div>
				<div id="theater-choice">극장선택</div>
				<div>> 좌석선택 > 결제</div>
			</div>
		</div>
		
	</div>

</div>


<jsp:include page="../footer1.jsp" />