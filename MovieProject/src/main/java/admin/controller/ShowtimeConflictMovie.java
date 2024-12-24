package admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class ShowtimeConflictMovie extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equalsIgnoreCase(method)) {

			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String screen_no = request.getParameter("screen_no");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("start_time", start_time);
			paraMap.put("end_time", end_time);
			paraMap.put("screen_no", screen_no);
			
			// **** [상영시간 조회하기] 선택한 상영 시간과 상영관에 중첩된 상영이 있는지 확인하는 메소드 (select) **** //
			List<MovieVO> movieList =  mvdao.selectShowtimeConflict(paraMap);
			
			// movieList 를 JOSN 배열로 변환
			JSONArray movieArray = new JSONArray();
			
			for (MovieVO movie : movieList) {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("poster_file", movie.getPoster_file());
				jsonObj.put("movie_title", movie.getMovie_title());
				jsonObj.put("movie_grade", movie.getMovie_grade());
				jsonObj.put("category", movie.getCatevo().getCategory());
				jsonObj.put("screen_no", movie.getScvo().getScreen_no());
				jsonObj.put("running_time", movie.getRunning_time());
				jsonObj.put("start_time", movie.getShowvo().getStart_time());
				jsonObj.put("end_time", movie.getShowvo().getEnd_time());
				
				movieArray.put(jsonObj);
			}// end of for-------------------------------
			
			// ArrayList와 같은 복잡한 객체를 JSON으로 변환 후 보내는 경우 아래와 같이 전송
			// response.setContentType("application/json");
			// response.getWriter().write(movieArray.toString());
			
			// AJAX 로 여러 개(영화검색정보, ctxPath)의 데이터를 전송
			JSONObject jsonObj_ctxPath = new JSONObject();
			jsonObj_ctxPath.put("movie_List", movieArray);
			jsonObj_ctxPath.put("ctxPath", request.getContextPath());
			
			response.setContentType("application/json"); // json 형태로 보내준다.
			response.getWriter().write(jsonObj_ctxPath.toString());
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}---------------------

}
