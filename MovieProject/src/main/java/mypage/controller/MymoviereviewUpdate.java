package mypage.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.domain.MovieReviewVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MymoviereviewUpdate extends AbstractController {

	private MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

			String method = request.getMethod(); // "GET" 또는 "POST"
			if ("POST".equalsIgnoreCase(method) && super.checkLogin(request)) {
				// **** POST 방식으로 넘어온 것이라면 **** //

				String userid = loginuser.getUserid();
				int seq_review_no = Integer.parseInt(request.getParameter("seq_review_no"));
				int movie_rating = Integer.parseInt(request.getParameter("movie_rating"));
				String review_content = request.getParameter("review_content");

				MovieReviewVO mrvo = new MovieReviewVO();
				mrvo.setFk_user_id(userid);
				mrvo.setSeq_review_no(seq_review_no);
				mrvo.setMovie_rating(movie_rating);
				mrvo.setReview_content(review_content);

				try {
					int n = mydao.mymoivereviewUpdate(mrvo);

					 JSONObject jsobj = new JSONObject();  // {}
			         jsobj.put("n", n);   // {"n":1}
			         
			         String json = jsobj.toString();   // "{"n":1}"
			         
			         request.setAttribute("json", json);
			         
			         super.setRedirect(false);
			         super.setViewPage("/WEB-INF/jsonview.jsp");
					
				} catch (Exception e) {
					e.printStackTrace();

					super.setRedirect(true);
					super.setViewPage(request.getContextPath() + "/error.jsp");
				}
			} else {
				// GET 요청 처리 (비밀번호 입력 페이지로 이동)
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/mypage/mymoviereview.jsp");
			}

		}

	}
