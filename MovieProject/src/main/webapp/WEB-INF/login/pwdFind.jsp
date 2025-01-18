<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<jsp:include page="/WEB-INF/header1.jsp" />
<%--사용자 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/login/pwdFind.css" > 

<script type="text/javascript">

$(document).ready(function(){
    
	
	const method = "${requestScope.method}";
	
	// --- 처음에는 아이디 찾은 결과를 보여주는 태그를 가린다. --- //
	if(method == "GET") {
		$("div#div_findResult").hide();
	}
	if(method == "POST") {
		$("input:text[name='userid']").val("${requestScope.userid}");
		$("input:text[name='email']").val("${requestScope.email}");
		/*
			if(${requestScope.isUserExist == false || requestScope.sendMailSuccess == true}) { 
	           $("button#findBtn").html("재전송");  
	        }
		*/
	}
	
	// 아이디 입력 검사 //
	$(document).on('input', '#useridInput', function() {
		if($("#useridInput").val()==""){
			$("#idErrorMsg").html("<span>아이디를 입력해 주세요.</span>");
		}
		else{
			$("#idErrorMsg").html("");
			$("#errorMsg").html("");
		}
	});
	
	// 이메일 입력 검사 //
	$(document).on('input', '#emailInput', function() {
		if($("#emailInput").val()==""){
			$("#emailErrorMsg").html("<span>비밀번호를 입력해 주세요.</span>");
		}
		else{
			$("#emailErrorMsg").html("");
			$("#errorMsg").html("");
		}
	});
	
	// 새 암호 입력 검사 //
	$(document).on('input', '#newPwd', function() {
		if($("#newPwd").val()==""){
			$("#newPwdErrorMsg").html("<span>새 암호를 입력해 주세요.</span>");
		}
		else{
			$("#newPwdErrorMsg").html("");
			//$("#errorMsg").html("");
		}
	});
	
	// 새 암호 확인 입력 검사 //
	$(document).on('input', '#confirmPwd', function() {
		if($("#confirmPwd").val()==""){
			$("#confirmPwdErrorMsg").html("<span>새 암호를 입력해 주세요.</span>");
		}
		else{
			$("#confirmPwdErrorMsg").html("");
			//$("#errorMsg").html("");
		}
	});
	
	
	$("button#findBtn").click(function(){
	 
		$("#verificationErr").html("");
	    goFind(); 
	      
	});// end of $("button#findBtn").click(function(){})-----
	
	
	   
	$("input:text[name='email']").bind("keyup", function(e){
		if(e.keyCode == 13) {
		   goFind();
		}
	});// end of $("input:text[name='email']").bind("keyup", function(e){})-------
	
	
	
	//암호변경하기(새 암호로 변경 후 변경하기버튼 클릭)
	$(document).on("click", "button#finishNewPwd", function() { // ajax로 인해 동적으로 생성된 폼태그요소를 선택자로 잡고 클릭이벤트 함수 생성.
        
        const pwd  = $("input:password[name='pwd']").val();
        const pwd2 = $("input:password[name='pwd2']").val();
        
        if(pwd != pwd2) {
        	$("#confirmPwdErrorMsg").html("암호가 일치하지 않습니다.");
           $("input:password[name='pwd']").val("");
           $("input:password[name='pwd2']").val("");
           return;  // 종료
        }
        else {
           const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);  
            // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성 
            
            const bool = regExp_pwd.test(pwd);   
           
            if(!bool) {
              // 암호가 정규표현식에 위배된 경우
              $("#confirmPwdErrorMsg").html("암호는 8글자 이상 15글자 이하에 영문자,숫자,특수기호가 혼합되어야 합니다.");
              $("input:password[name='pwd']").val("");
              $("input:password[id='pwd2']").val("");
              return; // 종료
            }
            else {
               // 암호가 정규표현식에 맞는 경우
               
               <%-- const frm = document.pwdUpdateEndFrm;
               frm.action = "<%= ctxPath%>/login/pwdUpdateEnd.mp";
               frm.method = "post";
               frm.submit(); --%>
               $.ajax({
            	    url: "pwdUpdateEnd.mp",
            	    data: {
            	        "pwd": $("input:password[name='pwd']").val().trim(),
            	        "userid": $("input:hidden[name='userid']").val().trim()
            	    },
            	    type: "post",
            	    dataType: "json", // 서버에서 JSON 형식의 응답을 받음
            	    success: function(json) {
            	        let resultHtml = "";

            	        if (json.method.toLowerCase() === "post" && json.n == 1 ) { 
            	        	// 소문자/대문자 모두 처리(모두 소문자로 변경해주기 때문)
            	            // post 방식이며 회원이 존재할 경우
            	            resultHtml = `
										  <div class="password-reset-success">
										    <h2>비밀번호 변경 완료</h2>
										    <p>회원님의 비밀번호가 성공적으로 변경되었습니다.</p>
										  </div>
										`;

            	            $("#findPwBtn").hide();
            	        } 
            	        else if (json.n == 0) {
            	            // SQL 구문 오류가 발생했을 경우
            	            resultHtml = `<span style="color: red;">SQL 구문 오류가 발생되어 비밀번호 변경을 할 수 없습니다.</span>`;
            	            $("#findPwBtn").html("비밀번호 찾기");
            	            $("#findPwBtn").val("findPw"); 
            	        } /* 필요가 없음. 하지만 수정할 수도 있으니 남겨둠
            	        else {
            	            // GET 방식
            	            $("#div_findResult").hide(); // 기존 폼태그 숨기기
            	            
            	            resultHtml = `
            	                <form name="pwdUpdateEndFrm">
            	                    <div class="div_pwd" style="text-align: center;">
            	                        <span style="color: blue; font-size: 12pt;">새 암호</span><br/> 
            	                        <input type="password" name="pwd" size="25" />
            	                    </div>
            	                    
            	                    <div class="div_pwd" style="text-align: center;">
            	                        <span style="color: blue; font-size: 12pt;">새 암호 확인</span><br/>
            	                        <input type="password" id="pwd2" size="25" />
            	                    </div>
            	                    
            	                    <input type="hidden" name="userid" value="${requestScope.userid}" />
            	                    
            	                    <div style="text-align: center;">
            	                        <button type="button" class="btn btn-success">암호 변경하기</button>
            	                    </div>
            	                </form>
            	            `;
            	        }
            	        */

            	        // 결과를 div에 삽입
            	        $("#div_findResult").html(resultHtml);
            	    },
            	    error: function(request, status, error) {
            	        alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
            	    }
            	});

               
            }
        }
        
     });// end of $("button#finishNewPwd").click(function(){})----
    
	
	
	
	// === 인증하기 버튼 클릭시 이벤트 처리해주기 시작 === //
	// 동적으로 생성된 btn-info 버튼에 이벤트 바인딩
	$(document).on("click", "button.btn-info", function() {
		
		const input_confirmCode = $("input:text[name='input_confirmCode']").val().trim();
		
		if(input_confirmCode == ""){
			alert("인증코드를 입력하세요!!");
			return; // 종료
		}
		
		const userid = $('#useridInput').val(); // 입력한 아이디 값을 폼태그에 넣어주기 위해.
		//console.log("확인용"+userid); // 값이 제대로 들어있는지 확인
		

		<%-- const frm = document.verifyCertificationFrm;
		frm.userCertificationCode.value = input_confirmCode;
		frm.userid.value = $("input:text[name='userid']").val().trim();
		
		frm.action = "<%= ctxPath%>/login/verifyCertification.mp";
		frm.method = "POST";
		frm.submit(); --%>
		
		
		
		/////////////////============ ajax ============///////////////////////
		 $.ajax({
		     url: "verifyCertification.mp",
		     data: {
		         "userCertificationCode": $("input:text[name='input_confirmCode']").val().trim(),
		         "userid":userid
		     },
		     type: "post",
		     dataType: "json", // 서버에서 JSON 형식의 응답을 받음
		     success: function(json) {
		    	 console.log("서버 응답:", json);
		         let resultHtml = "";
		
		         if (json.is_True_false == false) {
		             // 인증코드가 맞지 않는 경우
		             $("#verificationErr").html("<p style='margin-top: 1px; margin-bottom: 0px;color: red; font-size: 10pt; text-align: left;'>인증에 실패하였습니다. </p>");
		             $("#findPwBtn").html("비밀번호 찾기");
			         $("#findPwBtn").val("findPw"); 
			         $("button#findBtn").html("재전송");
		         } 
		         else {
		             // 인증코드가 맞는 경우
		             $("#form_container > form").hide(); // 기존 폼태그 숨기고, 코드가 인증 되었으니 새로운 비밀번호를 생성할 수 있도록.
		             
		             resultHtml = `
		            	  <div class="password-reset-container">
		            	    <p class="reset-message">코드가 인증되었습니다.</p>
		            	    <form name="newpwdFrm" style="max-width: 350px; margin: 0 auto; margin-bottom: 20px; margin-top: 20px;">
		            	      <!-- 새 암호 -->
		            	      <div class="form-group">
		            	        <div class="form-floating-label">
		            	          <input type="password" class="form-control" name="pwd" id="newPwd" autocomplete="off" placeholder=" " />
		            	          <label for="newPwd">새 암호</label>
		            	          <div id="newPwdErrorMsg"></div>
		            	        </div>
		            	        
		            	      </div>
		            	      <!-- 새 암호 확인 -->
		            	      <div class="form-group">
		            	        <div class="form-floating-label">
		            	          <input type="password" class="form-control" name="pwd2" id="confirmPwd" autocomplete="off" placeholder=" " />
		            	          <label for="confirmPwd">새 암호 확인</label>
		            	          <div id="confirmPwdErrorMsg"></div>
		            	        </div>
		            	      </div>
		            	      <button type="button" class="btn btn-primary" id="finishNewPwd" style="margin-top: 10px; width: 100%;">암호 변경하기</button>
		            	    </form>
		            	  </div>
		            	`;

		         } 
		
		         // 결과를 div에 삽입
		         $("#div_findResult").html(resultHtml);
		         
		         // 전송 버튼 클릭 전 아이디를 폼태그로 담아줌. 이것은 hidden처리 됨.  
		         $("input[name='userid']").val(userid);
		
		     },
		     error: function(request, status, error) {
		         alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
		     }
		 });

		
	});
	
	// === 인증하기 버튼 클릭시 이벤트 처리해주기 끝 === //

	
	
});// end of $(document).ready(function(){})-----------------------
 

