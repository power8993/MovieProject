<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>


<jsp:include page="/WEB-INF/header1.jsp" />

<%--사용자 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/login/idFind.css" > 

<script type="text/javascript">

$(document).ready(function(){
	const method = "${requestScope.method}";
	//alert(method);
	if(method == "GET") {
	 	$("div#div_findResult").hide();
	}
	
	
	$("input:text[name='email']").bind("keyup", function(e){
		 if(e.keyCode == 13) {
		  	goidfind();
		 }
	});// end of $("input:text[name='email']").bind("keyup", function(e){})-------
		  
}); // end of $(document).ready(function(){})------------------------------------------------------------
	
	
function goidfind() {
	

	const name = $("input:text[name='name']").val().trim();

		if(name == "") {
			alert("성명을 입력하세요!!");
		 	return; // 종료
		}

		const email = $("input:text[name='email']").val();

		// const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
		// 또는
		const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);  
		// 이메일 정규표현식 객체 생성 
		      
		const bool = regExp_email.test(email);
		
		if(!bool) {
		      // 이메일이 정규표현식에 위배된 경우
		      alert("e메일을 올바르게 입력하세요!!");
		return; // 종료
	   	}    
		
		
		
	/////////////////============ ajax ============///////////////////////
	
    $.ajax({
        url: "idFind.mp", // 서버의 컨트롤러 매핑 주소
        data: {
            "name": $("input[name='name']").val(),
            "email": $("input[name='email']").val()
        },
        type: "post",
        dataType: "json", // 서버에서 JSON 형식의 응답을 받음
        success: function(json) {
            if (json.userid) {
                // 성공적으로 아이디를 찾은 경우
            	$("#div_findResult ").html(name + " 회원님의 아이디는 <span style='color: orange; font-size: 18pt; font-weight: bold;'>" + json.userid + "</span>입니다.");

            } else {
                // 아이디를 찾지 못한 경우
                $("#div_findResult").html("존재하지 않은 정보입니다.");
            }
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
        }
    });
}
	
</script>


<div id="form_container">

<div id="logo">LOGO</div>
	<form name="pwdFindFrm">
	
        <label style="display: block; width: 90px; margin:0px;">성명</label>
        <input type="text" name="name" size="25" autocomplete="off" placeholder="성명을 입력하세요" style="display: block; width:100%;"/> 
 
        <label style="display: block; width: 90px; margin:10px 0 0 0;">이메일</label>
        <div style="display:flex">
        	<input type="text" name="email" size="28" autocomplete="off" placeholder="이메일을 입력하세요" style="display: block; "/> <button type="button" id="findBtn"onclick="goidfind()">아이디 찾기</button>
        </div>
	
	   
	</form>
	 <div class="my-3 text-center" id="div_findResult">

	</div>
	
	<div style="width: 350px; margin: 0 auto;">
	    <div id="buttonContainer">
				<button type="button" class="loginjspBtn" id="loginBtn" onclick="location.href='<%=request.getContextPath()%>/login/login.mp'">로그인</button>
			<form action="<%=request.getContextPath()%>/login/idpwFind.mp" method="get">
				<button type="submit" class="loginjspBtn" id="findPwBtn" name="action" value="findPw">비밀번호 찾기</button>
			</form>
	    </div>
	</div>
</div>
	
<jsp:include page="/WEB-INF/footer1.jsp" />