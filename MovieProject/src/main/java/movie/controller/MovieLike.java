package movie.controller;

import java.sql.SQLException;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO_imple_wonjae;
import movie.model.MovieDAO_wonjae;


public class MovieLike extends AbstractController {

	private MovieDAO_wonjae mdao = new MovieDAO_imple_wonjae();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_id = request.getParameter("user_id");
        int seq_movie_no = Integer.parseInt(request.getParameter("seq_movie_no"));
        String movieLike = request.getParameter("like"); // "add" or "remove"

        JSONObject jsonResponse = new JSONObject();
        try {
            boolean success = false;

            if ("add".equals(movieLike)) {
                // 좋아요 추가
                success = mdao.insertMovieLike(user_id, seq_movie_no);
            } else if ("remove".equals(movieLike)) {
                // 좋아요 취소
                success = mdao.removeMovieLike(user_id, seq_movie_no);
            }

            if (success) {
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "좋아요 상태가 변경되었습니다.");
            } else {
                jsonResponse.put("status", "fail");
                jsonResponse.put("message", "좋아요 처리에 실패했습니다.");
            }
        } catch (SQLException e) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "오류 발생: " + e.getMessage());
        }

        // JSON 응답 반환
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }

}
