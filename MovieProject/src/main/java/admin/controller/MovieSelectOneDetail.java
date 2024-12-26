package admin.controller;

import java.sql.SQLException;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import movie.domain.MovieVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class MovieSelectOneDetail extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			
			String seq = request.getParameter("seq");	// 선택한 영화의 seq_movie_no 를 가져온다.
		
			try {
				// 등록된 영화를 보여주는 페이지에서 영화 클릭 시, 해당 영화 상세 내용 보여주기(select)
				MovieVO mvvo = mvdao.selectMovieDetail(seq);
				if(mvvo != null) {
					JSONObject jsonObj = new JSONObject();

					// SONObject의 mvvo 속성으로 포함되어 전달
					jsonObj.put("seq_movie_no", mvvo.getSeq_movie_no());
					jsonObj.put("fk_category_code", mvvo.getCatevo().getCategory());
					jsonObj.put("movie_title", mvvo.getMovie_title());
					jsonObj.put("content", mvvo.getContent());
					jsonObj.put("director", mvvo.getDirector());
					jsonObj.put("actor", mvvo.getActor());
					jsonObj.put("movie_grade", mvvo.getMovie_grade());
					jsonObj.put("running_time", mvvo.getRunning_time()); 
					jsonObj.put("start_date", mvvo.getStart_date());
					jsonObj.put("end_date", mvvo.getEnd_date());
					jsonObj.put("poster_file", mvvo.getPoster_file());
					jsonObj.put("video_url", mvvo.getVideo_url());
					jsonObj.put("register_date", mvvo.getRegister_date());
					jsonObj.put("ctxPath", request.getContextPath());
					
					response.setContentType("application/json"); // json 형태로 보내준다.
					response.getWriter().write(jsonObj.toString());
					
//					HttpSession session = request.getSession();
//					session.setAttribute("seq_movie_no", mvvo.getSeq_movie_no());
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				String message = "영화 상세페이지 보기 실패";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				
			}
			
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}-----------------

}
