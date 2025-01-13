package admin.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.domain.MovieVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class MovieRegisterSearch extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)으로 로그인 했을 경우
		
			String method = request.getMethod();
			
			if("GET".equalsIgnoreCase(method)) {
				
				String movie_title = request.getParameter("movie_title");
				
				try {
					// **** 영화를 등록 페이지에서 [등록된 영화 조회]를 통해 검색한 영화들을 보여주는 페이지(select) **** //
					List<MovieVO> movieList = mvdao.selectMovieRegister(movie_title);
							
					// movieList 를 JOSN 배열로 변환
					JSONArray movieArray = new JSONArray();
					
					
					for (MovieVO movie : movieList) {
	
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("poster_file", movie.getPoster_file());
						jsonObj.put("movie_title", movie.getMovie_title());
						jsonObj.put("fk_category_code",movie.getCatevo().getCategory()); 
						jsonObj.put("movie_grade", movie.getMovie_grade()); 
						jsonObj.put("register_date", movie.getRegister_date()); 
						
						movieArray.put(jsonObj);
					}// end of for-------------------------------
					
					
					// ArrayList와 같은 복잡한 객체를 JSON으로 변환 후 보내는 경우 아래와 같이 전송
					//response.setContentType("application/json");
			        //response.getWriter().write(movieArray.toString());
			        
					// AJAX 로 여러 개(영화검색정보, ctxPath)의 데이터를 전송
					JSONObject jsonObj_ctxPath = new JSONObject();
					jsonObj_ctxPath.put("movie_List", movieArray);
					jsonObj_ctxPath.put("ctxPath", request.getContextPath());
					
					response.setContentType("application/json"); // json 형태로 보내준다.
					response.getWriter().write(jsonObj_ctxPath.toString());
				} catch (SQLException e) {
					e.printStackTrace();
					
					String message = "등록된 영화 조회 실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
					
				}
				
			}// end of if ("GET".equalsIgnoreCase(method)) {}---------------------------------------------
	
		}// end of if(loginuser != null && "admin".equalsIgnoreCase(loginuser.getUserid())) {}---------------------------------
		else {
			// 일반사용자로 로그인 한 경우
			request.setAttribute("message", "관리자만 접근이 가능합니다.");
			request.setAttribute("loc", request.getContextPath() + "/index.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}----------------
}
