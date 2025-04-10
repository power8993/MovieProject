package mypage.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;
import reservation.domain.PaymentVO;

public class MyreservationReceipt extends AbstractController {

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

			try {
				String imp_uid = request.getParameter("imp_uid");
				
				// 예매내역 리스트
				List<PaymentVO> myreservationList_impUid = mydao.myreservationList_impUid(imp_uid);
				request.setAttribute("myreservationList_impUid", myreservationList_impUid);

				super.setRedirect(false);
				super.setViewPage("/WEB-INF/mypage/myreservationReceipt.jsp");

			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/error.up");
			}
		} else {
			request.setAttribute("message", "로그인이 필요합니다.");
			request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
