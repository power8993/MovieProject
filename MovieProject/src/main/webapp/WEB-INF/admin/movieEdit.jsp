<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String ctxPath = request.getContextPath(); %>

<jsp:include page="/WEB-INF/admin_header1.jsp" />

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/movieEdit.css" >

<%-- 직접 만든 Javascript --%>
<script type="text/javascript" src="<%= ctxPath%>/js/admin/movieEdit.js"></script>

<%-- 수정하기에서 줄거리 입력을 위한 Javascript --%>
<script type="text/javascript">
$(document).ready(function(){
	
	// === 기존의 '줄거리' 값 넣기 === //
	var content = "${requestScope.mvvo.content}";
	content = content.replace(/<br>/g, "\n");   
	
	$("textarea#content").val(content);
	charCount(document.getElementById("content"), 500);
	
	//========================================================================//
	
	// === 기존의 '감독' 값 넣기 === //
    const director_list = $("div#director_tags"); 			// 태그를 넣을 위치
    const director_value = "${requestScope.mvvo.director}"; // 서버에서 받아온 값

    if(director_value.includes(",")) {
        // 감독이 여러 명일 경우
        const director_arr = director_value.split(",").map(function(director) {
            return director.trim(); // 공백 제거 후 배열로 변환
        });
        
        // 감독 태그 추가
        director_arr.forEach(function(director_name) {
            if(director_name.trim() !== "") {
                const director_tag = "<div class='director_tag'>" + 
                                      director_name +
                                      "<button type='button' class='remove_director'>x</button>" +
                                      "</div>";
                director_list.append(director_tag); 
            }
        });
    }
    else {
        // 감독이 한 명일 경우
        const director_name = director_value.trim();

        if(director_name !== "") {
            const director_tag = "<div class='director_tag'>" + 
                                  	director_name + 
                                  	"<button type='button' class='remove_director'>x</button>" +
                                  "</div>";
            director_list.append(director_tag); 
        }
    }
    
    //========================================================================//
    
 	// === 기존의 '배우' 값 넣기 === //
    const actor_list = $("div#actor_tags"); 			// 태그를 넣을 위치
    const actor_value = "${requestScope.mvvo.actor}"; // 서버에서 받아온 값

    if(actor_value.includes(",")) {
        // 배우가 여러 명일 경우
        const actor_arr = actor_value.split(",").map(function(actor) {
            return actor.trim(); // 공백 제거 후 배열로 변환
        });
        
        // 배우 태그 추가
        actor_arr.forEach(function(actor_name) {
            if(actor_name.trim() !== "") {
                const actor_tag = "<div class='actor_tag'>" + 
                					  actor_name +
                                      "<button type='button' class='remove_actor'>x</button>" +
                                   "</div>";
               actor_list.append(actor_tag); 
            }
        });
    }
    else {
        // 배우가 한 명일 경우
        const actor_name = actor_value.trim();

        if(actor_name !== "") {
            const actor_tag = "<div class='actor_tag'>" + 
            					  actor_name + 
                                  "<button type='button' class='remove_actor'>x</button>" +
                              "</div>";
           actor_list.append(actor_tag); 
        }
    }

    //========================================================================//
    
    // === 기존의 '장르' 값 넣기 === //
    const category ="${requestScope.mvvo.catevo.category}"
    $("select[name='fk_category_code']").val(category);
	
 	// === 기존의 '상영등급' 값 넣기 === //
    const movie_grade ="${requestScope.mvvo.movie_grade}"
    $("select[name='movie_grade']").val(movie_grade);

});
</script>
  

	<div class="movie_register_container">
	    <div class="movie_header" style="background-color: #FFFCF2;">
		    <h2>영화 수정</h2>
		    <span><span class="star">*&nbsp;</span>표시는 <span class="star">필수 기재 사항</span>입니다.</span>
	    </div>
		  <form name="movie_register" method="post" action="<%= ctxPath%>/admin/movieEditEnd.mp">
		    <table class="table" id="tbl_movie_register">
		      <tbody>
		        <!-- 첫 번째 줄: 영화 제목 -->
		        <tr>
		          <td>
		          	<div class="form-group">
		              	<label for="movie_title" class="info_text"><span class="star">*&nbsp;</span>영화제목</label>
		              	<!-- 영화 제목을 입력받는 텍스트 필드 -->
		           	 	<input type="text" name="movie_title" class="form-control" id="movie_title" value="${requestScope.mvvo.movie_title}" placeholder="영화 제목을 입력하세요">
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
		              	<div class="movie_content">
			            	<label for="content" class="info_text"><span class="star">*&nbsp;</span>줄거리&nbsp;</label>
			            	<span id="char_count" class="form-text text-muted">0 / 500</span>
		              	</div>
		              <textarea class="form-control" name="content" id="content" rows="4" onkeyup="charCount(this,500)" placeholder="줄거리를 입력하세요."></textarea>
		            </div>
		          </td>
		        </tr>
		        
		        <!-- 세 번째 줄: 감독 / 배우 -->
		        <tr>
		        	<td>
					  <div class="form-row">
					    <div class="col">
					      <label for="director" class="info_text"><span class="star">*&nbsp;</span>감독</label>
					      <input type="text" id="director" name="director" class="form-control" placeholder="감독 이름을 입력 후 엔터 키를 눌러 추가하세요." autocomplete="off">
					      <div id="director_tags" class="d-flex flex-wrap mt-2">
					        <!-- 감독 이름이 나열되는 곳 -->
					      </div>
					    </div>
					    <div class="col">
					      <label for="actor" class="info_text"><span class="star">*&nbsp;</span>배우</label>
					      <input type="text" id="actor" name="actor" class="form-control" placeholder="배우 이름을 입력 후 엔터 키를 눌러 추가하세요." autocomplete="off">
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
			              <label for="fk_category_code" class="info_text"><span class="star">*&nbsp;</span>장르</label>
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
			              <label for="running_time" class="info_text"><span class="star">*&nbsp;</span>러닝타임 (분)</label>
			              <input type="number" class="form-control" name="running_time" id="running_time" min="0" value="${requestScope.mvvo.running_time}" placeholder="러닝타임">
			            </div>
			            <div class="col">
			              <label for="movie_grade" class="info_text"><span class="star">*&nbsp;</span>상영등급</label>
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
			              <label for="start_date" class="info_text">상영 시작일(개봉일)</label>
			              <input type="date" class="form-control" name="start_date" id="start_date" value="${requestScope.mvvo.start_date}">
			            </div>
			            <div class="col">
			              <label for="end_date" class="info_text">상영 종료일</label>
			              <input type="date" class="form-control" name="end_date" id="end_date" value="${requestScope.mvvo.end_date}">
			            </div>
		            </div>
		          </td>
		        </tr>
		
		        <!-- 여섯 번째 줄: 포스터 파일 / URL -->
		        <tr>
		          <td>
		            <div class="form-group">
		              <label for="poster_file" class="info_text">포스터 파일</label>
		              <input type="text" class="form-control" name="poster_file" id="poster_file" value="${requestScope.mvvo.poster_file}" placeholder="파일명">
		            </div>
		            <div class="form-group">
		              <label for="video_url" class="info_text">예고편 URL</label>
		              <input type="url" class="form-control" name="video_url" id="video_url" value="${requestScope.mvvo.video_url}" placeholder="예고편 URL 입력">
		            </div>
		          </td>
		        </tr>
		      </tbody>
		    </table>
			
			<input type="hidden" name="seq" value="${requestScope.mvvo.seq_movie_no}"/>
			
			<div class="d-flex justify-content-end btns">
			    <button type="submit" id="edit_btn" class="btn btn-success ml-auto" value="등록하기">영화수정하기</button>
			    <button type="button" id="reset_btn" class="btn btn-danger" value="취소하기" onclick="confirmCancel()">취소하기</button>
		    </div>
		  </form>
		  
	</div>

<jsp:include page="/WEB-INF/admin_footer1.jsp" />  