package reservation.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import movie.domain.MovieVO;
import movie.model.MovieDAO_imple_sunghoon;
import movie.model.MovieDAO_sunghoon;

public class Reservation extends AbstractController {
	
	private MovieDAO_sunghoon mdao = new MovieDAO_imple_sunghoon();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
		// System.out.println("~~~ 확인용 referer : " + referer);
		
		if(referer == null) {
			// referer == null 은 웹브라우저 주소창에 URL 을 직접 입력하고 들어온 경우
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/reservation/reservation.mp");
			return;
		}
		
		List<MovieVO> movieList = mdao.reservationMovieList();
		
		request.setAttribute("movieList", movieList);
		
		String seq_movie_no = request.getParameter("seq_movie_no");
		request.setAttribute("seq_moive_no", seq_movie_no);
		String start_date = request.getParameter("start_date");
		request.setAttribute("start_date", start_date);
		String start_time = request.getParameter("start_time");
		request.setAttribute("start_time", start_time);
		String fk_screen_no = request.getParameter("fk_screen_no");
		request.setAttribute("fk_screen_no", fk_screen_no);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/reservation/reservation.jsp");

	}

}
