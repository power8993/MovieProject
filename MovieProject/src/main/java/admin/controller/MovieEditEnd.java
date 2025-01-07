package admin.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.domain.CategoryVO;
import movie.domain.MovieVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class MovieEditEnd extends AbstractController {

	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)으로 로그인 했을 경우
			
			String method = request.getMethod();
			
			if ("POST".equalsIgnoreCase(method)) {
			
				// 폼태그의 값 받기	
				String seq = request.getParameter("seq");
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
				movie.setSeq_movie_no(Integer.parseInt(seq));
				
				CategoryVO catevo = new CategoryVO();
				catevo.setCategory_code(fk_category_code);;
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
					
					// **** 영화를 수정하는 메소드(seq에 해당하는 영화를 update) **** //
					int n = mvdao.updateMovie(movie);
					
					if(n == 1) {
						super.setRedirect(false); 
				        super.setViewPage("/WEB-INF/admin/movieRegisteredList.jsp");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					
					String message = "영화 수정 실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				
			}// end of if ("POST".equalsIgnoreCase(method)) {}---------------------------------------------
			
		}// end of if(loginuser != null && "admin".equalsIgnoreCase(loginuser.getUserid())) {}---------------------------------
		else {
			// 일반사용자로 로그인 한 경우
			request.setAttribute("message", "관리자만 접근이 가능합니다.");
			request.setAttribute("loc", request.getContextPath() + "/index.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}-----------------
}
