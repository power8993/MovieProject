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
            font-family: Arial, sans-serif;
	        background-color: #fffcf2; /* 부드러운 아이보리 배경색 */
	        margin: 0;
	        padding: 0;
        }

        /* 컨테이너 설정 */
        .container {
             width: 70%; /* 컨테이너 너비를 70%로 설정 */
		     margin: 50px auto;
		     padding: 30px;
        }

        .form-header {
            display: flex;
	        justify-content: space-between;
	        align-items: center;
	        margin-bottom: 30px;
	        color: #403d39;
        }
        .form-header h3 {
            font-size: 28px;
		    font-weight: bold;
        }

        /* 입력 필드 및 텍스트 영역 */
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            font-size: 16px;
		    display: block;
		    margin-bottom: 5px;
		    background-color: #ccc5b9;
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
            padding: 6px 17px;
	        background-color: #252422;
	        color: white;
	        border: none;
	        border-radius: 5px;
	        font-size: 17px;
	        cursor: pointer;
        }
        .submit-btn:hover {
            background-color: #d85e1b; /* 어두운 주황색 */
            transform: translateY(-2px); /* 버튼 호버시 살짝 위로 */
        }

        /* 버튼을 중앙 정렬 */
        .form-groups {
            text-align: center;
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
            <textarea id="notice_content" name="notice_content" placeholder="공지사항 내용을 입력하세요" required><%=request.getAttribute("notice_content")%></textarea>
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
