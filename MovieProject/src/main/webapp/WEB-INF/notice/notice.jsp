<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<!-- CSS 스타일 추가 -->
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #fffcf2; /* 부드러운 아이보리 배경색 */
        margin: 0;
        padding: 0;
    }
    .container {
        width: 70%; /* 컨테이너 너비를 70%로 설정 */
        margin: 50px auto;
        background-color: #fff; /* 테이블 및 내용 배경 흰색 */
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .board-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
        color: #403d39; /* 딥한 브라운 색 */
    }
    .board-header h3 {
        margin: 0;
        font-size: 28px;
    }
    .board-header a {
        font-size: 18px;
        color: #eb5e28; /* 오렌지색 버튼 링크 */
        text-decoration: none;
    }
    .board-header a:hover {
        text-decoration: underline;
    }
    .board-table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 30px;
    }
    .board-table th, .board-table td {
        padding: 12px;
        text-align: center;
        border: 1px solid #ddd;
    }
    .board-table th {
        background-color: #ccc5b9; /* 밝은 베이지 색 */
        color: #252422; /* 어두운 회색 텍스트 */
    }
    .board-table td {
        background-color: #f9f9f9; /* 연한 회색 배경 */
    }
    .board-table td a {
        text-decoration: none;
        color: #007bff;
    }
    .board-table td a:hover {
        text-decoration: underline;
        color: #0056b3;
    }
    .pagination {
        list-style-type: none;
        padding: 0;
        text-align: center;
        margin-top: 20px;
    }
    .pagination li {
        display: inline;
        margin: 0 5px;
    }
    .pagination a {
        text-decoration: none;
        padding: 10px 15px;
        background-color: #eb5e28; /* 오렌지색 배경 */
        color: white;
        border-radius: 5px;
    }
    .pagination a:hover {
        background-color: #d75f1e; /* 어두운 오렌지색 */
    }
    .btn-create {
        padding: 12px 25px;
        background-color: #28a745; /* 초록색 버튼 */
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
    }
    .btn-create:hover {
        background-color: #218838; /* 어두운 초록색 */
    }
</style>
</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container">
    <!-- 공지사항 헤더 -->
    <div class="board-header">
        <h3>공지사항</h3>        
    </div>

    <!-- 공지 작성 버튼 -->
    <div style="text-align: center; margin-bottom: 20px;">
        <a href="<%= ctxPath %>/notice/noticeWrite.up">
            <button class="btn-create">
                공지작성
            </button>
        </a>
    </div>

    <!-- 공지사항 테이블 -->
    <table class="board-table">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>등록일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="notice" items="${requestScope.NoticeList}">
                <tr>
                    <td>${notice.seq_notice_no}</td> <!-- 공지사항 번호 -->
                    <td><a href="<%= ctxPath %>/notice/noticeDetail.up?seq=${notice.seq_notice_no}">${notice.notice_subject}</a></td>
                    <td>${notice.notice_wtite_date}</td>
                    <td>${notice.views}</td> <!-- 조회수 -->
                </tr>
            </c:forEach>
            <c:if test="${empty requestScope.NoticeList}">
                <tr>
                    <td colspan="4" style="text-align: center;">공지사항이 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <!-- 페이지네이션 -->
    <ul class="pagination" style="justify-content: center;">
        <li><a href="#">◀</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">▶</a></li>
    </ul>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>
</html>
