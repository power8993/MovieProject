package reservation.controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class MakePayment extends AbstractController {
	
	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST"
	    
		String message = "";
		String loc = "";
	    
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이면
			
			String userid = request.getParameter("userid");
			String ticketPrice = request.getParameter("ticketPrice");
			String imp_uid = request.getParameter("imp_uid");
			String seq_showtime_no = request.getParameter("seq_showtime_no");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("ticketPrice", ticketPrice);
			paraMap.put("imp_uid", imp_uid);
			paraMap.put("seq_showtime_no", seq_showtime_no);
			paraMap.put("status", "결제 완료");
			
			int n = 0;
			try {
				n = mdao.makePayment(paraMap);
			} catch(SQLException e) {
				message = "영화 티켓 결제가 DB오류로 인해 실패되었습니다.";
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
