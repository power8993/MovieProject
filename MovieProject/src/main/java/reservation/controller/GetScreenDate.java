package reservation.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.ShowTimeVO_sunghoon;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class GetScreenDate extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String seq_movie_no = request.getParameter("seq_movie_no");
		
		List<String> screenDateList = mdao.getScreenDate(seq_movie_no);
		
		JSONArray jsonArr = new JSONArray(); // []
		
		if(screenDateList.size() > 0) {
			// DB에서 조회해온 결과물이 있을 경우
			
			for (String start_date : screenDateList) {
				
				JSONObject jsonObj = new JSONObject(); // {}
				
				jsonObj.put("start_date", start_date);
	            jsonArr.put(jsonObj);
	            
			} // end of for----------------------------------------------
			
		} // end of if(screenDateList.size() > 0) -----------------------------------------------------------
		
		String json = jsonArr.toString(); // 문자열로 변환
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
