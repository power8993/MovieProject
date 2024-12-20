<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>


<jsp:include page="/WEB-INF/header1.jsp" />


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
		  
// Function Declaration
		  
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
        url: "idFind.up", // 서버의 컨트롤러 매핑 주소
        data: {
            "name": $("input[name='name']").val(),
            "email": $("input[name='email']").val()
        },
        type: "post",
        dataType: "json", // 서버에서 JSON 형식의 응답을 받음
        success: function(json) {
            if (json.userid) {
                // 성공적으로 아이디를 찾은 경우
            	$("#div_findResult > span").html(name + " 회원님의 아이디는 <span style='color: orange; font-size: 18pt; font-weight: bold;'>" + json.userid + "</span>입니다.");

            } else {
                // 아이디를 찾지 못한 경우
                $("#div_findResult > span").html("존재하지 않은 정보입니다.");
            }
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
        }
    });
}
	
</script>

<style>

div#form_container > form {
  	max-width: 350px;
  	height: 320px;
  	margin: 0 auto;
}

div#form_container  {
	width: 980px;
	height: 600px;
	margin: 0 auto;
	box-sizing: border-box; /* padding 포함 계산 */
	border:solid 1px red;
}


div#inputIdPW > div#logo{
	margin: 0 auto;	
	margin-top:120px;
	margin-bottom:30px;
}

#loginTbl{
	margin: 0 auto;
}

#loginTbl tbody tr td{
	border-bottom: 2px solid gray;
	width:300px;
}

div#form_container > form {
  	max-width: 350px;
  	height: 280px;
  	margin: 0 auto;
}
	
.inputNameEmail{
	border: none;        /* 테두리 제거 */
    outline: none;       /* 포커스 시 나타나는 아웃라인 제거 (선택 사항) */
    background-color: transparent; /* 배경을 투명하게 (선택 사항) */
    padding: 5px;        /* 내부 여백 설정 (선택 사항) */
}
#form_container form {
    text-align: center;
}

.loginjspBtn {
	color: #ffffff; /* 하얀색 폰트 */
    font-size: 14px; /* 폰트 크기 설정 */
    background-color: #ff7a00; /* 주황색 배경 */
    border: none; /* 테두리 제거 */
    border-radius: 5px; /* 모서리를 둥글게 */
    cursor: pointer; /* 마우스 포인터 변경 */
    flex: 1; /* 버튼을 동일한 너비로 설정 */
    text-align: center; /* 텍스트를 가운데 정렬 */
    font-weight:700;
}

#btnSubmit{
	margin-top:40px;
	margin-bottom:0px;
	width:100%;
	height:45px;
	background-color: #ff7a00; /* 주황색 배경 */
    color: #ffffff; /* 하얀색 폰트 */
    font-size: 21px; /* 폰트 크기 설정 */
    
}

#buttonContainer {
    display: flex;
    justify-content: space-between; /* 버튼 사이의 간격을 동일하게 설정 */
    gap: 10px; /* 버튼들 사이에 일정한 간격 추가 (선택 사항) */
}

#findIdBtn{
	background-color: #403d39;
	height:35px;
}

 #findPwBtn{
 	background-color: #403d39;
 	height:35px;
}

#signUpBtn{
	background-color: #ccc5b9;
	height:35px;
	color:black;
}

</style>

<div class="container" style="height:600px;">


	<div id="form_container">
			<form name="idFindFrm">
		
				<div id="inputIdPW">
					<div id="logo">LOGO</div>
		
					<table id="loginTbl">
						<tr>
							<td>성명</td>
							<td><input type="text" name="name" size="25" class="inputNameEmail" placeholder="성명을 입력하세요" autocomplete="off" /> 
							</td>
						</tr>
						<tr>
							<td style="padding-top:20px;">이메일</td>
							<td style="padding-top:20px;">
								<input type="text" name="email" size="25" class="inputNameEmail" placeholder="이메일을 입력하세요" autocomplete="off" />
							</td>
						</tr>
					</table>
				</div>
				<button type="button"  class="loginjspBtn" id="btnSubmit"onclick="goidfind()">아이디 찾기</button>
			</form>
			
			<div style="width: 350px; margin: 0 auto;">
		    <div id="buttonContainer">
					<button type="button" class="loginjspBtn" id="findIdBtn" name="action" value="findId"  onclick="location.href='<%=request.getContextPath()%>/login/login.up'">로그인</button>
				<form action="<%=request.getContextPath()%>/login/idpwFind.up" method="get">
					<button type="submit" class="loginjspBtn" id="findPwBtn" name="action" value="findPw">비밀번호 찾기</button>
				</form>
		    </div>
		</div>
		<div class="my-3 text-center" id="div_findResult">
		   <span style="color: black; font-size: 14pt; font-weight: bold;"></span>
		</div>
	
	</div>
	
</div>
<jsp:include page="/WEB-INF/footer1.jsp" />