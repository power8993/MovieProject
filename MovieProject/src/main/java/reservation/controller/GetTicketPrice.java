package reservation.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class GetTicketPrice extends AbstractController {
	
	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Integer> ticketPriceList = mdao.getTicketPrice();
		
		JSONObject jsonObj = new JSONObject();
			
		jsonObj.put("adult_ticket_price", ticketPriceList.get(0));		// 가격순으로 정렬해서 제일 비싼 순으로 성인, 청소년, 어린이
		jsonObj.put("adolescent_ticket_price", ticketPriceList.get(1));
		jsonObj.put("youth_ticket_price", ticketPriceList.get(2));
		
		String json = jsonObj.toString(); // 문자열로 변환
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
