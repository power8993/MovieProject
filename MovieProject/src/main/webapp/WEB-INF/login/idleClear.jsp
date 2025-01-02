<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctx_Path = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/WEB-INF/header1.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/login/idleClear.css" />
<title>Insert title here</title>

<script>
$(document).ready(function() {
	

	$("input[name='mobile']").focus();
	//$("#error").hide();
    
    $("input[name='mobile']").bind("keydown", (e) => {// 번호입력란에 엔터를 했을 경우
    	if(e.keyCode == 13) { 
    		
    		$("#mobilebtn").html("재전송");
        	$("#pass").show();
        	$("input[name='passNumber']").val("");
    		
        	if ($("input[name='mobile']").val().trim() === "") {
    		    $("#error").html("휴대번호를 입력해 주세요.");
    		    return false;
    		}
    		
    	
    		goSend(); // 인증번호 전송 시도.
    	}
    });    
    
    $(document).on('click', '#mobilebtn', function(){ // 전송하기 클릭 시
    	
    	$("#mobilebtn").html("재전송");
    	$("#pass").show();
    	$("input[name='passNumber']").val("");
    	
    	
    	if ($("input[name='mobile']").val().trim() === "") {
		    $("#error").html("번호를 입력해 주세요.");
		    return false;
		}
    	
    	goSend(); // 인증번호 전송 시도.
    	
    });// end of $(document).on('click', '#mobilebtn', function(){}------------------
    
    		
	// 문자 전송이 되며 생성된 '인증하기' 버튼 클릭 시 
    $(document).on('click', '#passNumbtn', function(){
    	goAuthNumber(); // 인증번호 전송 시도.
    	$("#pass").hide(); //인증번호를 연속으로 누르면 안되기 때문에 hide 시켜버림.
    });//end of $(document).on('click', '#passNumber', function(){}-------------------	
    
}); // end of $(document).ready(function() {})---------------------------

// 인증번호 입력란에서 엔터를 누를 시 
$(document).on("keydown", "input[name='passNumber']", function(e) {
    if (e.key === "Enter" || e.keyCode == 13 || e.which == 13) {
        goAuthNumber();
        $("#pass").hide();
    }
});

var randomNumber = 0;
var idleMemberMobile = "${idleMemberMobile != null ? idleMemberMobile : ''}";
//console.log("idleClear.jsp 최상단 : " + idleMemberMobile);


/////////////////////////function/////////////////////////

//인증번호 전송 시도를 하는 함수
function goSend() {
	var ctx_Path = $("#ctx_Path").text();
	//alert("SmsSend.mp로 url을 이동합니다.");
	
	//입력된 번호와 idleClear에서 imple을 활용한 userid 의 번호를 비교하여 맞으면 밑을 진행 틀리면 return false;
	//$("input[name='mobile']").val() == userMobile 
	
	
	$.ajax({
	      type:"get",
	      url:"${pageContext.request.contextPath}/member/smsSend.mp",
	      data: {
	        //"passNumber": $("#passNumber").val().trim(),
	        "mobile" : $("input[name='mobile']").val().trim()
	    	},
	      cache : false, // AJAX 요청에 대한 캐싱을 방지 (서버로 항상 새로운 요청을 보내도록.)
	    	
	      success:function(json){
	    	  randomNumber = json.randomNumber;
	      }
	    });
	
	 var userInput = $("input[name='mobile']").val().trim(); // 사용자 입력값
	 
	
	if(userInput != idleMemberMobile){ // 계정에 등록된 번호와 사용자가 입력한 번호가 다를 경우.
	
		if(!validatePhoneNumber($("input[name='mobile']").val().trim())){ //정규표현식 함수에서 휴대번호 입력 검증
    		$("#error").html("유효하지 않은 휴대폰 번호입니다.");
    		return false;
    	}
		
		$("#error").html("계정에 등록된 전화번호와 일치하지 않습니다.");
		
		return false;
	}
	//alert("전화번호가 일치합니다.");
	//console.log("정상적으로 일치 확인");
	
	$("#pass").html("<br><label style='display:inline-block;width:80px;margin-bottom:0px;'>인증번호 : </label>  <input type='text' name='passNumber' id='passNumber'placeholder='인증번호 입력' autocomplete='off'/><button type='button' id='passNumbtn' style='margin-left:15px;'> 인증하기 </button>");
	$("#error").html("<p style='color:black;'>인증번호가 전송되었습니다.</p>");
}


		

