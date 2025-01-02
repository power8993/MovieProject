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
		
		List<MovieVO> movieList = mdao.reservationMovieList();
		
		request.setAttribute("movieList", movieList);
		
		String seq_movie_no = request.getParameter("seq_movie_no");
		request.setAttribute("seq_moive_no", seq_movie_no);
		String start_date = request.getParameter("start_date");
		request.setAttribute("start_date", start_date);
		String start_time = request.getParameter("start_time");
		request.setAttribute("start_time", start_time);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/reservation/reservation.jsp");

	}

}
