package reservation.controller;

import java.util.List;

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
		
		List<Integer> ticketPriceList = mdao.getTicketPrice();
		
		int adult_ticket_price = ticketPriceList.get(0);
		int adolescent_ticket_price = ticketPriceList.get(1);
		int youth_ticket_price = ticketPriceList.get(2);
		
		
		
	}

}
