<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="movie.domain.MovieVO" %>

<% 
	String ctxPath = request.getContextPath(); 

	//request에서 mvvo 객체 가져오기
	MovieVO mvvo = (MovieVO) request.getAttribute("mvvo");
	
	// mvvo.start_date 값 가져오기
    String start_date_str = mvvo.getStart_date(); 
    String end_date_str = mvvo.getEnd_date(); 
    
	// 날짜 포맷
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    
 	// String을 Date로 변환
    Date start_date_d = inputFormat.parse(start_date_str);
    Date end_date_d = inputFormat.parse(end_date_str);
    
    String start_date = outputFormat.format(start_date_d) + ":00";
    String end_date = outputFormat.format(end_date_d) + ":00"; 
%>

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/movieShowtimeRegister.css" >

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/movieShowtimeRegister.js"></script>

	<div class="showtime_register_container">
		<div class="showtime_register_header">
			<h2>상영일정 등록</h2>
			<span><span class="star">*&nbsp;</span>표시는 <span class="star" id="star_text">필수 기재 사항</span>입니다.</span>
		 </div>
		<form name="showtime_register" method="post" action="">
	        <table class="table" id="tbl_showtime_register">
	            <tbody>
	                <!-- 첫 번째 줄: 영화 및 상영 관련 정보 -->
	                <tr>
	                    <td colspan="2" rowspan="3" style="width: 45%;"><img src="<%= ctxPath%>/images/admin/poster_file/${requestScope.mvvo.poster_file}.jpg" alt="" style="width:250px; height:auto;"></td>
	                    <td colspan="2" style="width: 55%;" class="movie_title">${requestScope.mvvo.movie_title}</td>
	                </tr>
	                <tr>
	                    <td>상영 시간<br><span class="text_ignore">${requestScope.mvvo.running_time}&nbsp;분</span></td>
	                    <td>상영 등급<br><img src="<%= ctxPath%>/images/admin/movie_grade/${requestScope.mvvo.movie_grade}.png" alt="${requestScope.mvvo.movie_grade}" style="width:40px; height:auto; margin-left:4%; padding-top:3%;"></td>
	                </tr>
	                <tr>
	                    <td>개봉 일자<br><span class="text_ignore">${requestScope.mvvo.start_date}</span></td>
	                    <td>종영 일자<br><span class="text_ignore">${requestScope.mvvo.end_date}</span></td>
	                </tr>
	
	                <!-- 두 번째 줄: 상영관 번호 -->
	                <tr>
	                    <td class="text_ignore" style="width: 10%;"><span class="star">*&nbsp;</span>상영관 번호</td>
					    <td class="text_ignore text_label" style="width: 80%;" colspan="3">
					        <label for="screen_1" style="display: inline-flex; align-items: center; margin-right: 30px;">
					            <input type="radio" name="screen_no" value="1" id="screen_1" />
					            <span class="text_ignore" style="margin-left: 10px;">1관</span>
					        </label>
					        <label for="screen_2" style="display: inline-flex; align-items: center;">
					            <input type="radio" name="screen_no" value="2" id="screen_2" />
					            <span class="text_ignore" style="margin-left: 10px;">2관</span>
					        </label>
					    </td>
	                </tr>
	
	                <!-- 세 번째 줄: 상영 시작 시간 / 상영 종료 시간 -->
	                <tr>
	                    <td colspan="4" class="text_ignore">
	                        <div class="form-row">
	                            <div class="col">
	                                <label for="start_time"><span class="star">*&nbsp;</span>상영 시작 시간</label>
	                                <input type="datetime-local" class="form-control" name="start_time" id="start_time" required min="<%= start_date %>" max="<%= end_date %>">
	                            </div>
	                            <div class="col">
	                                <label for="end_time">상영 종료 시간</label>
	                                <input type="datetime-local" class="form-control" name="end_time" id="end_time" required>
	                            </div>
	                        </div>
	                        <div class="form-row">
	                        	<div class="col">
	                        		<button type="button" class="btn btn-primary" id="showtime_select_btn">상영 일정 조회</button>
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
	        
	        <div class="d-flex justify-content-end btns">
	        	<button type="submit" id="showtime_resister_btn" class="btn btn-success ml-auto" value="등록하기">상영일정 등록하기</button>
		    	<button type="reset" id="reset_btn" class="btn btn-danger" value="취소하기" onclick="confirmCancel()">취소하기</button>
	        </div>
	    </form>
	    
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" /> 