package reservation.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.controller.GoogleMail;
import member.domain.MemberVO;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;
import reservation.domain.TicketVO;

public class SendReservationMail extends AbstractController {

	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
	    
		String message = "";
		String loc = "";
	    
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이면
		
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			
			int n = 0;
			
			GoogleMail mail = new GoogleMail();
			String userid = request.getParameter("userid");
			String imp_uid = request.getParameter("imp_uid");
			
			List<TicketVO> ticketlist = mdao.getTickets(userid, imp_uid);
			
			Map<String, String> map = mdao.getMovieTitle(imp_uid);
			
			if(ticketlist.size() == 0) {
				System.out.println("티켓이 없습니다.");
			}
			else {
				int ticketPrice = 0;
				String seat_str = "";
				for(int i = 0; i < ticketlist.size(); i++) {
					if(i == 0) {
						seat_str += ticketlist.get(i).getSeat_no();
					}
					else {
						seat_str += "," + ticketlist.get(i).getSeat_no();
					}
					ticketPrice += ticketlist.get(i).getTicket_price();
				}
				
				StringBuilder sb = new StringBuilder();
				
				sb.append("<div style='text-align:center; width:400px; margin-left:0'>");
				sb.append("<h1 style='color: #EB5E28; font-weight: bold;'>[HGV]</h1>");
				sb.append("안녕하세요&nbsp;" + loginuser.getName() + "님<br>[HGV]를 이용해주셔서 감사합니다.<br>");
				sb.append("예약하신 티켓 내역 확인 부탁드립니다.<br>");
				sb.append("결제번호 : <span style='color: blue; font-weight: bold;'>" + imp_uid + "</span><br><br>");
				
				sb.append("<티켓 내역><br>");
				
				sb.append("<div style='border:solid 1px gray; border-radius: 1px;'>");
				sb.append("<div><img src='http://localhost:9090/MovieProject/images/admin/poster_file/" + map.get("poster_file") + ".jpg' style='width:200px; height:auto; margin-top:10px;'></div>");
				sb.append("<hr style='width:90%; border:0px; border-top:5px solid black;'><div style='font-size:15pt; font-weight:bold;'>" + map.get("movie_title") + "<img src='http://localhost:9090/MovieProject/images/admin/movie_grade/" + map.get("movie_grade") + ".png' style='width:30px; height:auto;'></div><hr style='width:90%; border:0px; border-top:5px solid black;'>");
				sb.append("<div>");
				
				sb.append("<div>상영관 : [HGV] " + map.get("fk_screen_no") + "관</div>");
				sb.append("<div>예매좌석 : " + seat_str + "</div>");
				sb.append("<div>상영일시 : " + map.get("start_time") + "</div>");
				sb.append("<div>결제금액 : " + String.format("%,d", ticketPrice) + "원</div>");
				
				sb.append("<div><img src='http://localhost:9090/MovieProject/images/바코드.png' style='width:200px;'></div>");

				sb.append("</div>");
				
				
				sb.append("</div>");
				
				sb.append("<br>이용해 주셔서 감사합니다.</div>");

				String emailContents = sb.toString();
				
				mail.sendmail_OrderFinish(loginuser.getEmail(), loginuser.getName(), emailContents);
				
				n = 1;
				
			}
			
			JSONObject jsobj = new JSONObject(); // {}
			jsobj.put("n", n);  //  {"isSuccess":1} 또는 {"isSuccess":0} 
			
			String json = jsobj.toString();
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