// Function Declaration
function goFind() {
 
	if( $("input:text[name='userid']").val().trim() == "" ) {
		$("#idErrorMsg").html("<span>아이디를 입력해 주세요.</span>");
		$("input:text[name='userid']").focus();
		return; // goLogin() 함수 종료
	}
      
     
     
     if( $("input:text[name='email']").val().trim() == "" ) {
 		$("#emailErrorMsg").html("<span>비밀번호를 입력해 주세요.</span>");
 		$("input:text[name='email']").focus();
 		return; // goLogin() 함수 종료
 	}
     
     const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);  
     // 이메일 정규표현식 객체 생성 
          
     const bool = regExp_email.test($("input:text[name='email']").val().trim());
  
     if(!bool) {
        // 이메일이 정규표현식에 위배된 경우
         $("#errorMsg").html("<span>입력하신 이메일 주소 형식이 올바르지 않습니다.</span>");
         return; // 종료
     }    
        <%-- 
     const frm = document.pwdFindFrm;
     frm.action = "<%=ctxPath%>/login/pwdFind.mp";
     frm.method = "post";
     frm.submit();
     
     
     **폼 제출 (frm.submit();)**은 페이지를 새로고침하므로 AJAX 요청과 함께 사용하면 안 됨.
		AJAX를 사용할 때는 폼을 제출하지 않고 비동기 요청만 보내는 것이 좋음.
      --%>
     
