package movie.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.domain.MovieVO_wonjae;
import movie.model.*;

public class MovieDetail extends AbstractController {

	private MovieDAO_wonjae mdao = new MovieDAO_imple_wonjae();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		HttpSession session = request.getSession();
		
		MemberVO userid = (MemberVO) session.getAttribute("userid");
		
		int seq_movie_no = Integer.parseInt(request.getParameter("seq_movie_no"));

		// DB에서 해당 영화 정보를 가져옴
		MovieVO_wonjae mvo = mdao.movieDetail(seq_movie_no);
		
		request.setAttribute("mvo", mvo);
		request.setAttribute("userid", userid);
		
		
		super.setRedirect(false);
        super.setViewPage("/WEB-INF/movie/movieDetail.jsp");
		
	}
}
