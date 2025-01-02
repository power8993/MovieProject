<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
   String ctxPath = request.getContextPath();
%>


<jsp:include page="header1.jsp" />
<script type="text/javascript" src="<%= ctxPath%>/js/index/index.js"></script> 

         <div class="a_box" style="width: 100%; height: 80vh; background-color: #000; padding-top: 40px; position: relative; min-width:980px;">
               <div class="embed-responsive embed-responsive-16by9" style="position: absolute; top: 0; left: 0; right: 0; bottom: 0;">
                <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/jNJz2JfTxQ4?autoplay=1" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen style="width: 100%; height: 100%;"></iframe>
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
                            <div class="col mb-3 first-card" >
                                <div class="card">
                                    <div class="movieCard" style="position: relative; width: 170px; height: 234px; margin: 0 auto;">
									    <img src="${pageContext.request.contextPath}/images/admin/poster_file/미니언즈.jpg" class="card-img-top poster" style="width: 100%; height: 100%; object-fit: cover;">
									   
									</div>
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드2">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드3">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드4">
                                </div>
                            </div>
                            <div class="col mb-3 last-card">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드5">
                                </div>
                            </div>
                        </div>
                    </div>
            
                    <!-- 두 번째 카드 세트 -->
                    <div class="carousel-item">
                        <div class="d-flex justify-content-between">
                            <div class="col mb-3 first-card">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드1">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드2">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드3">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드4">
                                </div>
                            </div>
                            <div class="col mb-3 last-card">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드5">
                                </div>
                            </div>
                        </div>
                    </div>
            
                </div>
            
                <!-- 이전, 다음 버튼 -->
                <a class="carousel-control-prev" href="#cardCarousel" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">이전</span>
                </a>
                <a class="carousel-control-next" href="#cardCarousel" role="button" data-slide="next">
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
                            <div class="col mb-3 first-card">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드1">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드2">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드3">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드4">
                                </div>
                            </div>
                            <div class="col mb-3 last-card">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드5">
                                </div>
                            </div>
                        </div>
                    </div>
            
                    <!-- 두 번째 카드 세트 -->
                    <div class="carousel-item">
                        <div class="d-flex justify-content-between">
                            <div class="col mb-3 first-card">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드1">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드2">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드3">
                                </div>
                            </div>
                            <div class="col mb-3">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드4">
                                </div>
                            </div>
                            <div class="col mb-3 last-card">
                                <div class="card">
                                    <img src="https://via.placeholder.com/170x234" class="card-img-top" alt="카드5">
                                </div>
                            </div>
                        </div>
                    </div>
            
                </div>
            
                <!-- 이전, 다음 버튼 -->
                <a class="carousel-control-prev" href="#cardCarousel2" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">이전</span>
                </a>
                <a class="carousel-control-next" href="#cardCarousel2" role="button" data-slide="next">
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
		                  		<td style="width:390px;  padding:5px 0 5px 0; ">${notice.notice_subject}</td>
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
    