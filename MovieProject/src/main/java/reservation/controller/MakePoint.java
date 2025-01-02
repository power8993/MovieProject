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

public class MakePoint extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST"
		int n = 0;
	    
		String message = "";
		String loc = "";
	    
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이라면
	       
			String userid = request.getParameter("userid");
			String using_point = request.getParameter("using_point");
			String ticketPrice = request.getParameter("ticketPrice");
			String imp_uid = request.getParameter("imp_uid");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("using_point", using_point);
			paraMap.put("ticketPrice", ticketPrice);
			paraMap.put("imp_uid", imp_uid);
			
			try {
				n = mdao.makePoint(paraMap);
			} catch(SQLException e) {
				message = "포인트 가져오기가 DB오류로 인해 실패되었습니다.";
				loc = "javascript:history.back()";
			}
			
		}
		else {
			// POST 방식이 아니라면
			message = "비정상적인 경로로 들어왔습니다.";
			loc = "javascript:history.back()";
		}
		
		JSONObject jsonObj = new JSONObject(); // {}
		jsonObj.put("n", n);
		String json = jsonObj.toString();
		request.setAttribute("json", json);
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
