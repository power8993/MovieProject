<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 수정하기</title>
    <style>
        /* 전체 배경 */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa; /* 밝은 회색 배경 */
            color: #403d39;
        }

        /* 컨테이너 설정 */
        .container {
            width: 60%; /* 컨테이너 너비 */
            margin: 40px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
        }

        /* 헤더 스타일 */
        .form-header {
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 10px;
            border-bottom: 3px solid #eb5e28; /* 주황색 강조 */
        }
        .form-header h3 {
            font-size: 28px;
            color: #403d39;
            font-weight: 600;
        }

        /* 입력 필드 및 텍스트 영역 */
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 8px;
            color: #252422;
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border-radius: 8px;
            border: 1px solid #ddd;
            transition: all 0.3s ease;
        }
        .form-group input:focus, .form-group textarea:focus {
            border-color: #eb5e28; /* 주황색 포커스 */
            box-shadow: 0 0 8px rgba(235, 94, 40, 0.5);
            outline: none;
        }

        /* 텍스트 영역 기본 높이 설정 */
        .form-group textarea {
            resize: vertical;
            min-height: 150px;
        }

        /* 제출 버튼 */
        .submit-btn {
            padding: 12px 25px;
            background-color: #eb5e28; /* 주황색 버튼 */
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .submit-btn:hover {
            background-color: #d85e1b; /* 어두운 주황색 */
            transform: translateY(-2px); /* 버튼 호버시 살짝 위로 */
        }

        /* 버튼을 중앙 정렬 */
        .form-group button {
            text-align: center;
            width: 100%;
        }

        /* 반응형 디자인: 화면이 좁아지면 입력 필드 및 버튼이 자동으로 줄어듬 */
        @media (max-width: 768px) {
            .container {
                width: 90%;
            }
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container">
    <div class="form-header">
        <h3>공지사항 수정하기</h3>
    </div>

    <!-- 공지사항 수정 폼 -->
    <form action="<%= ctxPath %>/notice/noticeDetail.up" method="post">
        <!-- notice_id는 hidden 필드로 전송되어 수정될 공지사항을 식별 -->
        <input type="hidden" name="seq_notice_no" value="seq_notice_no" />

        <div class="form-group">
            <label for="notice_subject">공지사항 제목</label>
            <input type="text" id="notice_subject" name="notice_subject" value="notice_subject" placeholder="공지사항 제목을 입력하세요" required />
        </div>

        <div class="form-group">
            <label for="notice_content">공지사항 내용</label>
            <textarea id="notice_content" name="notice_content" placeholder="공지사항 내용을 입력하세요" required></textarea>
        </div>

        <div class="form-group">
            <button type="submit" class="submit-btn">수정하기</button>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>
</html>
