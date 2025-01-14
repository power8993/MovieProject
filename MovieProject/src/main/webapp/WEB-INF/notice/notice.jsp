<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        background-color: #fffcf2; /* 부드러운 아이보리 배경색 */
        margin: 0;
        padding: 0;
    }
    .container {
        width: 70%; /* 컨테이너 너비를 70%로 설정 */
        margin: 50px auto;
        padding: 30px;
    }
    
    .board-table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 30px;
        border-radius: 10%;
	
		border-collapse: separate; /* 테이블 셀 사이를 분리하도록 설정 */
	    border-spacing: 0; /* 셀 간의 간격을 0으로 설정 */
	    border-radius: 5px; /* 테이블의 둥근 모서리 설정 */
	    overflow: hidden; /* 둥근 모서리가 잘리지 않도록 */
    }
    .board-table th, .board-table td {
        padding: 12px;
        text-align: center;
        border: 1px solid #dbd7ce;
        
    }
    .board-table th {
        background-color: #333;
        color: white;
    }
    .board-table td a {
        text-decoration: none;
        color: #007bff;
    }
	body > div:nth-child(19) > table > tbody > tr:hover {
	    cursor: pointer;
	    background-color: #dbd7ce; 
	}
    
    .pagination {
			display: flex;
		    list-style: none;
		    border-radius: .25rem;
		    justify-content: center;  
		    margin-top: 20px;  
		}
		.pagination a {
		    padding: 10px 20px;
		    text-decoration: none;
		    border: 1px solid #ddd;
		    color: #403D39;
		    list-style: none;
		}
		
		.pagination a:hover {
		    background-color: #eb5e28;
		    color: #403D39;
		}

		.pagination .page-item.active .page-link {
		    background-color: #eb5e28;
		    border-color: #EB5E28;
		    color: white;
		    margin-left: 0;
		    border-top-left-radius: .25rem;
		    border-bottom-left-radius: .25rem;
		}
    .btn-create {
        padding: 6px 17px;
        background-color: #252422;
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 17px;
        cursor: pointer;
    }
    
    .btn-create:hover {
        background-color: #eb5e28;
        color: white;
    }
</style>
</head>
<body>
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>
<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container" style="background-color:#fffcf2; margin-top: 20px;">

    <!-- 공지 작성 버튼 -->
    <div style="display: flex; margin-bottom: 10px; justify-content: space-between;">
    	<h3><i class="fa-solid fa-list" style="color: #252422;"> 공지사항</i></h3>
    	<c:if test="${not empty sessionScope.loginuser and sessionScope.loginuser.userid == 'admin' }"> <!-- admin 으로 로그인 했으면 -->
	        <a href="<%= ctxPath %>/notice/noticeWrite.mp">
	            <button id=notice class="btn-create"><i class="fa-solid fa-pen"> 공지작성</i></button>
	        </a>
        </c:if>
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
            <c:forEach var="notice" items="${requestScope.NoticeList}" varStatus="status">
                <tr class="tr-table" onclick="location.href='<%= ctxPath %>/notice/noticeDetail.mp?seq=${notice.seq_notice_no}'">
                    <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
                    <fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" /> 
                    <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%>
          			<td>${(requestScope.totalNoticeCount) - (currentShowPageNo - 1) * sizePerPage - (status.index)}</td>
                    <td style="text-align: left;">${notice.notice_subject}</td>
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

    <div id="pageBar">
       <nav>
          <ul class="pagination">${requestScope.pageBar}</ul>
       </nav>
   </div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

</body>
</html>
