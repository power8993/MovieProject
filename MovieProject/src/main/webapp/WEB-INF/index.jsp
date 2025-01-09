<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
   String ctxPath = request.getContextPath();
%>


<jsp:include page="header1.jsp" />
<script type="text/javascript" src="<%= ctxPath%>/js/index/index.js"></script> 
<input type="hidden" value="<%=ctxPath%>" name="forJavaScript">

<%-- <c:forEach var="movie" items="${movies}">
    <div>${movie.showvo.seq_showtime_no},${movie.poster_file},${movie.movie_title}</div>
</c:forEach> --%>

		<div id="customVideoContainer">
			<div id="customVideoPositionRelative">
			<video id="customVideo" src="<%=ctxPath%>/images/index/LionKing.mp4" autoplay loop muted></video>

			<!-- 커스텀 컨트롤 -->
				<div id="trailerContentElmt">
					<p id="trailerTitle">무파사:라이온 킹</p>
					<p id="trailerInfo">	
						‘라이온 킹’ 탄생 30주년 기념작<br>
						외로운 고아에서 전설적인 왕으로 거듭난 ‘무파사’의 숨겨진 이야기가 베일을 벗는다!
					</p>
					<div id="videoControls">

					<button id="trailerShowDetailBtn">상세보기</button>

					<!-- 재생/일시정지 버튼 -->
					<button id="playPauseBtn">
						<i class="fa-solid fa-pause" style="color:white"></i>
					</button>
					<!-- 볼륨 켜기/끄기 버튼 -->
					<button id="muteBtn">
						<i class="fa-solid fa-volume-xmark" style="color:white"></i>
					</button>
					</div>
				</div>
				<!-- 그라데이션 효과를 위한 오버레이 -->
				<div id="overlayForgradation"></div>
			</div>
		</div>
		<%-- 무비차트 시작  --%>
         <div id="content">
            <h3 id="movie_ct"  style="font-weight:700;">무비차트</h3>
            <div id="cardCarousel" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                    <!-- 첫 번째 카드 세트 -->
                    <div class="carousel-item active">
                        <div class="d-flex justify-content-between">
                            
                            <c:forEach var="movie" items="${movies}" varStatus="status">
							    <div class="col mb-3 first-card" >
                                <div class="card">
	                                <form name="MovieForm" action="<%=ctxPath%>/movie/movieDetail.mp">
	                                    <div id="movieGradeElmt"class="movieCard">
		                                    <%-- 상영등급 --%>
		                                    <div style="position: absolute; right:10px; top:10px;">
		                                    <c:choose>
											    <c:when test="${movie.movie_grade == '전체'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/전체.png" class="movieGrade">
											    </c:when>
											    <c:when test="${movie.movie_grade == '15세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/15세.png" class="movieGrade">
											    </c:when>
											    <c:when test="${movie.movie_grade == '12세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/12세.png" class="movieGrade">
											    </c:when>
											    <c:when test="${movie.movie_grade == '19세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/19세.png"  class="movieGrade">
											    </c:when>
											</c:choose>
		                                    </div>
		                                    <%-- 영화 예매 순위 --%>
		                                    <div class="movieRank">${status.index + 1}</div>
		                                    
		                                    
		                                    
										    <img src="${pageContext.request.contextPath}/images/admin/poster_file/${movie.poster_file}" class="card-img-top poster" style="width: 100%; height: 100%; object-fit: cover;">
										   	<input style="margin-top:50px;" type="hidden" name="seq_showtime_no" value="${movie.showvo.seq_showtime_no}">
										   	<input style="margin-top:50px;" type="hidden" name="seq_movie_no" value="${movie.seq_movie_no}">
										</div>
									</form>
                                </div>
								<p class="movieTitle">${movie.movie_title}</p>
								<p class="bookingRate">예매율:${movie.bookingRate}%</p>
                            </div>
							</c:forEach>
                            
                        </div>
                    </div>
            
                    <!-- 두 번째 카드 세트 -->
                    <div class="carousel-item">
                        <div class="d-flex justify-content-between">
                            <c:forEach var="movie2" items="${movies2}" varStatus="status">
							    <div class="col mb-3 first-card" >
                                <div class="card">
	                                <form name="MovieForm" action="<%=ctxPath%>/movie/movieDetail.mp">
	                                    <div id="movieGradeElmt"class="movieCard">
	                                    	<%-- 상영등급 --%>
		                                    <div style="position: absolute; right:10px; top:10px;">
		                                    <c:choose>
											    <c:when test="${movie2.movie_grade == '전체'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/전체.png" class="movieGrade">
											    </c:when>
											    <c:when test="${movie2.movie_grade == '15세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/15세.png" class="movieGrade">
											    </c:when>
											    <c:when test="${movie2.movie_grade == '12세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/12세.png" class="movieGrade">
											    </c:when>
											    <c:when test="${movie2.movie_grade == '19세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/19세.png" class="movieGrade">
											    </c:when>
											</c:choose>
		                                    </div>
		                                    
		                                    <%-- 영화 예매 순위 --%>
		                                    <div class="movieRank">${status.index + 6}</div>
		                                    
		                                    
										    <img src="${pageContext.request.contextPath}/images/admin/poster_file/${movie2.poster_file}" class="card-img-top poster" style="width: 100%; height: 100%; object-fit: cover;">
										   	<input type="hidden" name="seq_showtime_no" value="${movie2.showvo.seq_showtime_no}">
										   	<input style="margin-top:50px;" type="hidden" name="seq_movie_no" value="${movie2.seq_movie_no}">
										</div>
									</form>
                                </div>
								<p class="movieTitle">${movie2.movie_title}</p>
								<p class="bookingRate">예매율:${movie2.bookingRate}%</p>
                            </div>
							</c:forEach>
                        </div>
                    </div>
            
                </div>
            
                <!-- 이전, 다음 버튼 -->
                <a style="display:inline-block;background-color: gray; width:30px; height:30px; border-radius: 50%;" class="carousel-control-prev" href="#cardCarousel" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">이전</span>
                </a>
                <a style="display:inline-block;background-color: gray; width:30px; height:30px; border-radius: 50%;" class="carousel-control-next" href="#cardCarousel" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">다음</span>
                </a>
            </div>
            <%-- 무비차트 끝 --%>
            
            
            
            
            <%-- 상영예정작 시작  --%>
            <h3 id="movie_ct" style="font-weight:700;">상영예정작</h3>
            <div id="cardCarousel2" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                    <!-- 첫 번째 카드 세트 -->
                    <div class="carousel-item active">
                        <div class="d-flex justify-content-between">
                            
							<c:forEach var="movie" items="${laterMovies}" varStatus="status">
							    <div class="col mb-3 first-card" >
	                                <div class="card">
		                                <form name="MovieForm" action="<%=ctxPath%>/movie/movieDetail.mp">
		                                <div id="movieGradeElmt">
		                                    <div class="movieCard" >
											    <img src="${pageContext.request.contextPath}/images/admin/poster_file/${movie.poster_file}" class="card-img-top poster" style="width: 100%; height: 100%; object-fit: cover;">
											   	<input style="margin-top:50px;" type="hidden" name="seq_movie_no" value="${movie.seq_movie_no}">
											
										<%-- 상영등급 --%>
		                                    <div class="movieGradeChoose" style="position: absolute; right:10px; top:10px;">
		                                    <c:choose>
											    <c:when test="${movie.movie_grade == '전체'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/전체.png" alt="전체" class="movieGrade">
											    </c:when>
											    <c:when test="${movie.movie_grade == '15세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/15세.png" alt="15세" class="movieGrade">
											    </c:when>
											    <c:when test="${movie.movie_grade == '12세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/12세.png" alt="12세" class="movieGrade">
											    </c:when>
											    <c:when test="${movie.movie_grade == '19세'}">
											        <img src="<%= ctxPath%>/images/admin/movie_grade/19세.png" alt="19세" class="movieGrade">
											    </c:when>
											</c:choose>
		                                    </div>	
										<%-- (개봉일 - 현재날짜) --%>	
										<div class="remaining_day" style="position:absolute; width:20px;height:20px; text-align:center; top:40px;  right:10px; z-index:4; border-radius:5px; font-size:8pt; background-color: white; color:red; font-weight: 700;" >D-${movie.remaining_day}</div>
										
										<%-- 개봉일에 가까운 순위 --%>
		                                    <div class="movieRank">${status.index + 1}</div>
		                                    </div>
										</div>
										</form>
	                                </div>
									<p class="movieTitle">${movie.movie_title} </p>
                            	</div>
							</c:forEach>

                        </div>
                    </div>
            
                    <!-- 두 번째 카드 세트 -->
                    <div class="carousel-item">
                        <div class="d-flex justify-content-between">
                            <c:forEach var="movie" items="${laterMovies2}" varStatus="status">
							    <div class="col mb-3 first-card" >
	                                <div class="card">
		                                <form name="MovieForm" action="<%=ctxPath%>/movie/movieDetail.mp">
			                                <div id="movieGradeElmt">
			                                    <div class="movieCard" >
												    <img src="${pageContext.request.contextPath}/images/admin/poster_file/${movie.poster_file}" class="card-img-top poster" style="width: 100%; height: 100%; object-fit: cover;">
												   	<input style="margin-top:50px;" type="hidden" name="seq_movie_no" value="${movie.seq_movie_no}">
												<div  class="remaining_day" style="position:absolute; width:20px;height:20px; text-align:center; top:40px;  right:10px; z-index:4; border-radius:5px; font-size:8pt; background-color: white; color:red; font-weight: 700;" >D-${movie.remaining_day}</div>
												<%-- 상영등급 --%>
			                                    <div class="movieGradeChoose" style="position: absolute; right:10px; top:10px;">
			                                    <c:choose>
												    <c:when test="${movie.movie_grade == '전체'}">
												        <img src="<%= ctxPath%>/images/admin/movie_grade/전체.png" alt="전체" class="movieGrade">
												    </c:when>
												    <c:when test="${movie.movie_grade == '15세'}">
												        <img src="<%= ctxPath%>/images/admin/movie_grade/15세.png" alt="15세" class="movieGrade">
												    </c:when>
												    <c:when test="${movie.movie_grade == '12세'}">
												        <img src="<%= ctxPath%>/images/admin/movie_grade/12세.png" alt="12세" class="movieGrade">
												    </c:when>
												    <c:when test="${movie.movie_grade == '19세'}">
												        <img src="<%= ctxPath%>/images/admin/movie_grade/19세.png" alt="19세" class="movieGrade">
												    </c:when>
												</c:choose>
			                                    </div>	
											<%-- (개봉일 - 현재날짜) --%>
		                                    <div class="movieRank" >${status.index + 6}</div>
												</div>
											</div>
										</form>
	                                </div>
									<p class="movieTitle">${movie.movie_title}</p>
                            	</div>
							</c:forEach>
                        </div>
                    </div>
            
                </div>
            
                <!-- 이전, 다음 버튼 -->
                <a style="display:inline-block;background-color: gray; width:30px; height:30px; border-radius: 50%;" class="carousel-control-prev" href="#cardCarousel2" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">이전</span>
                </a>
                <a style="display:inline-block;background-color: gray; width:30px; height:30px; border-radius: 50%;" class="carousel-control-next" href="#cardCarousel2" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">다음</span>
                </a>
            </div>
            <%-- 상영예정작 끝 --%>
            
            
            <%-- 공지사항 시작 --%>
            <div id="notis">
               <div id="left">
               
                  <h3>공지사항</h3>
                  <table>
                  <c:if test="${not empty noticeList}"> <%-- 공지사항이 있을 경우 --%>
                  	<tr style="border-bottom: 1px solid #cccccc;border-top: 1px solid #cccccc;">
                  		<td style="width:390px; padding:5px 0 5px 0;">제목</td>
                  		<td style="width:100px;">등록일</td>
                  	</tr>
	                  	<c:forEach var="notice" items="${noticeList}">  
		                  	<tr style="border-bottom: 1px solid #cccccc;">
		                  		<td style="width:390px;  padding:5px 0 5px 0;">
			                  		<span class="notice_subject" style="cursor: pointer;">${notice.notice_subject}</span>
			                  		<input type="hidden" value="${notice.seq_notice_no}"name="seq">
		                  		</td>
		                  		<td style="width:100px;">2025-01-02</td>
		                  	</tr>
	                  	</c:forEach>
                  	</c:if>
                  	
                  	<c:if test="${empty noticeList}"><%-- 공지사항이 없을 경우 --%>
	                  	<p style="line-height:29px;">
						    현재 공지사항이 없습니다. <br>새로운 이벤트, 업데이트, 또는 중요한 정보가 준비되는 즉시 
						    이곳에서 안내드릴 예정입니다.<br>궁금한 사항이 있으시다면 언제든 고객센터를 방문해주세요.<br> 
						    항상 저희 서비스를 이용해 주셔서 감사합니다!
						</p>

                  	</c:if>
                  </table>
               </div>
               
               
               
               <div id="right">
               		<div id="rightContent">
               			<h5>고객센터</h5>
               			<p style="font-size:22pt; font-weight:500;">1544-1122</p>
               			<p style="color:gray;">고객센터 운영시간 (평일 09:00~18:00)<br>업무시간 외 자동응답 안내 가능합니다.</p>
               		</div>
               </div>
               
            </div>
            <%-- 공지사항 끝 --%>
            
         </div><%-- end of content --%>
        

<jsp:include page="footer1.jsp" /> 
    