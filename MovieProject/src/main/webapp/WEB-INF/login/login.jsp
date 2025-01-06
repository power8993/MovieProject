<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<%
    String ctx_Path = request.getContextPath();
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
<div id="path" style="display:none;" ><%= ctx_Path%></div> <%-- 자바스크립트에 값을 전달하기 위한 요소 --%>

	<%-- === 로그인을 하기 위한 폼을 생성 === --%>
	<div id="form_container">
	<form name="loginFrm">
		<%--action="<%=ctx_Path%>/login/login.mp" method="post" --%>

		<div id="inputIdPW">
			<div id="semiLogo"><a href="<%= ctx_Path%>/"><img src="<%= ctx_Path%>/images/index/logo.png"/></a></div>

			<div class="form-group">
				<div class="form-floating-label">
					<input type="text" class="form-control" id="loginUserid" name="userid" placeholder=" " autocomplete="off" /> 
					<label for="loginUserid">아이디</label>
				</div>
				<div id="idErrorMsg"></div>
			</div>

			<div class="form-group">
				<div class="form-floating-label">
					<input type="password" class="form-control" id="loginPwd" name="pwd" placeholder=" " /> 
					<label for="loginPwd">비밀번호</label>
				</div>
				<div id="pwdErrorMsg"></div>
				<div id="login_error"></div>
			</div>

			<div class="form-check custom-checkbox">
				<input type="checkbox" id="saveid" /> 
				<label class="form-check-label" for="saveid">아이디 저장</label>
			</div>

			<button type="button" class="loginjspBtn" id="btnSubmit">로그인</button>
	</form>

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
</div>


<%-- --------------------------------------------------------------- --%>
<jsp:include page="/WEB-INF/footer1.jsp" />


