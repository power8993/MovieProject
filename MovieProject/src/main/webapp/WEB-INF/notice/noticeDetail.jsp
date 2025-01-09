<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 상세</title>
    <!-- 스타일 추가 -->
    <style>
    
        /* 공지사항 제목 및 헤더 */
        .board-header {
            margin-top: 20px;
        }
        .board-header h3 {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 18px;
        }

        /* 공지사항 내용 */
        .notice-content {  		
    		width: 100%;
	        border-collapse: collapse;
	        margin-bottom: 10px;
	        border-radius: 10%;
		
			border-collapse: separate; /* 테이블 셀 사이를 분리하도록 설정 */
		    border-spacing: 0; /* 셀 간의 간격을 0으로 설정 */
		    border-radius: 5px; /* 테이블의 둥근 모서리 설정 */
		    overflow: hidden; /* 둥근 모서리가 잘리지 않도록 */
        }

        /* 공지사항 정보(제목, 날짜, 조회수) */
        .notice-info {
    		padding: 5px;
    		background-color: #333;
        	color: white;
		}
		
		.dayview {
			font-size: 13.5px;
			display: flex;
			justify-content: end;
		}

        .notice-info .title {
            font-size: 22px;
            margin-top: 8px;
            margin-left: 5px;
        }
        
        .notice-info .date, .notice-info .views {
    		margin-right: 10px;
		}
        

        /* 공지사항 내용 텍스트 */
        .notice-content p {
		    padding: 20px 10px;
		    border: solid 1px #b8b6aa;
		    line-height: 24px;
		    height: 300px; /* 고정된 높이 설정 */
		    overflow-y: auto; /* 내용이 넘치면 세로로 스크롤이 생김 */
		
			border-collapse: separate; /* 테이블 셀 사이를 분리하도록 설정 */
		    border-spacing: 0; /* 셀 간의 간격을 0으로 설정 */
		    border-radius: 4px; /* 테이블의 둥근 모서리 설정 */
		    overflow: hidden; /* 둥근 모서리가 잘리지 않도록 */
		}

         /* 버튼 컨테이너 */
		.button-container {
		    display: flex;
		    justify-content: space-between; /* 양 끝에 정렬 */
		    align-items: center; /* 세로 중앙 정렬 */
		    margin-bottom: 20px;
		}
		
		/* 목록으로 돌아가기 버튼 */
		.back-btn {
		    padding: 6px 17px;
		    color: white;
		    border: none;
		    border-radius: 5px;
		    font-size: 17px;
		    cursor: pointer;
		    background-color: #252422;
		    text-align: center;
		    display: inline-block;
		    text-decoration: none;
		    margin-left: 365px;
		}

		/* 수정 및 삭제 버튼 */
		.edit-btn, .delete-btn {
		    padding: 6px 17px;
		    color: white;
		    border: none;
		    border-radius: 5px;
		    font-size: 17px;
		    cursor: pointer;
		    background-color: #252422;
		    text-align: center;
		    display: inline-block;
		    margin: 5px; /* 버튼들 사이에 여백 추가 */
		}
		
		.edit-btn:hover, .delete-btn:hover, .back-btn:hover {
		    background-color: #eb5e28;
		    color: white;
		    text-decoration: none;
		}


    </style>
</head>
<body>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>
<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container">
    <div class="board-header">
        <h3><i class="fa-solid fa-list" style="color: #252422;"> 공지사항</i></h3>
    </div>

    <div class="notice-content">
        <!-- 제목, 등록일, 조회수를 한 줄로 나열 -->
        <div class="notice-info">
            <div class="title">[공지] &nbsp; ${notice.notice_subject}</div>
            <div class="dayview">
            <div class="date"><strong>등록일 </strong> &nbsp; ${notice.notice_wtite_date}</div>
            <div class="views"><strong>조회수 </strong> &nbsp; ${notice.views}</div>
            </div>
        </div>

        <!-- 공지사항 내용 출력 -->
        <p>${notice.notice_content}</p>
    </div>

    <div class="button-container">
        <!-- 목록으로 돌아가기 버튼 -->
        <a href="<%= ctxPath %>/notice/notice.mp" class="back-btn"><i class="fa-solid fa-house style=" > 목록으로 돌아가기</i></a>
		<div>
		<c:if test="${not empty sessionScope.loginuser and sessionScope.loginuser.userid == 'admin' }"> <!-- admin 으로 로그인 했으면 -->
	        <!-- 수정 버튼 (수정 페이지로 이동) -->
	        <a href="<%= ctxPath %>/notice/noticeEdit.mp?seq=${notice.seq_notice_no}&subject=${notice.notice_subject}&notice_content=${notice.notice_content}" class="edit-btn"><i class="fa-solid fa-pen-to-square"> 수정</i></a>
	
	        <form action="<%= ctxPath %>/notice/noticeDelete.mp" method="POST" style="display: inline; text-align: right;">
		    	<!-- seq_notice_no 값을 실제 공지사항 번호로 전달 -->
		    	<input type="hidden" name="seq_notice_no" value="${notice.seq_notice_no}">
		    	<button type="submit" class="delete-btn" onclick="return confirm('정말 삭제하시겠습니까?');"><i class="fa-solid fa-trash"> 삭제</i></button>
			</form>
		</c:if>
		</div>
    </div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>

</html>
