package movie.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.CategoryVO_yeo;
import movie.domain.MovieVO_yeo;
import movie.model.MovieDAO_imple_yeo;
import movie.model.MovieDAO_yeo;

public class AllfilterByGenre extends AbstractController {

	private MovieDAO_yeo mdao = new MovieDAO_imple_yeo();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    // 1. 클라이언트로부터 전달받은 genreCode를 가져옵니다.
	    String genreCode = request.getParameter("genreCode");

	    try {
	       
	        List<MovieVO_yeo> movies;
	        if (genreCode != null && !genreCode.trim().isEmpty()) {
	            movies = mdao.allgetMoviesByGenre(genreCode); // 특정 장르의 영화 가져오기(전체 )
	        } else {
	            movies = mdao.select_Movies(); // 모든 영화 가져오기
	        }
	        
	        // 장르 종류 가져오기
        	List<CategoryVO_yeo> cgList =  mdao.selectcategory();
            request.setAttribute("cgList", cgList);

	        request.setAttribute("movies", movies);
	        super.setViewPage("/WEB-INF/movie/movieList.jsp");
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
