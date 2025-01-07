package reservation.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReservationEnd extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();

		if(!"POST".equalsIgnoreCase(method)) {
			
			
			String userid = request.getParameter("userid");
			String imp_uid = request.getParameter("imp_uid");
			String name = request.getParameter("name");
		
			super.setViewPage("/WEB-INF/reservation/reservationEnd.jsp");
			
		}
		else {
			
		}
		
	}

}
