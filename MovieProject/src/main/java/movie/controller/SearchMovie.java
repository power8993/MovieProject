package movie.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO_imple_yeo;
import movie.model.MovieDAO_yeo;
import movie.domain.MovieVO_yeo;
import java.util.List;

public class SearchMovie extends AbstractController {

	private MovieDAO_yeo mdao = new MovieDAO_imple_yeo();
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 사용자 입력 받기
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            request.setAttribute("errorMsg", "검색어를 입력해주세요.");
            String errorPage = "/WEB-INF/views/error.jsp";
            request.getRequestDispatcher(errorPage).forward(request, response);
            return;
        }

        
        // 검색 기능 구현 
        List<MovieVO_yeo> movies = mdao.searchMovies(keyword);

        // 검색 결과를 request 영역에 저장
        request.setAttribute("movies", movies);

        // 검색 결과 페이지로 포워드
        String viewPage = "/WEB-INF/movie/searchMovie.jsp";
        request.getRequestDispatcher(viewPage).forward(request, response);
    }
}
