package reservation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class UpdateShowtime extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST"
	    
		String message = "";
		String loc = "";
	    
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이라면
			String selected_seat_str = request.getParameter("seat_arr");
			String[] selected_seat_arr = selected_seat_str.split(",");
			
			int selected_seat_arr_length = selected_seat_arr.length;

			int seq_showtime_no = Integer.parseInt(request.getParameter("seq_showtime_no"));
			
			int n = 0;
			
			try {
				
				String seat_str = mdao.getSeatArr(seq_showtime_no);	// 상영영화의 좌석 배열 가져오기
				
				String[] seat_arr = seat_str.split(",");
				
				boolean isDuplicated = false; // 좌석이 중복되서 예약되었나 확인하는 변수
				
				for(int i = 0; i < selected_seat_arr.length; i++) {
					int index = (selected_seat_arr[i].charAt(0) - 'A') * 10 + Integer.parseInt(selected_seat_arr[i].substring(1)) - 1;
					// 좌석번호 A1 -> index : 0, A2 -> index : 1 식으로 변환
					if(seat_arr[index] == "1") {
						// 이미 예약된 좌석일 경우
						isDuplicated = true;
						break;
					};
					seat_arr[index] = "1";
				}
				
				if(isDuplicated) {
					n = -1;
				}
				else {
					seat_str = String.join(",", seat_arr);
					
					n = mdao.updateShowtime(seat_str, seq_showtime_no, selected_seat_arr_length);
				}
				
				
			} catch(SQLException e) {
				message = "상영 영화 정보 수정이 DB오류로 인해 실패되었습니다.";
				loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
	              
				super.setRedirect(false);   
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("n", n);
			String json = jsonObj.toString();
			request.setAttribute("json", json);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
		else {
			// GET 방식이면
			
			message = "비정상적인 경로로 들어왔습니다.";
			loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
              
			super.setRedirect(false);   
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}

}
