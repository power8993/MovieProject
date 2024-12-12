<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
        margin: 20px;
    }
    .container {
        width: 80%;
        margin: 0 auto;
    }
    .board-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
    }
    .board-header h3 {
        margin: 0;
        font-size: 24px;
    }
    .board-table {
        width: 100%;
        border-collapse: collapse;
    }
    .board-table th, .board-table td {
        padding: 10px;
        text-align: center;
        border: 1px solid #ddd;
    }
    .board-table th {
        background-color: #f4f4f4;
    }
    .board-table td a {
        text-decoration: none;
        color: #007bff;
    }
    .board-table td a:hover {
        text-decoration: underline;
    }
    .pagination {
        list-style-type: none;
        padding: 0;
        text-align: center;
    }
    .pagination li {
        display: inline;
        margin: 0 5px;
    }
    .pagination a {
        text-decoration: none;
        padding: 8px 12px;
        background-color: #007bff;
        color: white;
        border-radius: 5px;
    }
    .pagination a:hover {
        background-color: #0056b3;
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

    <!-- 공지사항 테이블 -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>등록일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${not empty requestScope.noticeList}">
                <c:forEach var="notice" items="${requestScope.noticeList}">
                    <tr>
                        <td>${notice.noticeId}</td>
                        <td><a href="<%= ctxPath %>/notice/detail/${notice.noticeId}">${notice.title}</a></td>
                        <td>${notice.Date}</td>
                        <td>${notice.status}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty requestScope.noticeList}">
                <tr>
                    <td colspan="4" style="text-align: center;">공지사항이 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <!-- 페이지네이션 (가상의 예시) -->
    <ul class="pagination" style="justify-content: center; margin-top: 20px;">
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