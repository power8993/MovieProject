package movie.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO;
import movie.domain.MovieVO_yeo;
import movie.model.MoveDAO_imple_yeo;
import movie.model.MovieDAO_yeo;

public class RunningMovies extends AbstractController {

	private MovieDAO_yeo mdao = new MoveDAO_imple_yeo();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 try {
			 // 영화 데이터 가져오기
	        	List<MovieVO_yeo> movies = mdao.select_run_Movies();

	            // 영화 데이터를 request에 저장
	            request.setAttribute("movies", movies);

	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/movie/runningMovies.jsp");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	}

}
