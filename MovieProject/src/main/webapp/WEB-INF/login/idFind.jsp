<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
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
	
	
	// 이름에 숫자입력을 시도할 경우 입력을 차단 //
	document.getElementById("nameInput").addEventListener("keypress", function (event) {
	  
	  if (!isNaN(event.key)) {
	    event.preventDefault();
	  }
	});

	
	// 성명 입력 검사 //
	$(document).on('input', '#nameInput', function () {
	   if ($("#nameInput").val() == "") {
	     $("#nameErrorMsg").html("<span>성명을 입력해 주세요.</span>");
	   } else {
	     $("#nameErrorMsg").html("");
	     $("#div_findResult").html("");
	   }
	});
	
	// 이메일 입력 검사 //
	$(document).on('input', '#emailInput', function() {
		if($("#emailInput").val()==""){
			$("#emailErrorMsg").html("<span>이메일을 입력해 주세요.</span>");
		}
		else{
			$("#emailErrorMsg").html("");
			$("#div_findResult").html("");
		}
	});
		  
}); // end of $(document).ready(function(){})------------------------------------------------------------
	
	
function goidfind() {
	

	const name = $("input:text[name='name']").val().trim();

		if(name == "") {
			$("#nameErrorMsg").html("<span>성명을 입력해 주세요.</span>");
			$("input#nameInput").val("").focus();
		 	return; // 종료
		}
		
	const email = $("input:text[name='email']").val();
	
		if(email == "") {
			$("#emailErrorMsg").html("<span>이메일을 입력해 주세요.</span>");
			$("input#emailInput").val("").focus();
		 	return; // 종료
		}

		

		// const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
		// 또는
		const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);  
		// 이메일 정규표현식 객체 생성 
		      
		const bool = regExp_email.test(email);
		
		if(!bool) {
		      // 이메일이 정규표현식에 위배된 경우
		      $("#emailErrorMsg").html("<span>입력하신 이메일 주소 형식이 올바르지 않습니다.</span>");
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
            	$("#div_findResult").html("<div class='result-card'><h4>" + name + " 회원님,</h4><p>회원님의 아이디는</p><h3 class='userid-display'>[ " + json.userid + " ]</h3><p>입니다.</p></div>");


            } else {
                // 아이디를 찾지 못한 경우
                $("#div_findResult").html("<span style='color:red;font-size:10pt;'>성명 또는 이메일을 확인해 주세요.</span>");
            }
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
        }
    });
}
	
</script>


<div id="form_container">

<div id="semiLogo"><a href="<%= ctxPath%>/"><img src="<%= ctxPath%>/images/index/logo.png"/></a></div>
	<form name="pwdFindFrm">
	  <!-- 성명 입력 -->
	  <div class="form-group">
	    <div class="form-floating-label">
	      <input type="text" class="form-control" id="nameInput" name="name" autocomplete="off" placeholder=" " />
	      <label for="nameInput">성명</label>
	    </div>
	    <div id="nameErrorMsg"></div>
	  </div>
	
	  <!-- 이메일 입력 -->
	  <div class="form-group">
	    <div class="form-floating-label">
	      <div style="position: relative;">
	        <input type="text" class="form-control" id="emailInput" name="email" autocomplete="off" placeholder=" " />
	        <label for="emailInput" style="left: 10px;">이메일</label>
	        <button type="button" id="findBtn" class="btn btn-primary" onclick="goidfind()" >
	          아이디 찾기
	        </button>
	      </div>
	    </div>
	    <div id="emailErrorMsg"></div>
	    <div id="div_findResult"></div>
	  </div>
	</form>

	 
	
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