package movie.controller;

import java.util.List;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO_yeo;
import movie.model.MovieDAO_yeo;
import movie.model.MovieDAO_imple_yeo;

public class SearchDate extends AbstractController {

    private MovieDAO_yeo mdao = new MovieDAO_imple_yeo();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 선택된 날짜 가져오기
        String selectedDate = request.getParameter("selectedDate");

        if (selectedDate == null || selectedDate.isEmpty()) {
            selectedDate = java.time.LocalDate.now().toString(); // 기본값: 오늘 날짜
        }

        try {
            // 영화 시간표 가져오기
            List<MovieVO_yeo> movieTimeList = mdao.selectMovieTimeByDate(selectedDate); // 전체 
            List<MovieVO_yeo> movieTimeList_o = mdao.selectMovieTimeByDateNO1(selectedDate); // 1관
            List<MovieVO_yeo> movieTimeList_t = mdao.selectMovieTimeByDateNO2(selectedDate); // 2관

            // JSP에 데이터 전달
            request.setAttribute("movieTimeList", movieTimeList);
            request.setAttribute("movieTimeList_o", movieTimeList_o);
            request.setAttribute("movieTimeList_t", movieTimeList_t);
            request.setAttribute("selectedDate", selectedDate);

            // View 설정
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/movie/movieTime.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("영화 시간표 데이터를 가져오는 중 오류 발생", e);
        }
    }
}
