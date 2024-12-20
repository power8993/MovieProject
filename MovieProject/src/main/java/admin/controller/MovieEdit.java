package admin.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class MovieEdit extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if ("POST".equalsIgnoreCase(method)) {
			
			String seq = request.getParameter("seq");
			
			MovieVO mvvo = mvdao.selectMovieDetail(seq);
			
			request.setAttribute("mvvo", mvvo);
			request.setAttribute("content", mvvo.getContent().replace("<br>","\n"));
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/movieEdit.jsp");
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}-----------------

}
