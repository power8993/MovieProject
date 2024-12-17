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
            background-color: #fff5e6; /* 부드러운 주황색 배경 */
            color: #403d39; /* 어두운 회갈색 글씨 */
        }

        /* 페이지 컨테이너 스타일 */
        .container {
            width: 70%; /* 컨테이너 너비 */
            margin: 40px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 16px;           
        }

        /* 공지사항 제목 및 헤더 */
        .board-header {
            text-align: center;
            margin-bottom: 40px;
            border-bottom: 1px solid #eb5e28; /* 주황색 구분선 */
            padding-bottom: 15px;
        }
        .board-header h3 {
            font-size: 36px;
            color: #403d39;
            font-weight: bold;
            letter-spacing: -1px;
        }

        /* 공지사항 내용 */
        .notice-content {
            background-color: #fff8f2; /* 부드러운 밝은 주황색 */
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 30px;
        }

        /* 공지사항 정보(제목, 날짜, 조회수) */
        .notice-info {
            display: flex;
            justify-content: space-between;
            font-size: 18px;
            margin-bottom: 20px;
            color: #403d39;
        }
        .notice-info .title {
            font-weight: 600;
            font-size: 22px;
            color: #252422;
            flex: 2;
        }
        .notice-info .date, .notice-info .views {
            text-align: right;
            flex: 1;
            font-size: 16px;
            color: #ccc5b9; /* 연한 회색 */
        }

        /* 공지사항 내용 텍스트 */
        .notice-content p {
            font-size: 16px;
            line-height: 1.6;
            color: #403d39;
            margin-top: 20px;
        }

        /* 버튼들 */
        .back-btn, .edit-btn, .delete-btn {
            display: inline-block;
            padding: 12px 28px;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            cursor: pointer;
            font-weight: 600;
            text-align: center;
            transition: all 0.3s ease;
        }

        /* 목록으로 돌아가기 버튼 */
        .back-btn {
            background-color: #eb5e28; /* 주황색 */
            color: white;
        }
        .back-btn:hover {
            background-color: #d85e1b; /* 어두운 주황색 */
            transform: translateY(-2px); /* 살짝 위로 */
        }

        /* 수정 버튼 */
        .edit-btn {
            background-color: #ff7f32; /* 밝은 주황색 */
            color: white;
            margin-right: 15px;
        }
        .edit-btn:hover {
            background-color: #e56a1e; /* 어두운 주황색 */
            transform: translateY(-2px); /* 살짝 위로 */
        }

        /* 삭제 버튼 */
        .delete-btn {
            background-color: #dc3545; /* 빨간색 */
            color: white;
        }
        .delete-btn:hover {
            background-color: #c82333; /* 어두운 빨간색 */
            transform: translateY(-2px); /* 살짝 위로 */
        }

        /* 버튼들이 위치할 컨테이너 */
        .button-container {
            text-align: center;
            margin-top: 40px;
        }

        /* 구분선 스타일 */
        hr {
            border: 0;
            height: 1px;
            background: linear-gradient(to right, #eb5e28, #eb5e28);
            margin: 30px 0;
        }

        /* 반응형 디자인 */
        @media (max-width: 768px) {
            .container {
                width: 90%;
                padding: 20px;
            }
            .board-header h3 {
                font-size: 28px;
            }
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container">
    <div class="board-header">
        <h3>공지사항</h3>
    </div>

    <div class="notice-content">
        <!-- 제목, 등록일, 조회수를 한 줄로 나열 -->
        <div class="notice-info">
            <div class="title"><h4>${notice.notice_subject}</h4></div>
            <div class="date"><strong>등록일:</strong> ${notice.notice_wtite_date}</div>
            <div class="views"><strong>조회수:</strong> ${notice.views}</div>
        </div>

        <!-- 구분선 -->
        <hr>

        <!-- 공지사항 내용 출력 -->
        <p>${notice.notice_content}</p>
    </div>

    <div class="button-container">
        <!-- 목록으로 돌아가기 버튼 -->
        <a href="<%= ctxPath %>/notice/notice.up" class="back-btn">목록으로 돌아가기</a>

        <!-- 수정 버튼 (수정 페이지로 이동) -->
        <a href="<%= ctxPath %>/notice/noticeEdit.up" class="edit-btn">수정</a>

        <!-- 삭제 버튼 (삭제 확인 후 삭제 처리) -->
        <form action="<%= ctxPath %>/notice/notice.up" method="POST" style="display: inline;">
            <input type="hidden" name="seq_notice_no" value="seq_notice_no">
            <button type="submit" class="delete-btn" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</button>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>
</html>
