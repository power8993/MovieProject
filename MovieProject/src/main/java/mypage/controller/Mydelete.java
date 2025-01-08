package mypage.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class Mydelete extends AbstractController {

	MypageDAO mydao = new MypageDAO_imple();

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

			// ★★★ 마이 프로필--나의 포인트 목록 ★★★ //
			List<Map<String, Object>> myreservationprofile = mydao.myreservationprofile(loginuser.getUserid());
			request.setAttribute("myreservationprofile", myreservationprofile);

			// ★★★ 마이 프로필--나의 영화 랭킹 ★★★ //
			List<Map<String, String>> myranking = mydao.myranking(loginuser.getUserid());
			request.setAttribute("myranking", myranking);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/mypage/mydelete.jsp");

		} else {
			request.setAttribute("message", "로그인이 필요합니다.");
			request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
