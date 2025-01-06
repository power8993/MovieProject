package mypage.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.domain.MovieLikeVO;
import movie.domain.MovieReviewVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;
import reservation.domain.PaymentVO;

public class Mypage extends AbstractController {

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
				//마이페이지 메인 리스트 = 나의 예매내역
				List<PaymentVO> main_mypage_Myreservationlist = mydao.main_mypage_Myreservationlist(loginuser.getUserid());
				request.setAttribute("main_mypage_Myreservationlist", main_mypage_Myreservationlist);

					
				//마이페이지 메인 리스트 = 내가 본 영화
				List<PaymentVO> main_mypage_MovieWatchedList = mydao.main_mypage_MovieWatchedList(loginuser.getUserid());
				request.setAttribute("main_mypage_MovieWatchedList", main_mypage_MovieWatchedList);

				//마이페이지 메인 리스트 = 내가 쓴 리뷰
				List<MovieReviewVO> main_mypage_MovieReviewList = mydao.main_mypage_MovieReviewList(loginuser.getUserid());
				request.setAttribute("main_mypage_MovieReviewList", main_mypage_MovieReviewList);

				//마이페이지 메인 리스트 = 기대되는 영화
				List<MovieLikeVO> main_mypage_MovieLikeList = mydao.main_mypage_MovieLikeList(loginuser.getUserid());
				request.setAttribute("main_mypage_MovieLikeList", main_mypage_MovieLikeList);

				super.setRedirect(false);
				super.setViewPage("/WEB-INF/mypage/mypage.jsp");

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