//인증번호 인증하는 함수 (인증하기 버튼 클릭 또는 엔터 시 실행되는 함수)
function goAuthNumber() {
	$.ajax({
	      type:"POST",
	      dataType: "json",
	      url:"${pageContext.request.contextPath}/member/smsSend.mp",
	      data: {
	        "passNumber": $("#passNumber").val().trim(),
	        "randomNumber": randomNumber,
	        "idleMemberMobile":idleMemberMobile
	    	},
	    	
	      success:function(json){

				//console.log("DB 업데이트/삭제 행 수  : "+json.result1 +  json.result2);
				if(json.result1 !=undefined && json.result2 !=undefined){ // 인증 완료된 경우 (서버에서 sql문이 실행됨.) 
					$("#successAuthNumber").append(
				            "<span style='display:inline-block;color:black;'>인증완료</span>되었습니다. HGV 서비스를 이용하시려면 <span style='display:inline-block;color:black;'>[로그인]</span>을 진행해 주세요." 
				     );
					$("#error").hide();
					 return false;
				}
				else{ // 인증이 완료되지 않은 경우
					 $("#error").html("인증에 실패하였습니다. 인증번호를 재전송해 주세요.");
				}
				
	    	  return false;
	      }
	    });
}

// 휴대번호 정규표현식을 검증하는 함수
function validatePhoneNumber(phoneNumber) {
    // 정규식: 010으로 시작하고 뒤에 숫자 8자리
    const phoneRegex = /^010\d{8}$/;

    if (phoneRegex.test(phoneNumber)) {
        //console.log("유효한 휴대폰 번호입니다.");
        return true;
    } else {
        //console.log("유효하지 않은 휴대폰 번호입니다. 올바른 형식: 010XXXXXXXX");
        return false;
    }
}
	

		
//인증번호 인증이 완료된 후 생성되는 로그인 버튼 클릭 시
$(document).on('click', '#loginBtn', function(){
	var ctx_Path = $("#ctx_Path").text();
	location.href = ctx_Path +"/login/login.mp";
}); // end of $(document).on('click', '#mobilebtn', function(){});

</script>

</head>
<body>
<div id="ctx_Path" style="display:none;"><%= ctx_Path%></div>
<div id="container">
	<%-- <p>회원 전화번호: ${idleMemberMobile}</p>  출력 잘됨.--%> 
	<div style="width:500px; margin:0 auto; margin-top:100px;">
					<h1 style="font-size: 32px; font-weight: bold;">휴면 해제를 위한 휴대번호 인증</h1>
					<p>
						<span>안녕하세요!
				        	<br>
						회원님의 <span style="font-weight:700px;">HGV계정에 로그인된 전화번호로</span>인증을 진행해 주세요.<br>
						</span>
					</p>
					
					<div style="background-color:#e6ccb3; padding:10px 10px 10px 10px;">

							<label style="display:inline-block;width:80px;margin-bottom:0px;">휴대번호 :</label>  
							<input type="text" name="mobile" value="" placeholder="'-' 제외" autocomplete="off"/> <button type="button" id="mobilebtn"> 전송하기 </button>
							<div id="pass" ></div>
					</div>
					
					<%-- 인증 성공 시 나오는 문구 --%>
					<p style="color:gray; font-size:13px; margin-top:20px;" id="successAuthNumber"></p>
					<p style="color:red; font-size:13px; margin-top:20px;" id="error"></p>

				<hr>
				<form name="idleClearfrm">
				<input type="hidden" name="userid" value="`+json.userid+`" >
				
					<div style="display:flex; justify-content:space-between; margin-top:30px;">
					<button type="button" id="loginBtn" style="border:solid 1px #cccccc; height:40px;width:100%;">로그인</button> 
					</div>
					
				</form>
				</div>
	
	<div id="message"></div>
	
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />
</body>
</html>