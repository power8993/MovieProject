<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<jsp:include page="../header1.jsp" /> 

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/memberRegister1.css" />

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script> 
<script type="text/javascript" src="<%= ctxPath%>/js/member/memberRegister1.js"></script> 

<div class="row" id="divRegisterFrm">
   <div class="col-md-12">
      <form name="registerFrm">
      <div style="width:400px; margin: 0 auto; margin-top:100px;">
      <div id="registerTitle"><p>회원가입</p></div>
      
	      <label class="labelName" >성명</label>
		  <input type="text" name="name" id="name"size="25" autocomplete="off" placeholder="성명을 입력하세요"/> 
	          <span class="error">성명을 입력해 주세요.</span>
	       
	          
	      <label class="labelName" >아이디</label>
	      <div style="display:flex;">
		  <input type="text" name="userid" id="userid"size="25" autocomplete="off" placeholder="아이디를 입력하세요" /> 
		  	<button  type="button" id="idcheck">중복확인</button>
	       </div>   
	       <span class="error">숫자,소문자 포함 형태의 4~15 자리 이내의 아이디를 입력해 주세요.</span>
	        
	        <%-- 아이디중복체크 --%>
           <span id="idcheckResult"></span>
                       
	      <label class="labelName">비밀번호</label>
		  <input type="password" name="pwd" id="pwd"size="25" autocomplete="off" placeholder="비밀번호를 입력하세요"/> 
	      <span class="error">숫자,문자,특수문자 포함 형태의 8~15자리 이내의 비밀번호를 입력해 주세요.</span>    
	          
	      <label class="labelName" >비밀번호 확인</label>
		  <input type="password" name="pwdcheck" id="pwdcheck"size="25" autocomplete="off" placeholder="비밀번호를 입력하세요"/> 
	      <span class="error">암호가 일치하지 않습니다.</span>
	          
	      <label class="labelName">이메일</label>
	      <div style="display:flex;">
		  <input type="text" name="email" id="email"size="25" autocomplete="off" placeholder="이메일을 입력하세요"/>
		  	<button  type="button" id="emailcheck" >중복확인</button>
	      </div>
	       <span class="error">이메일 주소가 정확한지 확인해 주세요.</span>
	          <%-- 이메일중복체크 --%>
	                       <span id="emailCheckResult"></span>
	      
	      <label class="labelName" >전화번호</label>
	      <div style="widht:100%;">
		  <input type="text" name="hp1" id="hp1" size="6" maxlength="3" value="010" readonly/>&nbsp;-&nbsp; 
	      <input type="text" name="hp2" id="hp2" size="6" maxlength="4" class="requiredInfo" autocomplete="off" placeholder="1234"/>&nbsp;-&nbsp;
	      <input type="text" name="hp3" id="hp3" size="6" maxlength="4" class="requiredInfo" autocomplete="off" placeholder="5678"/>   
	      	<button  type="button" id="phoneCheckandAuth" style="margin-left:19px;" >인증번호 전송</button>
	      </div>
	      <span class="error" id="hp_error">전화번호를 정확하게 입력해 주세요.</span>  
	      <div id="authMsg"></div>
	      <%-- 인증 버튼을 클릭하면 나오는 요소. 밑의 인증하는 요소들은 페이지가 로드되면 hide() 됨. 즉, 전화번호가 DB 상에 존재하지 않는다면 show()됨. --%>
	      <div id="authPassElmt">
		      <input type="text" name="authPassInput" id="authPassValue" autocomplete="off"/> 
		      <button id="authPassBtn" type="button">인증하기</button>
	      </div>
	      
	      
	     
	     <div style="display:flex; justify-content: space-between; ">
		     <div>
			     <label class="labelName" >성별</label>
				 <input type="radio" name="gender" value="1" id="male" class="requiredInfo_radio"/><label for="male" style="margin-left: 1.5%;">남자</label>
			     <input type="radio" name="gender" value="2" id="female" class="requiredInfo_radio" style="margin-left: 10%;" /><label for="female" style="margin-left: 1.5%;">여자</label>
			          <span class="error" id="gender_error">성별을 선택해 주세요.</span>
			  </div>    
			  <div class="info" id="birth">
			  <label class="labelName">생년월일</label>
				  <div id="only_birth"style="display:flex;">
					  <select class="box" id="birth-year" name="year" >
					    <option disabled selected value="">출생 연도</option>
					  </select>
					  <select class="box" id="birth-month" name="month">
					    <option disabled selected value="">월</option>
					  </select>
					  <select class="box" id="birth-day" name="day">
					    <option disabled selected value="">일</option>
					  </select>
				  </div>
			  <span class="error" id="birth_error">생년월일을 모두 선택해 주세요.</span>
			  </div>
	      </div>                 
	  </div>         
	          
          
          
          <table id="tblMemberRegister">
             <tbody>
                
                <tr>
                    <td colspan="2">
                       <label for="agree">이용약관에 동의합니다</label>&nbsp;&nbsp;<input type="checkbox" id="agree" />
                    </td>
                </tr>
                
                <tr>
                    <td colspan="2">
                       <iframe src="<%= ctxPath%>/iframe_agree/agree1.html" width="100%" height="150px" style="border: solid 1px #ff7a00;"></iframe>
                    </td>
                </tr>
                
                <tr>
                    <td colspan="2" class="text-center">
                       <input type="button" id="registerbtn" value="가입하기" onclick="goRegister()" />
                    </td>
                </tr>
                 
             </tbody>
          </table>
       
       <%--    
          <div>
          	 <button onclick="goGaib()">type이 없으면 submit 임</button>&nbsp; 
          	 <button type="button" onclick="goGaib()">type이 button 인것</button>&nbsp;
          	 <button type="submit">type이 submit 인 것</button>
          </div>
       --%>   
      </form>
   </div>
</div>



<jsp:include page="../footer1.jsp" />
   