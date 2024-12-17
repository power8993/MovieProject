<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 작성</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            width: 70%; /* 컨테이너 너비 */
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
        }
        .form-header {
            margin-bottom: 20px;
        }
        .form-header h3 {
            font-size: 24px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            font-size: 16px;
            display: block;
            margin-bottom: 5px;
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }
        .form-group textarea {
            resize: vertical;
            height: 150px; /* 기본 텍스트 영역 높이 설정 */
        }
        .submit-btn {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .submit-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container">
    <div class="form-header">
        <h3>공지사항 작성</h3>
    </div>

    <!-- 공지사항 작성 폼 -->
    <form action="<%= ctxPath %>/notice/noticeWrite.up" method="post">
        <div class="form-group">
            <label for="notice_subject">공지사항 제목</label>
            <input type="text" id="notice_subject" name="notice_subject" required placeholder="공지사항 제목을 입력하세요" />
        </div>

        <div class="form-group">
            <label for="notice_content">공지사항 내용</label>
            <textarea id="notice_content" name="notice_content" required placeholder="공지사항 내용을 입력하세요"></textarea>
        </div>

        <div class="form-group" style="text-align: center;">
            <button type="submit" class="submit-btn">등록하기</button>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>
</html>
