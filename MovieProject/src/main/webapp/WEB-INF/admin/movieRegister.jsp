<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/movieRegister.css" >

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/movieRegister.js" ></script>
  

	<div class="movie_register_container">
	    <h2>영화 등록</h2>
		  <form name="movie_register" method="post" action="<%= ctxPath%>/admin/movieRegister.up">
		    <table class="table" id="tbl_movie_register">
		      <tbody>
		        <!-- 첫 번째 줄: 영화 제목 -->
		        <tr>
		          <td>
		          	<div class="form-group">
		              	<label for="movie_title"><span class="star">*&nbsp;</span>영화제목</label>
		              	<!-- 영화 제목을 입력받는 텍스트 필드 -->
		           	 	<input type="text" name="movie_title" class="form-control" id="movie_title" placeholder="영화 제목을 입력하세요">
		            	<!-- 등록된 영화 조회 버튼 -->
		            	<button type="button" class="btn btn-primary mt-2" onclick="checkDuplicate()">등록된 영화 조회</button>
		            	<!-- 영화를 조회할 수 있는 팝업창이 나올 곳 -->
		            	<div id="movie_check_modal"></div>  <!-- 모달을 삽입할 위치 -->
		            </div>
		            
		          </td>
		        </tr>
		        
		        <!-- 두 번째 줄: 줄거리 -->
		        <tr>
		          <td>
		          	 <!-- 줄거리를 입력받는 텍스트 영역 -->
		          	<div class="form-group">
		              <label for="content"><span class="star">*&nbsp;</span>줄거리&nbsp; <span id="char_count" class="form-text text-muted">0 / 300</span></label>
		              <textarea class="form-control" name="content" id="content" rows="4" onkeyup="charCount(this,300)" placeholder="줄거리를 입력하세요."></textarea>
		            </div>
		          </td>
		        </tr>
		        
		        <!-- 세 번째 줄: 감독 / 배우 -->
		        <tr>
		        	<td>
					  <div class="form-row">
					    <div class="col">
					      <label for="director"><span class="star">*&nbsp;</span>감독</label>
					      <input type="text" id="director" name="director" class="form-control" placeholder="감독 이름을 입력하세요" autocomplete="off">
					      <div id="director_tags" class="d-flex flex-wrap mt-2">
					        <!-- 감독 이름이 나열되는 곳 -->
					      </div>
					    </div>
					    <div class="col">
					      <label for="actor"><span class="star">*&nbsp;</span>배우</label>
					      <input type="text" id="actor" name="actor" class="form-control" placeholder="배우 이름을 입력하세요" autocomplete="off">
					      <div id="actor_tags" class="d-flex flex-wrap mt-2">
					        <!-- 배우 이름이 나열되는 곳 -->
					      </div>
					    </div>
					  </div>
					</td>
		        </tr>	        
		
		        <!-- 네 번째 줄: 장르 코드 / 러닝타임 / 상영등급 -->
		        <tr>
		          <td>
		            <div class="form-row">
			            <div class="col">
			              <label for="fk_category_code"><span class="star">*&nbsp;</span>장르</label>
			              <select class="form-control" name="fk_category_code" id="fk_category_code">
			                <option value="">장르</option>
			                <option value="액션">액션</option>
			                <option value="코미디">코미디</option>
			                <option value="드라마">드라마</option>
			                <option value="스릴러">스릴러</option>
			                <option value="로맨스">로맨스</option>
			                <option value="sf">sf</option>
			                <option value="판타지">판타지</option>
			                <option value="애니메이션">애니메이션</option>
			                <option value="역사">역사</option>
			                <option value="범죄">범죄</option>
			                <option value="스포츠">스포츠</option>
			                <option value="느와르">느와르</option>
			              </select>
			            </div>
			            <div class="col">
			              <label for="running_time"><span class="star">*&nbsp;</span>러닝타임 (분)</label>
			              <input type="number" class="form-control" name="running_time" id="running_time" min="0" placeholder="러닝타임">
			            </div>
			            <div class="col">
			              <label for="movie_grade"><span class="star">*&nbsp;</span>상영등급</label>
			              <select class="form-control" name="movie_grade" id="movie_grade">
			                <option value="">상영등급</option>
			                <option value="전체">전체관람가</option>
			                <option value="12세">12세이상 관람가</option>
			                <option value="15세">15세이상 관람가</option>
			                <option value="19세">청소년관람불가</option>
			              </select>
			            </div>
		            </div>
		          </td>
		        </tr>
		
		        <!-- 다섯 번째 줄: 개봉일 / 상영 종료일 -->
		        <tr>
		          <td>
		            <div class="form-row">
			            <div class="col">
			              <label for="start_date">상영 시작일(개봉일)</label>
			              <input type="date" class="form-control" name="start_date" id="start_date">
			            </div>
			            <div class="col">
			              <label for="end_date">상영 종료일</label>
			              <input type="date" class="form-control" name="end_date" id="end_date">
			            </div>
		            </div>
		          </td>
		        </tr>
		
		        <!-- 여섯 번째 줄: 포스터 파일 / URL -->
		        <tr>
		          <td>
		            <div class="form-group">
		              <label for="poster_file">포스터 파일</label>
		              <input type="text" class="form-control" name="poster_file" id="poster_file" placeholder="파일명">
		            </div>
		            <div class="form-group">
		              <label for="video_url">예고편 URL</label>
		              <input type="url" class="form-control" name="video_url" id="video_url" placeholder="예고편 URL 입력">
		            </div>
		          </td>
		        </tr>
		      </tbody>
		    </table>
		
		    <button type="submit" id="resister_btn" class="btn btn-success" value="등록하기">영화등록하기</button>
		    <button type="reset" class="btn btn-danger" value="취소하기" onclick="goMovieReset()">취소하기</button>
		    
		  </form>
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" />  