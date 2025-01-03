package movie.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieReviewVO;
import movie.model.MovieDAO_imple_wonjae;
import movie.model.MovieDAO_wonjae;

public class ReviwDetail extends AbstractController {

	private MovieDAO_wonjae mdao = new MovieDAO_imple_wonjae();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();

		// 영화 번호 파라미터
		String seq_movie_no = request.getParameter("seq_movie_no");

		if ("POST".equalsIgnoreCase(method)) {
			// riview 조회
			List<MovieReviewVO> mrList = mdao.reviewDetail(Integer.parseInt(seq_movie_no));

			JSONArray mrArray = new JSONArray();

			for (MovieReviewVO mvo : mrList) {

				JSONObject jsonObj = new JSONObject();
				jsonObj.put("userid", mvo.getFk_user_id());
				jsonObj.put("movie_rating", mvo.getMovie_rating());
				jsonObj.put("review_content", mvo.getReview_content());
				jsonObj.put("review_write_date", mvo.getReview_write_date());

				mrArray.put(jsonObj);
			} // end of for-------------------------------

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("mrList", mrArray);

			String json = jsonObj.toString();

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
