package admin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class ShowtimeList extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();

		if("GET".equalsIgnoreCase(method)) {
			
			String movie_title = request.getParameter("movie_title");
			String sizePerPage = request.getParameter("sizePerPage");
			String currentShowPageNo = request.getParameter("currentShowPageNo");
			String fk_category_code = request.getParameter("search_type");

			if(sizePerPage == null) {
				sizePerPage = "10";
			}
			
			if(currentShowPageNo == null) {
				currentShowPageNo = "1";
			}
			
			if(movie_title == null) { 
				// 처음 보여주는 페이지, 모든 등록된 영화가 보인다.
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("fk_category_code", fk_category_code);
				paraMap.put("sizePerPage", sizePerPage); 			  // 한 페이지당 보여줄 행의 개수
				paraMap.put("currentShowPageNo", currentShowPageNo);  // 현재 내가 보고자하는 페이지

				try {
					// **** 페이징 처리를 안한 상영일정이 등록된 모든 영화 리스트 보여주기 (select) **** //
					List<MovieVO> movieList = mvdao.selectShowtimeList(paraMap);
					
					request.setAttribute("movieList", movieList);
					super.setViewPage("/WEB-INF/admin/showtimeList.jsp");
					
				} catch (SQLException e) {
					e.printStackTrace();
					
					String message = "영화 조회 실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				
			}
			else {
				// 폼태그를 제출 후 검색어에 대한 영화를 조회
	
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("movie_title", movie_title);
				paraMap.put("fk_category_code", fk_category_code);
				paraMap.put("sizePerPage", sizePerPage); 			  // 한 페이지당 보여줄 행의 개수
				paraMap.put("currentShowPageNo", currentShowPageNo);  // 현재 내가 보고자하는 페이지
				
				
				try {
					// **** 페이징 처리를 안한 상영일정이 등록된 모든 영화 리스트 중 특정 영화제목 검색 결과 보여주기 **** //
					List<MovieVO> movieList = mvdao.selectSearchMovieList(paraMap);
					
					request.setAttribute("movieList", movieList);
					super.setViewPage("/WEB-INF/admin/showtimeList.jsp");
				} catch (SQLException e) {
					e.printStackTrace();
					
					String message = "영화 조회 실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
			}
			
		}
		else {
			
		}

	}

}
