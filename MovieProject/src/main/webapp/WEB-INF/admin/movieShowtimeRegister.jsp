<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/movieShowtimeRegister.css" >

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/movieShowtimeRegister.js"></script>

	<div class="showtime_register_container">
		<h2>상영일정 등록</h2>
		<form name="showtime_register" method="post" action="">
	        <table class="table" id="tbl_showtime_register">
	            <tbody>
	                <!-- 첫 번째 줄: 영화 및 상영 관련 정보 -->
	                <tr>
	                    <td rowspan="3"><img src="<%= ctxPath%>/images/admin/poster_file/${requestScope.mvvo.poster_file}" alt="" style="width:250px; height:auto;"></td>
	                    <td colspan="2">${requestScope.mvvo.movie_title}</td>
	                </tr>
	                <tr>
	                    <td>상영 시간<br><span>${requestScope.mvvo.running_time}</span>분</td>
	                    <td>상영 등급<br><img src="<%= ctxPath%>/images/admin/movie_grade/${requestScope.mvvo.movie_grade}.png" alt="${requestScope.mvvo.movie_grade}" style="width:30px; height:auto;"></td>
	                </tr>
	                <tr>
	                    <td>개봉 일자<br>${requestScope.mvvo.start_date}</td>
	                    <td>종영 일자<br>${requestScope.mvvo.end_date}</td>
	                </tr>
	
	                <!-- 두 번째 줄: 상영관 번호 -->
	                <tr>
	                    <td><span class="star">*&nbsp;</span>상영관 번호</td>
	                    <td colspan="1">
	                        <label for="screen_1">
	                            <input type="radio" name="screen_no" value="1" id="screen_1" /> 1관
	                        </label>
	                        <label for="screen_2">
	                            <input type="radio" name="screen_no" value="2" id="screen_2" /> 2관
	                        </label>
	                    </td>
	                </tr>
	
	                <!-- 세 번째 줄: 상영 시작 시간 / 상영 종료 시간 -->
	                <tr>
	                    <td colspan="3">
	                        <div class="form-row">
	                            <div class="col">
	                                <label for="start_time"><span class="star">*&nbsp;</span>상영 시작 시간</label>
	                                <input type="datetime-local" class="form-control" name="start_time" id="start_time" required>
	                            </div>
	                            <div class="col">
	                                <label for="end_time">상영 종료 시간</label>
	                                <input type="datetime-local" class="form-control" name="end_time" id="end_time" required>
	                            </div>
	                        </div>
	                        <div class="form-row">
	                        	<div class="col">
	                        		<button type="button" class="btn btn-primary" id="showtime_select_btn">상영시간 조회하기</button>
	                        	</div>
	                        	<div id="showtime_conflict_check_modal"></div>
	                        </div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>       
	        
	        <!-- 상영 종료 시간을 전송 -->
			<input type="hidden" name="end_time_val" id="end_time_val">
	        <input type="hidden" name="seq" value="${requestScope.mvvo.seq_movie_no}"/>
	        
	        <button type="submit" id="showtime_resister_btn" class="btn btn-success" value="등록하기">상영일정 등록하기</button>
		    <button type="reset" class="btn btn-danger" value="취소하기" onclick="confirmCancel()">취소하기</button>
	        
	    </form>
	    
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" /> 