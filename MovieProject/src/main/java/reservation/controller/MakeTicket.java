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
		int n = 0;
	    
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
					n = mdao.makeTicket(ticket);
				} catch(SQLException e) {
					message = "영화 티켓 결제가 DB오류로 인해 실패되었습니다.";
					loc = "javascript:history.back()";
				}
			}
			
		}
		else {
			// POST 방식이 아니라면
			message = "비정상적인 경로로 들어왔습니다.";
			loc = "javascript:history.back()";
		}
		
		JSONObject jsonObj = new JSONObject(); // {}
		jsonObj.put("n", n);             // {"n":1} 또는 {"n":0}
//		jsonObj.put("message", message); // {"n":1, "message":"김성훈님의 300,000원 결제가 완료되었습니다."} 
//		jsonObj.put("loc", loc);         // {"n":1, "message":"김성훈님의 300,000원 결제가 완료되었습니다.", "loc":"/MyMVC/index.up"}   
		String json = jsonObj.toString();  // "{"n":1, "message":"김성훈님의 300,000원 결제가 완료되었습니다.", "loc":"/MyMVC/index.up"}"
		request.setAttribute("json", json);
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
		
	}

}
