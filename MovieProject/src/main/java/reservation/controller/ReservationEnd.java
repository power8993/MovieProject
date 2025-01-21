package reservation.controller;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
			
			String userid = request.getParameter("userid");		// 사용자ID
			String imp_uid = request.getParameter("imp_uid");	// 결제번호
			
			int ticketPrice_int = 0;
			String seat_str = "";
			
			List<TicketVO> ticketlist = mdao.getTickets(userid, imp_uid);
			// 티켓 목록을 DB에서 가져옴
			
			Map<String, String> map = mdao.getMovieTitle(imp_uid);
			// 영화 제목을 DB에서 가져옴
			
			for(int i = 0; i < ticketlist.size(); i++) {
				// 티켓 목록의 좌석들을 하나의 String으로 합치기
				if(i == 0) {
					seat_str += ticketlist.get(i).getSeat_no();
				}
				else {
					seat_str += "," + ticketlist.get(i).getSeat_no();
				}
				ticketPrice_int += ticketlist.get(i).getTicket_price();
				// 티켓 목록들의 총 가격
			}
			
			String ticketPrice = String.format("%,d", ticketPrice_int);
			// 티켓 가격 세자리마다 , 찍어주기
			
			request.setAttribute("imp_uid", imp_uid);						// 결제 번호
			request.setAttribute("seat_str", seat_str);						// 예매한 좌석들
			request.setAttribute("ticketPrice", ticketPrice);				// 결제금액
			request.setAttribute("movie_grade", map.get("movie_grade"));	// 영화 등급
			request.setAttribute("start_time", map.get("start_time"));		// 상영 영화 시작 시간
			request.setAttribute("end_time", map.get("end_time"));			// 상영 영화 종료 시간
			request.setAttribute("fk_screen_no", map.get("fk_screen_no"));	// 상영관 번호
			request.setAttribute("poster_file", map.get("poster_file"));	// 포스터 이미지 경로
			request.setAttribute("movie_title", map.get("movie_title"));	// 영화 제목
			
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
