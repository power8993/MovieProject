package reservation.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;
import reservation.domain.TicketVO;

public class ReservationEnd extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String method = request.getMethod();
		
		String message = "";
		String loc = "";
		
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이면
			
			String userid = request.getParameter("userid");
			String imp_uid = request.getParameter("imp_uid");
			
			int ticketPrice = 0;
			String seat_str = "";
			
			List<TicketVO> ticketlist = mdao.getTickets(userid, imp_uid);
			
			Map<String, String> map = mdao.getMovieTitle(imp_uid);
			
			for(int i = 0; i < ticketlist.size(); i++) {
				if(i == 0) {
					seat_str += ticketlist.get(i).getSeat_no();
				}
				else {
					seat_str += "," + ticketlist.get(i).getSeat_no();
				}
				ticketPrice += ticketlist.get(i).getTicket_price();
			}
			
			request.setAttribute("imp_uid", imp_uid);
			request.setAttribute("seat_str", seat_str);
			request.setAttribute("ticketPrice", ticketPrice);
			request.setAttribute("movie_grade", map.get("movie_grade"));
			request.setAttribute("start_time", map.get("start_time"));
			request.setAttribute("fk_screen_no", map.get("fk_screen_no"));
			request.setAttribute("poster_file", map.get("poster_file"));
			request.setAttribute("movie_title", map.get("movie_title"));
			
			super.setViewPage("/WEB-INF/reservation/reservationEnd.jsp");
			
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
