package mypage.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class Mypage extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		  if (super.checkLogin(request)) {
		        HttpSession session = request.getSession();
		        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		        if (loginuser == null) {
		            request.setAttribute("message", "로그인 세션이 만료되었습니다.");
		            request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
		            super.setViewPage("/WEB-INF/msg.jsp");
		            return;
		        }

		        // 세션의 사용자 ID 사용
		        //System.out.println("로그인된 사용자 ID: " + loginuser.getUserid());

		        // 사용자 인증 성공
		        super.setViewPage("/WEB-INF/mypage/mypage.jsp");
		    } else {
		        request.setAttribute("message", "로그인이 필요합니다.");
		        request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
		        super.setViewPage("/WEB-INF/msg.jsp");
		    }
	}

}
