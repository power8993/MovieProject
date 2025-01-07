<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/admin.js"></script>

	<div class="admin_container">
		
		<div class="movie_header">
		    <h4>오늘의 영화 예매 현황</h4>
		    <div id="today_reserved" data-chart-type="pie" style="max-width: 200px;">
		    	<canvas id="today_reserved_chart"></canvas>
		    </div>
		    
		    <h4>전체 영화 예매 추이 (일간/월간/연간)</h4>
		    <div id="total_reserved" data-chart-type="line">
		    	<button id="day_btn">일간</button>
		    	<button id="month_btn">월간</button>
		    	<button id="year_btn">연간</button>
		    	<canvas id="total_day_reserved_chart"></canvas>
		    	<canvas id="total_month_reserved_chart"></canvas>
		    	<canvas id="total_year_reserved_chart"></canvas>
		    </div>
		    
		    <h4>방문자 수 추이</h4>
		    <div id="total_visitors" data-chart-type="area">
		    	<canvas id="total_visitors_chart"></canvas>
		    </div>
	    </div>
		
	</div>

	
	


	
<jsp:include page="/WEB-INF/admin_footer1.jsp" />  