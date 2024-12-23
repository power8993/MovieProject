package movie.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO;
import movie.domain.MovieVO_yeo;
import movie.model.MoveDAO_imple_yeo;
import movie.model.MovieDAO_yeo;

public class MovieTime extends AbstractController {

	private MovieDAO_yeo mdao = new MoveDAO_imple_yeo();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<MovieVO_yeo> movieTime = mdao.selectMovieTiem();
            request.setAttribute("movieTime", movieTime);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/movie/movieTime.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
}

