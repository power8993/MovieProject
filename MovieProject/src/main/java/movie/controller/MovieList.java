package movie.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.CategoryVO_yeo;
import movie.domain.MovieVO;
import movie.domain.MovieVO_yeo;
import movie.model.*;

public class MovieList extends AbstractController {
	
	private MovieDAO_yeo mdao = new MovieDAO_imple_yeo();

	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       

        try {
            // 영화 데이터 가져오기
        	List<MovieVO_yeo> movies = mdao.select_Movies();
        	// 영화 데이터를 request에 저장
            request.setAttribute("movies", movies);
            
                      
            // 장르 종류 가져오기
        	List<CategoryVO_yeo> cgList =  mdao.selectcategory();
            request.setAttribute("cgList", cgList);
           
            // View 경로 설정 (forward 방식)
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/movie/movieList.jsp");

        } catch (SQLException e) {
            e.printStackTrace();

            // 에러 페이지로 이동 (forward 방식)
            //request.setAttribute("errorMessage", "영화 데이터를 불러오는 중 오류가 발생했습니다.");
            //super.setRedirect(false);
            //super.setViewPage("/WEB-INF/error/error.jsp");
        }
    }
}