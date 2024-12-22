package reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equalsIgnoreCase(method)) {
	        // 비밀번호 찾기 모달창에서 "찾기" 버튼을 클릭했을 경우
			
			String input_date = request.getParameter("input_date");
			String seq_movie_no = request.getParameter("seq_movie_no");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("input_date", input_date);
			paraMap.put("seq_movie_no", seq_movie_no);
			
			List<ShowTimeVO_sunghoon> showTimeList = mdao.getScreenTime(paraMap);
			
			System.out.println(showTimeList.size());
			
			request.setAttribute("showTimeList", showTimeList);
			
			
		} // end of if("POST".equalsIgnoreCase(method))-----------------------------------------
			
		request.setAttribute("method", method);
		
		super.setRedirect(false);
	    super.setViewPage("/WEB-INF/reservation/reservation.jsp");
	}

}
