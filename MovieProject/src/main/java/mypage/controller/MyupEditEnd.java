package mypage.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MyupEditEnd extends AbstractController {
	
	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		if ("POST".equalsIgnoreCase(method)) {
			// **** POST 방식으로 넘어온 것이라면 **** //

			String userid = request.getParameter("userid");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String hp1 = request.getParameter("hp1");
			String hp2 = request.getParameter("hp2");
			String hp3 = request.getParameter("hp3");

			String mobile = hp1 + hp2 + hp3;

			MemberVO member = new MemberVO();
			member.setUserid(userid);
			member.setName(name);
			member.setEmail(email);
			member.setMobile(mobile);

			// === 회원수정이 성공되어지면 "회원정보 수정 성공!!" 이라는 alert 를 띄우고 시작페이지로 이동한다. === //
			try {
				int n = mydao.updateMember(member);

				if (n == 1) {
					// !!!! session 에 저장된 loginuser 를 변경된 사용자의 정보값으로 변경해주어야 한다. !!!!
					HttpSession session = request.getSession();
					MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

					loginuser.setName(name);
					loginuser.setEmail(email);
					loginuser.setMobile(mobile);

					String message = "회원정보 수정 성공!!";
					String loc = request.getContextPath() + "/index.mp"; // 시작페이지로 이동한다.

					request.setAttribute("message", message);
					request.setAttribute("loc", loc);

					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
			} catch (Exception e) {
				e.printStackTrace();

				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/error.jsp");
			}

		}

	}


}
