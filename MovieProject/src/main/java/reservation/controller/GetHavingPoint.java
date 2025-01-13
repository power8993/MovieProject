package reservation.controller;

import java.sql.SQLException;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class GetHavingPoint extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST"
	    
		String message = "";
		String loc = "";
	    
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이면
	       
			String userid = request.getParameter("userid");
			
			int havingPoint = 0;
			
			try {
				havingPoint = mdao.getHavingPoint(userid);
			} catch(SQLException e) {
				message = "데이터베이스 오류로 인해 포인트 가져오기 실패되었습니다";
				loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
	              
				super.setRedirect(false);   
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("havingPoint", havingPoint);
			
			String json = jsonObj.toString();
			request.setAttribute("json", json);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}
		else {
			// GET 방식이면
			
			message = "비정상적인 경로로 들어왔습니다";
			loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
              
			super.setRedirect(false);   
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}

}
