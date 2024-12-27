package mypage.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class Mymovielike extends AbstractController {
	
	private MypageDAO mydao = new MypageDAO_imple();

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

		String userid = loginuser.getUserid();
		//System.out.println("세션에서 가져온 userid: " + userid);

		int totalmymovielike = mydao.totalmymovielike(userid);// 전체개수를 알아온다.

		//System.out.println("~~~~ 확인용 totalmymovielike : " + totalmymovielike);

		request.setAttribute("totalmymovielike", totalmymovielike);

		super.setRedirect(false);
		super.setViewPage("/WEB-INF/mypage/mymovielike.jsp");
		
		} else {
			request.setAttribute("message", "로그인이 필요합니다.");
			request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}

	}

}
