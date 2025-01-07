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
		
		if(!"POST".equalsIgnoreCase(method)) {
			// POST 방식이라면
			
			String userid = request.getParameter("userid");
			String imp_uid = request.getParameter("imp_uid");
			String name = request.getParameter("name");
			
			List<TicketVO> ticketlist = mdao.getTickets(userid, imp_uid);
			
			Map<String, String> map = mdao.getMovieTitle(imp_uid);
			
			request.setAttribute("imp_uid", imp_uid);
			request.setAttribute("imp_uid", imp_uid);
			request.setAttribute("imp_uid", imp_uid);
			request.setAttribute("imp_uid", imp_uid);
			request.setAttribute("imp_uid", imp_uid);
			
		
			super.setViewPage("/WEB-INF/reservation/reservationEnd.jsp");
			
		}
		else {
			// POST 방식이 아니라면
		}
		
	}

}
