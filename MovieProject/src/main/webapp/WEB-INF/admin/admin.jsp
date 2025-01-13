<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-annotation@1.0.0/dist/chartjs-plugin-annotation.min.js"></script>

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/admin.js"></script>

<style>
section#mycontent {
 	border: solid 1px #fff !important; 
	float: right;
	width: 79%; 
    padding: 0; 
    margin-bottom: 1%;
}
</style>

	<div class="admin_container">
		
		<div class="movie_header">
		
			<div id="statistics" style="display: flex; justify-content: space-between; width: 100%;">
				<div class="visit" style="flex: 1; align-items: center;">
					<div>오늘 방문자 수</div>
					<div id="today_visit_cnt"></div>
				</div>
				<div class="visit" style="flex: 1; align-items: center;">
					<div>총 방문자 수</div>
					<div id="total_visit_cnt"></div>
				</div>
				<div class="pay" style="flex: 1; align-items: center;">
					<div>오늘 매출액</div>
					<div id="today_pay_sum"></div>
				</div>
				<div class="pay" style="flex: 1; align-items: center;">
					<div>총 매출액</div>
					<div id="total_pay_sum"></div>
				</div>
			</div>
			
			<div class="chart_body"> 
		    	<div class="statistis_title">금일 예매 현황 및 매출액</div>
			    <div style="display: flex; width: 100%; align-items: center; height: auto;">
				   	<div id="today_reserved" data-chart-type="pie" style="align-items: center; display: flex; width: 230px; margin-right: 2%; margin-left: 3%;">
		            	<canvas id="today_reserved_chart"></canvas>
			        </div>
			
			        <div id="today_payment_amount" data-chart-type="bar" style="align-items: center; display: flex; width: 450px; margin: 2%;">
			            <canvas id="today_payment_chart"></canvas>
			        </div>
				</div>
		    </div>
		    
		    <div class="chart_body"> 
			    <div class="statistis_title">영화 예매 추이</div>
			    <div id="total_reserved" data-chart-type="line">
			    	<button id="day_btn" class="button btnBorder btnLightBlue">일간</button>
			    	<button id="month_btn" class="button btnBorder btnLightBlue">월간</button>
			    	<button id="year_btn" class="button btnBorder btnLightBlue">연간</button>
			    	<canvas id="total_reserved_chart"></canvas>
			    </div>
		    </div>
		    
   		    <div class="chart_body"> 
			    <div class="statistis_title">방문자 수 추이</div>
			    <div id="total_visitors" data-chart-type="area">
			    	<canvas id="total_visitors_chart"></canvas>
			    </div>
		    </div>
		    
	    </div>
		
	</div>

	
	


	
<jsp:include page="/WEB-INF/admin_footer1.jsp" />  