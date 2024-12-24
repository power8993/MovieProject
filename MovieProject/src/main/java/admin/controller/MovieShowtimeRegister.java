package admin.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO;
import movie.domain.ScreenVO;
import movie.domain.ShowtimeVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class MovieShowtimeRegister extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			
			String seq = request.getParameter("seq");
			String fk_screen_no = request.getParameter("screen_no");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time_val");
			
			if(fk_screen_no == null || fk_screen_no.isEmpty()) {
				// 상영일정 등록 첫 페이지
				MovieVO mvvo = mvdao.selectMovieDetail(seq);
				
				request.setAttribute("mvvo", mvvo);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin/movieShowtimeRegister.jsp");
			}
			else {
				// 상영일정 등록 페이지에서 폼 태그 전송 후
				int seq_int = Integer.parseInt(seq);
				int fk_screen_no_int =Integer.parseInt(fk_screen_no);
				
				MovieVO mvvo = new MovieVO();
				mvvo.setSeq_movie_no(seq_int);
				
				ScreenVO scvo = new ScreenVO();
				scvo.setScreen_no(fk_screen_no_int);
				mvvo.setScvo(scvo);
				
				ShowtimeVO showvo = new ShowtimeVO();
				showvo.setStart_time(start_time);
				showvo.setEnd_time(end_time);
				mvvo.setShowvo(showvo);
				
				try {
					
					// **** 상영일정을 등록해주는 메소드(tbl_showtime 테이블에 insert) **** //
					int n = mvdao.registerShowtime(mvvo);
					
					if(n == 1) {
						
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					
					String message = "상영일정 등록 실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				
			}
			
			
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}-----------------

}
