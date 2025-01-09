package reservation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;
import reservation.domain.TicketVO;

public class ReservationCancel extends AbstractController {
	
	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
		if(referer == null) {
			// referer == null 은 웹브라우저 주소창에 URL 을 직접 입력하고 들어온 경우
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/");
			return;
		}
		
		String message = "";
		String loc = "";
		
		if( !super.checkLogin(request) ) {
			// 로그인 하지 않았을 경우
			
			message = "영화 예약를 하기 위해서는 먼저 로그인을 하세요";
			loc = request.getContextPath() + "/login/login.mp";
	         
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
	         
			// super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			return;
		}
		else {
			// 로그인 했을 경우
			
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method)) {
				// POST 방식이면
				
				HttpSession session = request.getSession();
				MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
				String userid = loginuser.getUserid(); // 사용자ID
				String imp_uid = request.getParameter("imp_uid");
				String seq_showtime_no = request.getParameter("seq_showtime_no");
				
				Map<String, String> paramap = new HashMap<>();
				String seatList = mdao.getSeatList(imp_uid);
				// String seatArr = mdao.getSeatArr(seq_showtime_no);
				String seatArr = mdao.getSeatArr(60);
				String[] selected_seat_arr = seatList.split(",");
				String[] seat_arr = seatArr.split(",");
				
				int seatListLength = selected_seat_arr.length;
				
				for(int i = 0; i < seatListLength; i++) {
					int index = (selected_seat_arr[i].charAt(0) - 'A') * 10 + Integer.parseInt(selected_seat_arr[i].substring(1)) - 1;
					seat_arr[index] = "0";
				}
				
				seatArr = String.join(",", seat_arr);
				
				paramap.put("userid", userid);
				paramap.put("imp_uid", imp_uid);
				paramap.put("seq_showtime_no", seq_showtime_no);
				paramap.put("seatList", seatList);
				paramap.put("seatArr", seatArr);
				paramap.put("seatListLength", "" + seatListLength);
				
				int n = mdao.reservationCancel(paramap);
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("n", n);
				
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

}
