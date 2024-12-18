<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
   String ctxPath = request.getContextPath();
    //     /MyMVC
%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
	Calendar currentDate = Calendar.getInstance();
	
	int year_current = currentDate.get(Calendar.YEAR);
	
	int month_current = (currentDate.get(Calendar.MONTH) + 1);
	
	String[] dayname = {"일", "월", "화", "수", "목", "금", "토"};
	
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/reservation/reservation.css" />

<jsp:include page="../header1.jsp" />

<div class="container">
	
	<div id="ticket" class="ticket">
		
		<div class="steps">
			
			<div class="section movie">
				
				<div class="col-head">
					<h3 class="title">영화</h3>
				</div>

				<div class="col-body">
					<c:if test="${not empty requestScope.movieList}">
						<ul>
		      				<c:forEach var="movievo" items="${requestScope.movieList}" varStatus="status">
								<li class="movie-list"><label class="movie-grade">${movievo.movie_grade}</label>${movievo.movie_title}</li>
		      				</c:forEach>
		     			</ul>
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
								<span id="first-year" class="year"><%= year_current %></span>
								<span id="last-year" class="month"><%= month_current %></span>
							</div>
						</li>
						<li><%= dayname[currentDate.get(Calendar.DAY_OF_WEEK)-1] %> <%= currentDate.get(Calendar.DATE) %> </li>
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
												<span id="first-year" class="year"><%= year_current %></span>
												<span id="last-year" class="month"><%= month_current %></span>
											</div>
										</li>
										
										<%
									}
								%>
								
								<li><%= dayname[currentDate.get(Calendar.DAY_OF_WEEK)-1] %> <%= currentDate.get(Calendar.DATE) %> </li>
								<%
							}
						
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
		
		
		
	</div>

</div>


<jsp:include page="../footer1.jsp" />