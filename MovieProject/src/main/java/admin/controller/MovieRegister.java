package admin.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.CategoryVO;
import movie.domain.MovieVO;
import movie.model.*;

public class MovieRegister extends AbstractController {

	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();

		if("GET".equalsIgnoreCase(method)) { // [영화등록] 버튼 클릭 후 폼태그 보이기
			
			super.setViewPage("/WEB-INF/admin/movieRegister.jsp");
			
		}
		else {	// 폼태그 작성 후 [영화등록하기] 버튼 클릭
			
			// 폼태그의 값 받기	
			String fk_category_code = request.getParameter("fk_category_code");
			String movie_title = request.getParameter("movie_title");     
			String content = request.getParameter("content");         
			String director = request.getParameter("director");        
			String actor = request.getParameter("actor");           
			String movie_grade = request.getParameter("movie_grade");     
			String running_time = request.getParameter("running_time");         
			String start_date = request.getParameter("start_date");      
			String end_date = request.getParameter("end_date");        
			String poster_file = request.getParameter("poster_file");     
			String video_url = request.getParameter("video_url");       
			
			// 위의 값을 MovieVO 객체에 담는다.
			MovieVO movie = new MovieVO();
			
			CategoryVO catevo = new CategoryVO();
			catevo.setCategory_code(fk_category_code);
			movie.setCatevo(catevo);
			
			movie.setMovie_title(movie_title);
			movie.setContent(content);
			movie.setDirector(director);
			movie.setActor(actor);
			movie.setMovie_grade(movie_grade);
			movie.setRunning_time(running_time);
			movie.setStart_date(start_date);
			movie.setEnd_date(end_date);
			movie.setPoster_file(poster_file);
			movie.setVideo_url(video_url);
			
			try {
				
				// **** 영화를 등록해주는 메소드(tbl_movie 테이블에 insert) **** //
				int n = mvdao.registerMovie(movie);
				
				if(n == 1) {
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				String message = "영화 등록 실패";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
		}

	}

}
