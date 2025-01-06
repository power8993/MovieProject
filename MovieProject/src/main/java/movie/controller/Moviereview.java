package movie.controller;

import org.json.JSONObject;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.model.MovieDAO_wonjae;
import movie.domain.MovieReviewVO;
import movie.model.MovieDAO_imple_wonjae;

public class Moviereview extends AbstractController {

    private MovieDAO_wonjae mdao = new MovieDAO_imple_wonjae();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        // 영화 번호 파라미터
        String seq_movie_no = request.getParameter("seq_movie_no");

        HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

        if ("POST".equalsIgnoreCase(method)) {

            boolean isPaid = mdao.checkuserpay(loginuser, Integer.parseInt(seq_movie_no));

            JSONObject jsobj = new JSONObject();

            if (isPaid) {
            	// 리뷰 관련 파라미터 받기
                String rating = request.getParameter("rating");  // 별점
                String review = request.getParameter("review");  // 리뷰 내용
       
                // 리뷰 데이터 DB에 저장하고, MovieReviewVO 객체 반환
                MovieReviewVO mrvo = mdao.submitReview(Integer.parseInt(seq_movie_no), loginuser, Integer.parseInt(rating), review);

                if (mrvo != null) {
                	jsobj.put("n", 1);  // 리뷰 저장 성공  
                	
                	// 리뷰 정보를 JSON으로 구성
                    JSONObject reviewObj = new JSONObject();
                    reviewObj.put("user_id", mrvo.getFk_user_id());
                    reviewObj.put("movie_rating", mrvo.getMovie_rating());
                    reviewObj.put("review_content", mrvo.getReview_content());
                    reviewObj.put("review_write_date", mrvo.getReview_write_date());

                    jsobj.put("review", reviewObj);  // review 정보를 포함
                    
                    String json = jsobj.toString();
                    
                    request.setAttribute("review", json);
                } 
                else {
                    jsobj.put("n", 0);  // 리뷰 저장 실패
                }              
            } 
            else {
            	 jsobj.put("n", 2);  // 결제되지 않은 경우
            }

            String json = jsobj.toString();

            request.setAttribute("json", json);            
            
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
            	
        } else {
            String message = "잘못된 접근입니다.";
            String loc = "javascript:history.back()";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
        }
    }


}
