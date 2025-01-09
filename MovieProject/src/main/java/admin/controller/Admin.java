package admin.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class Admin extends AbstractController {

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
			else {
				
				if("admin".equals(loginuser.getUserid())) {
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/admin/admin.jsp");
				}
				else {
					// 일반사용자로 로그인 한 경우
					request.setAttribute("message", "관리자만 접근이 가능합니다.");
					request.setAttribute("loc", request.getContextPath() + "/index.mp");
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				
			}
			
		} 
		else {
			request.setAttribute("message", "로그인이 필요합니다.");
			request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}

}
