package reservation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class MakeTicket extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
	    
		String message = "";
		String loc = "";
	    
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이라면
	       
			List<Integer> ticketPriceList = mdao.getTicketPrice();
			
			int adult_ticket_price = ticketPriceList.get(0);
			int adolescent_ticket_price = ticketPriceList.get(1);
			int youth_ticket_price = ticketPriceList.get(2);
			
			String imp_uid = request.getParameter("imp_uid");
			int adult_cnt = Integer.parseInt(request.getParameter("adult_cnt"));
			int adolescent_cnt = Integer.parseInt(request.getParameter("adolescent_cnt"));
			int youth_cnt = Integer.parseInt(request.getParameter("youth_cnt"));
			String seat_arr_str = request.getParameter("seat_arr");
			String[] seat_arr = seat_arr_str.split(",");
			
			int sum = 1;
			
			for (int i = 0; i < seat_arr.length; i++) {
				TicketVO_hoon ticket = new TicketVO_hoon();
				ticket.setFk_imp_uid(imp_uid);
				ticket.setSeat_no(seat_arr[i]);
				if(i < adult_cnt) {
					ticket.setTicket_price(adult_ticket_price);
					ticket.setTicket_age_group("성인");
				}
				else if(i - adult_cnt < adolescent_cnt) {
					ticket.setTicket_price(adolescent_ticket_price);
					ticket.setTicket_age_group("청소년");
				}
				else {
					ticket.setTicket_price(youth_ticket_price);
					ticket.setTicket_age_group("어린이");
				}
				
				try {
					int n = mdao.makeTicket(ticket);
					sum *= n;
				} catch(SQLException e) {
					message = "영화 티켓 결제가 DB오류로 인해 실패되었습니다.";
					loc = "javascript:history.back()";
				}
				
				
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("sum", sum);
			
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
