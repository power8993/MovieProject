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
        /* 전체 페이지 스타일 */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #fff5e6;
            color: #403d39; /* 어두운 회갈색 글씨 */
        }

        /* 페이지 컨테이너 스타일 */
        .container {
            width: 70%; /* 컨테이너 너비 */
            margin: 40px auto;
            padding: 30px;
            border-radius: 16px;
            display: table;
   			border-collapse: separate;
    		box-sizing: border-box;
    		text-indent: initial;
    		unicode-bidi: isolate;
    		border-spacing: 2px;
    		border-color: gray;        
        }

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
            font-family: 'Noto Sans KR', 'CJONLYONENEW', '맑은 고딕', '돋움', Dotum, sans-serif;
    		font-size: 100%;
    		margin: 0;
    		padding: 0;
    		border: 0;
    		vertical-align: baseline;
    		word-break: break-all;
        }

        /* 공지사항 정보(제목, 날짜, 조회수) */
        .notice-info {
        	display: flex;
            overflow: hidden;
    		padding: 11px;
    		border-top: solid 1px #b8b6aa;
    		background-color: #ccc5b9;
		}
        
        .notice-info .title {
            font-weight: 600;
            font-size: 22px;
            flex: 2;
        }
        
        .notice-info .date, .notice-info .views {
            overflow: hidden;
    		padding: 11px;
    		background-color: #ccc5b9;
		}
        

        /* 공지사항 내용 텍스트 */
        .notice-content p {
            padding: 35px 13px;
            background-color: #f9f9f9;
    	    border-bottom: solid 1px #b8b6aa;
    		line-height: 24px;
        }

       /* 버튼 컨테이너 */
		.button-container {
		    display: flex;
		    justify-content: space-between; /* 양 끝에 정렬 */
		    align-items: center; /* 세로 중앙 정렬 */
		    margin-top: 20px; /* 버튼들 사이 여백 */
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
		    margin: 0 auto; /* 가운대로 정렬 */
		}
		
		.back-btn:hover {
		    background-color: #eb5e28;
		    color: white;
		    text-decoration: none;
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
		
		.edit-btn:hover, .delete-btn:hover {
		    background-color: #eb5e28;
		    color: white;
		    text-decoration: none;
		}
		
		/* 삭제 버튼 스타일 */
		.delete-btn {
		    padding: 6px 17px;
		    color: white;
		    border: none;
		    border-radius: 5px;
		    font-size: 17px;
		    cursor: pointer;
		    background-color: #252422;
		    text-align: center;
		    display: inline-block;
		    margin: 5px;
		}
		
		.delete-btn:hover {
		    background-color: #eb5e28;
		    color: white;
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
            <div class="title"><h4>[공지] ${notice.notice_subject}</h4></div>
            <div class="date"><strong>등록일:</strong> ${notice.notice_wtite_date}</div>
            <div class="views"><strong>조회수:</strong> ${notice.views}</div>
        </div>

        <!-- 공지사항 내용 출력 -->
        <p>${notice.notice_content}</p>
    </div>

    <div class="button-container">
        <!-- 목록으로 돌아가기 버튼 -->
        <a href="<%= ctxPath %>/notice/notice.mp" class="back-btn"><i class="fa-solid fa-house style=" > 목록으로 돌아가기</i></a>

        <!-- 수정 버튼 (수정 페이지로 이동) -->
        <a href="<%= ctxPath %>/notice/noticeEdit.mp?seq=${notice.seq_notice_no}&subject=${notice.notice_subject}&notice_content=${notice.notice_content}" class="edit-btn"><i class="fa-solid fa-pen-to-square"> 수정</i></a>

        <form action="<%= ctxPath %>/notice/noticeDelete.mp" method="POST" style="display: inline; text-align: right;">
	    	<!-- seq_notice_no 값을 실제 공지사항 번호로 전달 -->
	    	<input type="hidden" name="seq_notice_no" value="${notice.seq_notice_no}">
	    	<button type="submit" class="delete-btn" onclick="return confirm('정말 삭제하시겠습니까?');"><i class="fa-solid fa-trash"> 삭제</i></button>
		</form>
    </div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>
</html>
