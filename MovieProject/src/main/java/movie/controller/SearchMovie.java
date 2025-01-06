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
        String method = request.getMethod(); // GET 또는 POST 요청 확인

        if ("GET".equalsIgnoreCase(method)) {
            // GET 요청 처리: 검색 기능 수행
            String keyword = request.getParameter("keyword");

            if (keyword == null || keyword.trim().isEmpty()) {
                // 검색어가 없을 경우 에러 메시지 출력
                String message = "검색어를 입력해주세요.";
                String loc = "javascript:history.back()";

                request.setAttribute("message", message);
                request.setAttribute("loc", loc);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
                return;
            }

            // 검색어가 있을 경우 데이터베이스에서 영화 검색
            List<MovieVO_yeo> movies = mdao.searchMovies(keyword);

            if (movies == null || movies.isEmpty()) {
                // 검색 결과가 없을 경우 메시지 출력
                String message = "검색 결과가 없습니다.";
                String loc = "javascript:history.back()";

                request.setAttribute("message", message);
                request.setAttribute("loc", loc);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
                return;
            }

            // 검색 결과를 JSP에 전달
            request.setAttribute("movies", movies);
            request.setAttribute("keyword", keyword);

            // 검색 결과 페이지로 이동
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/movie/searchMovie.jsp");

        } else if ("POST".equalsIgnoreCase(method)) {
            // POST 요청 처리: 잘못된 접근 처리
            String message = "잘못된 접근입니다.";
            String loc = "javascript:history.back()";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
        }
    }
}
