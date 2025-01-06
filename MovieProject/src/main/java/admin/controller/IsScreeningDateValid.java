package admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class IsScreeningDateValid extends AbstractController {

	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String seq = request.getParameter("seq");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("start_date", start_date);
		paraMap.put("end_date", end_date);
		paraMap.put("seq", seq);
		
		// 입력한 상영시작일과 상영종료일이 해당 영화의 상영 일정들에 모두 포함되는지 확인하는 메소드
		boolean isDateValid = mvdao.isDateValidCheck(paraMap);
		
		// Ajax 로 호출 후 값을 넘겨줄 때 JSON 형식으로 전송
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("isDateValid", isDateValid);
		
		String json = jsonObj.toString();
		
		// 서버에서 클라이언트(ajax)로 JSON 응답을 보낸다
		response.setContentType("application/json");
		response.getWriter().write(json); 
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}-------------------

}