/////////////////============ ajax ============///////////////////////
     $.ajax({
         url: "pwdFind.mp", // 서버의 컨트롤러 매핑 주소
         data: {
             "userid": $("input[name='userid']").val(),
             "email": $("input[name='email']").val()
         },
         type: "post",
         dataType: "json", // 서버에서 JSON 형식의 응답을 받음
         success: function(json) {
             let resultHtml = "";

             if (!json.isUserExist) {
                 // 사용자가 존재하지 않는 경우
                 resultHtml = `<span style="color: red;">사용자 정보가 없습니다</span>`;
             } 
             else if (json.isUserExist && json.sendMailSuccess) {
                 // 사용자가 존재하고 메일 발송에 성공한 경우
                 resultHtml = `
                	 <div class="email-verification">
                	  <p>
                	    <span>인증코드가 </span><br>
                	    <span class="highlight-email">` + json.email + `</span><br>
                	    <span> 으로 전송되었습니다.</span>
                	  </p>
                	  <p>인증코드를 입력해주세요.</p>
                	  <div class="verification-input">
                	    <input type="text" name="input_confirmCode"  autocomplete="off" placeholder="인증코드 입력" style="height:41px;width:200px;padding:5px;"/>
                	    <button type="button" class="btn btn-info" id="pwdFindjspBtn" >인증하기</button>
                	    
                	  </div>
                	  <div id="verificationErr"></div>
                	</div>
                 `;
             } 
             else if (json.isUserExist && !json.sendMailSuccess) {
                 // 사용자가 존재하지만 메일 발송에 실패한 경우
                 resultHtml = `<span style="color: red;">메일발송을 실패했습니다</span>`;
             }

             // 결과를 div에 삽입
             $("#errorMsg").html(resultHtml);
         },
         error: function(request, status, error) {
             alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
         }
     });

}// end of function goFind(){}-----------------------





  
</script>


