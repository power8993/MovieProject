package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IdpwFind extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//아이디찾기인지 비밀번호 찾기인지 확인하기 위해 값을 받아옴.
		String idpwFind = request.getParameter("action");
		String method = request.getMethod();
		
		if(method.equalsIgnoreCase("GET")) {
			
		
			if("findId".equals(idpwFind)) {//아이디 찾기.jsp로 이동
				
				//super.setRedirect(false); 
	            super.setViewPage("/WEB-INF/login/idFind.jsp");
	            
			}
			else if("findPw".equals(idpwFind)) {//비밀번호 찾기.jsp로 이동
				
				//super.setRedirect(false); 
	            super.setViewPage("/WEB-INF/login/pwdFind.jsp");
	            
			}
			else {
				//실행 전 페이지로 돌아가는 함수
				/*
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
			//	super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				*/
			}
		}
		else {
			//post 방식으로 요청된 경우
		}
		
		

	}

}
