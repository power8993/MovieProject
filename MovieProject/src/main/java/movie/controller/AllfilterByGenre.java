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
        String method = request.getMethod(); // 요청 메서드 확인 (GET 또는 POST)

        if ("POST".equalsIgnoreCase(method)) {
            // POST 요청 처리: 잘못된 접근 처리
            handleInvalidAccess(request, response);
        } else if ("GET".equalsIgnoreCase(method)) {
            // GET 요청 처리: 장르 필터링 및 영화 목록 가져오기
            handleGenreFiltering(request, response);
        } else {
            // 잘못된 요청 처리
            handleInvalidAccess(request, response);
        }
    }

    private void handleInvalidAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = "잘못된 접근입니다.";
        String loc = "javascript:history.back()";

        request.setAttribute("message", message);
        request.setAttribute("loc", loc);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/msg.jsp");
    }

    private void handleGenreFiltering(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String genreCode = request.getParameter("genreCode"); // 클라이언트로부터 전달받은 장르 코드

        try {
            List<CategoryVO_yeo> cgList = mdao.selectcategory(); // 장르 목록 가져오기
            request.setAttribute("cgList", cgList);

            // 유효성 검사: genreCode가 전달되었으나 데이터베이스에 없는 경우 잘못된 접근 처리
            if (genreCode != null && !genreCode.trim().isEmpty()) {
                boolean isValidGenre = cgList.stream()
                                             .anyMatch(category -> category.getCategory_code().equals(genreCode));
                if (!isValidGenre) {
                    handleInvalidAccess(request, response);
                    return;
                }
            }

            // 영화 목록 가져오기
            List<MovieVO_yeo> movies;
            if (genreCode != null && !genreCode.trim().isEmpty()) {
                movies = mdao.allgetMoviesByGenre(genreCode); // 특정 장르의 영화
            } else {
                movies = mdao.select_Movies(); // 전체 영화
            }

            // 데이터 JSP에 전달
            request.setAttribute("movies", movies);
            request.setAttribute("selectedGenre", genreCode);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/movie/movieList.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("영화 데이터를 가져오는 중 오류가 발생했습니다.", e);
        }
    }
}