<style>


</style>

<div id="form_container">

<div id="semiLogo"><a href="<%= ctxPath%>/"><img src="<%= ctxPath%>/images/index/logo.png"/></a></div>
	<form name="pwdFindFrm">
	  <!-- 아이디 입력 -->
	  <div class="form-group">
	  <div id="pwdFindTitle"><p>비밀번호 찾기</p></div>
	    <div class="form-floating-label">
	      <input type="text" class="form-control" id="useridInput" name="userid" autocomplete="off" placeholder=" " />
	      <label for="useridInput" >아이디</label>
	      <div id="idErrorMsg"></div>
	    </div>
	    
	  </div>
	
	  <!-- 이메일 입력 -->
	  <div class="form-group">
	    <div class="form-floating-label" style="position: relative;">
	      <input type="text" class="form-control" id="emailInput" name="email" autocomplete="off" placeholder=" " />
	      <label for="emailInput" style="left: 10px;">이메일</label>
	      <button type="button" id="findBtn" class="btn btn-primary" style="position: absolute; top: 0; right: 0; height: 54px; border-radius: 0 4px 4px 0;">
	        전송
	      </button>
	      <div id="emailErrorMsg"></div>
	      <div id="errorMsg"></div>
	    </div>
	  </div>
	</form>

	<div class="my-3 text-center" id="div_findResult"></div>
	
	<div style="width: 350px; margin: 0 auto;">
	    <div id="buttonContainer" >
			<button type="button" class="loginjspBtn" id="loginBtn" onclick="location.href='<%=request.getContextPath()%>/login/login.mp'">로그인</button>
			<form action="<%=request.getContextPath()%>/login/idpwFind.mp" method="get">
				<button type="submit" class="loginjspBtn" id="findPwBtn" name="action" value="findId">아이디 찾기</button>
			</form>
	    </div>
	</div>



	
</div>

 



<%-- 인증하기 form --%>
<form name="verifyCertificationFrm">
   <input type="hidden" name="userCertificationCode" />
   <input type="hidden" name="userid" />
</form>

<jsp:include page="/WEB-INF/footer1.jsp" />