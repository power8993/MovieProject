<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();
    //     /MyMVC
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/index.css" />

<jsp:include page="header1.jsp" />



       

			<div id="content">
			
			<div class="a_box" style="width: 100%; height: 80vh; background-color: #000; padding-top: 40px; position: relative;">
            	<div class="embed-responsive embed-responsive-16by9" style="position: absolute; top: 0; left: 0; right: 0; bottom: 0;">
                <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/jNJz2JfTxQ4?autoplay=1" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen style="width: 100%; height: 100%;"></iframe>
           	 </div>
        	</div>
			
			<%-- 무비차트 시작  --%>
				<h3 id="movie_ct">무비차트</h3>
				<div id="cardCarousel" class="carousel slide" data-ride="carousel">
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
				<h3 id="movie_ct">무비차트</h3>
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
				
				<div id="notis">
					<div id="left">
						<h3>공지사항</h4>
						
					</div>
					<div id="right"></div>
					
				</div>
				
				<%-- 공지사항 시작 --%>
				
				
				
				<%-- 공지사항 끝 --%>
				
			</div><%-- end of content --%>
	
     </div>
        
        

<jsp:include page="footer1.jsp" /> 
    