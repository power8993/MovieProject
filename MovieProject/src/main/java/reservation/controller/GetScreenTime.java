package reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.controller.GoogleMail;
import movie.domain.ShowTimeVO_sunghoon;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class GetScreenTime extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String input_date = request.getParameter("input_date"); // 선택한 날짜(yyyy-mm-dd) 형태
		if(input_date != null) {
			input_date = String.join("", input_date.split("-"));
			// yyyy-mm-dd 형태의 날짜 문자열을 를 yyyymmdd 로 바꾸기
		}
		String seq_movie_no = request.getParameter("seq_movie_no"); // 선택한 영화번호
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("input_date", input_date);
		paraMap.put("seq_movie_no", seq_movie_no);
		
		List<ShowTimeVO_sunghoon> showTimeList = mdao.getScreenTime(paraMap);
		// 선택한 영화와 날짜에 해당하는 상영 영화 리스트를 가져옴
		
		JSONArray jsonArr = new JSONArray(); // []
		
		if(showTimeList.size() > 0) {
			// DB에서 조회해온 결과물이 있을 경우
			
			for (ShowTimeVO_sunghoon svo : showTimeList) {
				
				JSONObject jsonObj = new JSONObject(); // {}
				
				jsonObj.put("start_time", svo.getStart_time()); 			// 상영 영화 시작 시간
				jsonObj.put("end_time", svo.getEnd_time());					// 상영 영화 종료 시간
				jsonObj.put("seat_arr", svo.getSeat_arr());					// 해당 영화의 좌석 배열
				jsonObj.put("seq_showtime_no", svo.getSeq_showtime_no());	// 상영 영화번호
	            jsonObj.put("fk_seq_movie_no", svo.getFk_seq_movie_no());	// 영화 번호
	            jsonObj.put("total_viewer", svo.getTotal_viewer());			// 관람객 수
	            jsonObj.put("unused_seat", svo.getUnused_seat());			// 남은 좌석
	            jsonObj.put("fk_screen_no", svo.getFk_screen_no());			// 상영관 번호
				
	            jsonArr.put(jsonObj);
	            
			} // end of for----------------------------------------------
			
		} // end of if(showTimeList.size() > 0) -----------------------------------------------------------
		
		String json = jsonArr.toString(); // 문자열로 변환
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
			
	}

}
