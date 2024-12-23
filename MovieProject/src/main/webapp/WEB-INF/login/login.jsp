<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<%
    String ctx_Path = request.getContextPath();
    //    /MyMVC
%>
<jsp:include page="/WEB-INF/header1.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/login/loginForm.css" />
<script type="text/javascript" src="<%= ctx_Path%>/js/login/login.js"></script>

<script type="text/javascript">
   $(document).ready(function(){
   
      ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
      // === 로그인을 하지 않은 상태일 때 
      //     로컬스토리지(localStorage)에 저장된 key가 'saveid' 인 userid 값을 불러와서 
      //     input 태그 userid 에 넣어주기 ===
           
        if( ${empty sessionScope.loginuser} ) {  // 로그인이 되어지면 session 정보에 넣어둠   
           
           const loginUserid = localStorage.getItem('saveid');  // localStorage에서 값을 꺼내옴
        
           if(loginUserid != null) {  //  문서가 로딩 되어지자 마자 바로
              $("input#loginUserid").val(loginUserid);
              $("input:checkbox[id='saveid']").prop("checked", true);
           }
        }
       ////////////////////////////////////////////////////////////////////////////////////////
       
           	  
});  // end of $(document).ready(function(){}) ---------------------------------------------


</script>


	<%-- === 로그인을 하기 위한 폼을 생성 === --%>
	<div id="form_container">
		<form name="loginFrm" action="<%=ctx_Path%>/login/login.mp" method="post">
	
			<div id="inputIdPW">
				<div id="logo">LOGO</div>
	
				<table id="loginTbl">
					<tr>
						<td>아이디</td>
						<td><input type="text" name="userid" class="inputIdPw" id="loginUserid"  placeholder="아이디를 입력하세요" autocomplete="off" />
						</td>
					</tr>
					<tr >
						<td style="padding-top:20px;">비밀번호</td>
						<td style="padding-top:20px;">
							<input type="password" name="pwd" class="inputIdPw" id="loginPwd" placeholder="비밀번호를 입력하세요" />
						</td>
					</tr>
					<tr>
						<td style="text-align:left; border:none;"><input type="checkbox" id="saveid"/>&nbsp;<label for="saveid">아이디저장</label> </td>
						
					</tr>
				</table>
					
				
			</div>
			
			<button type="button" class="loginjspBtn" id="btnSubmit">로그인</button>
		</form>
		
		<%-------
		
		onclick="location.href='<%= ctx_Path %>/WEB-INF/login/idpwFind.mp'" 을 하려면 command.Properties에 idpwFind.mp을 추가해야함!! 
		할 시에 성훈이형에게 말하기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		idpwFind.mp에서 String action = request.getParameter("action");로 폼 값을 받고 
		폼 값을 활용하여 .mp에서 idFind인지, pwFind 인지 if문을 활용하여 나누기
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/login.jsp");
		-------%>
		
		<div style="width: 350px; margin: 0 auto;">
		    <div id="buttonContainer">
				<form action="<%=request.getContextPath()%>/login/idpwFind.mp" method="get">
					<button type="submit" class="loginjspBtn" id="findIdBtn" name="action" value="findId">아이디 찾기</button>
					<button type="submit" class="loginjspBtn" id="findPwBtn" name="action" value="findPw">비밀번호 찾기</button>
				</form>
			<button type="button" class="loginjspBtn" id="signUpBtn" onclick="location.href='<%= ctx_Path %>/member/memberRegister.mp'">회원가입</button>
		    </div>
		</div>
	</div>



<%-- --------------------------------------------------------------- --%>
<jsp:include page="/WEB-INF/footer1.jsp" />


