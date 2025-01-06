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
			
			String search_date = request.getParameter("search_date");
			String search_time = request.getParameter("search_time");
			String search_movie_title = request.getParameter("search_movie_title");
			String search_orderby = request.getParameter("search_orderby");
			String invalid_showtime = request.getParameter("invalid_showtime");
			String size_per_page = request.getParameter("size_per_page");
			String current_showpage_no = request.getParameter("current_showpage_no"); // 현재 내가 보고자하는 페이지
			
			// ===== 입력하지 않았을 경우에 대한 NULL 처리 ===== //
			if(search_date == null) {
				search_date = "";
			}
			
			if(search_time == null ||
			   (!"모닝(06:00~10:00)".equals(search_time)) && (!"브런치(10:01~13:00)".equals(search_time)) && (!"일반(13:01~23:59)".equals(search_time)) && (!"심야(00:00~05:59)".equals(search_time))) {
				search_time = "";
			}
			
			if(search_movie_title == null) {
				search_movie_title = "";
			}
			
			if(search_orderby == null ||
			  (!"asc".equals(search_orderby)) && (!"desc".equals(search_orderby))) {
				search_orderby = "asc";
			}
			
			if(invalid_showtime == null ||
			  (!"상영예정작".equals(invalid_showtime)) && (!"상영종료작".equals(invalid_showtime))) {
				invalid_showtime = "상영예정작";
			}
			
			if(size_per_page == null||
			  (!"10".equals(size_per_page)) && (!"15".equals(size_per_page)) && (!"20".equals(size_per_page))) {
				size_per_page = "10";
			}
			
			if(current_showpage_no == null) {
				current_showpage_no = "1";
			}
			
			// ===== 선택한 시간대에 따른 switch~case 처리 ===== //	
			String search_time_1 = "";
			String search_time_2 = "";

			switch (search_time) {
				case "모닝(06:00~10:00)":
					search_time_1 = "06:00";
					search_time_2 = "10:00";
					break;
				case "브런치(10:01~13:00)":
					search_time_1 = "10:01";
					search_time_2 = "13:00";
					break;
				case "일반(13:01~23:59)":
					search_time_1 = "13:01";
					search_time_2 = "23:59";
					break;
				case "심야(00:00~05:59)":
					search_time_1 = "00:00";
					search_time_2 = "05:59";
					break;
			}// end of switch (search_time) {}-------------------------
			

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("search_date", search_date);
			paraMap.put("search_time_1", search_time_1);
			paraMap.put("search_time_2", search_time_2);
			paraMap.put("search_movie_title", search_movie_title);
			paraMap.put("search_orderby", search_orderby);
			paraMap.put("invalid_showtime", invalid_showtime);
			paraMap.put("size_per_page", size_per_page); 			  // 한 페이지당 보여줄 행의 개수
			paraMap.put("current_showpage_no", current_showpage_no);  // 현재 내가 보고자하는 페이지
			
			
			// **** 페이징 처리를 한 모든 상영일정 목록 또는 검색되어진 상영일정 목록 보여주기 **** //
			
			// 페이징 처리를 위한 검색이 있는 또는 검색이 없는 상영일정 목록에 대한 총페이지수 알아오기 //
			int total_page = mvdao.getTotalShowtimePage(paraMap);
			//System.out.println("~~~확인용 total_page : " + total_page);
			
			// 총 페이지수가 아닌 페이지를 GET 방식으로 접근하려고 할 때의 처리
			try {
				
				if(Integer.parseInt(current_showpage_no) > total_page ||
				   Integer.parseInt(current_showpage_no) <= 0) {
					current_showpage_no = "1";
					paraMap.put("current_showpage_no", current_showpage_no);
				}
				
			} catch (NumberFormatException e) {
				current_showpage_no = "1";
				paraMap.put("current_showpage_no", current_showpage_no);
			}
			
			// 페이지바 만들기
			String page_bar = "";
			int block_size = 10;   // 한 번에 보여줄 수 있는 페이지바의 길이
			
			int loop = 1;
			
			// page_no 구하는 공식
			int page_no = ( (Integer.parseInt(current_showpage_no) - 1)/block_size ) * block_size + 1; 
			
			// [맨처음][이전] 만들기
			page_bar += "<li class='page-item'><a class='page-link' href='showtimeList.mp?search_date="+search_date+"&search_time="+search_time+"&search_movie_title="+search_movie_title+"&invalid_showtime="+invalid_showtime+"&size_per_page="+size_per_page+"&current_showpage_no=1'>[맨처음]</a></li>";
			
			if(page_no != 1) {
				page_bar += "<li class='page-item'><a class='page-link' href='showtimeList.mp?search_date="+search_date+"&search_time="+search_time+"&search_movie_title="+search_movie_title+"&invalid_showtime="+invalid_showtime+"&size_per_page="+size_per_page+"&current_showpage_no="+(page_no-1)+"'>[맨처음]</a></li>";
	        }
			
			while( !(loop > block_size || page_no > total_page)) {
		       	if(page_no == Integer.parseInt(current_showpage_no)) {
		       		page_bar += "<li class='page-item active'><a class='page-link' href='#'>"+page_no+"</a></li>";
		       	}
		       	else {
		       		page_bar += "<li class='page-item'><a class='page-link' href='showtimeList.mp?search_date="+search_date+"&search_time="+search_time+"&search_movie_title="+search_movie_title+"&invalid_showtime="+invalid_showtime+"&size_per_page="+size_per_page+"&current_showpage_no="+page_no+"&search_orderby="+search_orderby+"'>"+page_no+"</a></li>";
		       	}
		       	loop++;
		       	
		       	page_no++; 
			}// end of while( !(loop > blockSize || pageNo > totalPage))---------------------------------------
	       
			// [다음][마지막] 만들기
			if(page_no <= total_page) {	
				page_bar += "<li class='page-item'><a class='page-link' href='showtimeList.mp?search_date="+search_date+"&search_time="+search_time+"&search_movie_title="+search_movie_title+"&invalid_showtime="+invalid_showtime+"&size_per_page="+size_per_page+"&current_showpage_no="+page_no+"'>[다음]</a></li>";
	        }
	        
			page_bar += "<li class='page-item'><a class='page-link' href='showtimeList.mp?search_date="+search_date+"&search_time="+search_time+"&search_movie_title="+search_movie_title+"&invalid_showtime="+invalid_showtime+"&size_per_page="+size_per_page+"&current_showpage_no="+total_page+"'>[마지막]</a></li>";
			
			
			try {
				// 페이징 처리를 한 모든 상영일정 리스트 보여주기 (select)
				List<MovieVO> showtimeList = mvdao.selectShowtimeListPaging(paraMap);
				
				request.setAttribute("showtimeList", showtimeList);
				
				request.setAttribute("search_date", search_date);
				request.setAttribute("search_time", search_time);
				request.setAttribute("search_movie_title", search_movie_title);
				request.setAttribute("search_orderby", search_orderby);
				request.setAttribute("invalid_showtime", invalid_showtime);
				
				request.setAttribute("size_per_page", size_per_page);
				request.setAttribute("page_bar", page_bar);
				
				/* >>> 뷰단(movieRegisteredList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 영화 상영 일정의 총개수 알아오기 시작  <<< */
				int total_showtime_count = mvdao.getTotalShowtimeCount(paraMap);
				// System.out.println("확인용 total_showtime_count : " + total_showtime_count);
				
				request.setAttribute("total_showtime_count", total_showtime_count);
				request.setAttribute("current_showpage_no", current_showpage_no);
				
				/* >>> 뷰단(movieRegisteredList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 영화 상영 일정의 총개수 알아오기 끝  <<< */
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin/showtimeList.jsp");
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/error.mp");
			}
			
/*
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
*/
		}

	}

}
