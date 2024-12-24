package common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public abstract class AbstractController implements InterCommand {

   private boolean isRedirect = false;
   // isRedirect 변수의 값이 false 이라면 view단 페이지(.jsp)로 forward 방법(dispatcher)으로 이동 
   // isRedirect 변수의 값이 true 이라면 sendRedirect 로 페이지이동
   
   private String viewPage;
   // viewPage 는 isRedirect 값이 false 이라면 view단 페이지(.jsp)의 경로명
   // isRedirect 값이 true 이라면 이동해야할 페이지 URL 주소

   public boolean isRedirect() {
      return isRedirect;
   }

   public void setRedirect(boolean isRedirect) {
      this.isRedirect = isRedirect;
   }

   public String getViewPage() {
      return viewPage;
   }

   public void setViewPage(String viewPage) {
      this.viewPage = viewPage;
   }
//////////////////////////////////////////////////////
//로그인 유무를 검사해서 로그인 했으면 true 를 리턴해주고
//로그인 안했으면 false 를 리턴해주도록 한다.
public boolean checkLogin(HttpServletRequest requst) {
HttpSession session = requst.getSession();
MemberVO loginuser=(MemberVO) session.getAttribute("loginuser");

if(loginuser != null) {
//로그인 한 경우
return true;
}else {
//로그인 안한 경우

return false;
}

}//end of public boolean checkLogin(HttpServletRequest requst)--
   
}
