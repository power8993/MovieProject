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
    $("#mobilebtn").click(function() {
         $("#mobilebtn").html("재전송");
    });
});

var randomNumber = 0;
var idleMemberMobile = "${idleMemberMobile != null ? idleMemberMobile : ''}";
console.log("idleClear.jsp 최상단 : " + idleMemberMobile);
//function
$(document).on('click', '#mobilebtn', function(){

	var ctx_Path = $("#ctx_Path").text();
	alert("SmsSend.mp로 url을 이동합니다.");
	//location.href=ctx_Path + "/SmsSend.mp";
	//console.log(ctx_Path)
	
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
	    	  //$("#message").append("<button type='button' id='loginBtn'>로그인</button>");
	    	  //return false;
	    	  randomNumber = json.randomNumber;
	      }
	    });
	
	 var userInput = $("input[name='mobile']").val().trim(); // 사용자 입력값
	 console.log("서버 전화번호: " + idleMemberMobile);
     console.log("사용자 입력값: " + userInput);
	
	if(userInput != idleMemberMobile){
		alert("계정에 등록된 전화번호와 일치하지 않습니다!");
		
		return false;
	}
	alert("전화번호가 일치합니다.");
	console.log("정상적으로 일치 확인");

	$("#pass").html("<br>인증번호 : <input type='text' name='passNumber' id='passNumber'/><button type='button' id='passNumbtn'> 인증하기 </button>");
	     
});// end of $(document).on('click', '#mobilebtn', function(){}------------------

		

// 문자 전송이 되며 생성된 '인증하기' 버튼 클릭 시 
$(document).on('click', '#passNumbtn', function(){
	
	$.ajax({
	      type:"POST",
	      dataType: "json",
	      url:"${pageContext.request.contextPath}/member/smsSend.mp",
	      data: {
	        "passNumber": $("#passNumber").val().trim(),
	        //"mobile" : $("input[name='mobile']").val().trim(),
	        "randomNumber": randomNumber,
	        "idleMemberMobile":idleMemberMobile
	    	},
	    	
	      success:function(json){
console.log("쿼리문이 잘 돌았나~? : "+json.result1 +  json.result2);
				if(json.result1 !=0 && json.result2 !=0){
					 $("#message").append("<button type='button' id='loginBtn'>로그인</button>");
					 console.log("return false 전");
					 return false;
					 console.log("return false 후");
				}
				else{
					 console.log("조건 불만족: 실패 메시지 출력");
					$("#message").append("휴면해제를 실패하였습니다.");
				}
				
	    	  return false;
	      }
	    });
	/*
	 if(json.randomNumber == $("#passNumber").val()){
		// 인증번호가 인증이 완료된 경우
		$("#message").html("인증이 완료되어 휴면계정이 해제되었습니다.");
		
		 $("#message").append("<button type='button' id='loginBtn'>로그인</button>");
		return false;
	 }
	 else{
	// 인증에 실패한 경우
	  $("#message").html("잘못된 인증번호입니다. 인증번호를 재전송해 주세요.");
	  return false;
	 }
 	*/
});//end of $(document).on('click', '#passNumber', function(){}-------------------		

		
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
	<p>회원 전화번호: ${idleMemberMobile}</p>
	
	휴면해제를 위한 인증을 진행합니다.<br>
	[휴대전화 인증]<br>
	휴대번호 : <input type="text" name="mobile" value=""/> <button type="button" id="mobilebtn"> 전송하기 </button>
	
	<!-- <form action="" method="post" name = "frm">
		 
	</form> -->
	<div id="pass"></div>
	<div id="message"></div>
	
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />
</body>
</html>