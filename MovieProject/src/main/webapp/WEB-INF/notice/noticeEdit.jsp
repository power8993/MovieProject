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
        .form-header h3 {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 18px;
            margin-top: 20px;
        }
        .form-group {
            margin-bottom: 0 !important;
        }
        .form-group label {
            padding: 5px;
    		background-color: #333;
        	color: white;
        	width: 100%;
        	margin:0;
        	font-size: 22px;
        	border-radius: 4px; /* 테이블의 둥근 모서리 설정 */
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border-radius: 4px; /* 테이블의 둥근 모서리 설정 */
            border: solid 1px #b8b6aa;
            background-color: #fff;
        }
        .form-group input:focus, .form-group textarea:focus {
            border-color: #eb5e28; /* 주황색 포커스 */
            box-shadow: 0 0 8px rgba(235, 94, 40, 0.5);
            outline: none;
        }

        /* 텍스트 영역 기본 높이 설정 */
        .form-group textarea {
            padding: 20px 10px;
		    line-height: 24px;
		    height: 300px; /* 고정된 높이 설정 */
		    overflow-y: auto; /* 내용이 넘치면 세로로 스크롤이 생김 */
        }

        /* 제출 버튼 */
        .submit-btn {
            padding: 6px 17px;
		    background-color: #252422;
		    color: white;
		    border: none;
		    border-radius: 5px;
		    font-size: 17px;
        }
        .submit-btn:hover {
           background-color: #eb5e28;
        }

        /* 버튼을 중앙 정렬 */
        .form-groups {
            text-align: center;
            margin: 10px 0;
        }

    </style>
</head>
<body>

<jsp:include page="/WEB-INF/header1.jsp" />
<script src="https://kit.fontawesome.com/0c69fdf2c0.js" crossorigin="anonymous"></script>
<div class="container">
    <div class="form-header">
        <h3><i class="fa-solid fa-pen-to-square">공지사항 수정하기</i></h3>
    </div>

    <!-- 공지사항 수정 폼 -->
    <form action="<%= ctxPath %>/notice/noticeEdit.mp" method="post" onsubmit="return goedit()">
        <!-- notice_id는 hidden 필드로 전송되어 수정될 공지사항을 식별 -->
        <input type="hidden" name="seq_notice_no" value="${requestScope.seq_notice_no}" />

        <div class="form-group">
            <label for="notice_subject">공지사항 제목</label>
            <input type="text" id="notice_subject" name="notice_subject" placeholder="공지사항 제목을 입력하세요" required value="${requestScope.notice_subject}"/>
        </div>

        <div class="form-group">
            <label for="notice_content">공지사항 내용</label>
            <textarea id="notice_content" name="notice_content" placeholder="공지사항 내용을 입력하세요" required style="resize: none;"><%=request.getAttribute("notice_content")%></textarea>
        </div>

        <div class="form-groups">
            <button type="submit" class="submit-btn" onclick="return confirm('정말 수정하시겠습니까?');">수정하기</button>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

<script type="text/javascript">

$(document).ready(function(){
	
	$("textarea#notice_content").val("${requestScope.notice_content}");
	
});

function goedit() {
    var notice_subject = document.getElementById('notice_subject').value.trim();
    var notice_content = document.getElementById('notice_content').value.trim();

    // 제목과 내용이 공백만으로 이루어졌는지 체크
    if (notice_subject === "") {
        alert("제목은 빈 공백만 입력할 수 없습니다.");
        return false;  // 폼 제출을 막음
    }

    if (notice_content === "") {
        alert("내용은 빈 공백만 입력할 수 없습니다.");
        return false;  // 폼 제출을 막음
    }

    // 길이 체크: 제목은 50자 이내, 내용은 200자 이내
    if (notice_subject.length > 50) {
        alert("제목은 50자 이하로 입력해주세요.");
        return false;  // 폼 제출을 막음
    }

    if (notice_content.length > 200) {
        alert("내용은 200자 이하로 입력해주세요.");
        return false;  // 폼 제출을 막음
    }

    return true;  // 모든 검증을 통과하면 폼 제출
}
</script>

</body>
</html>
