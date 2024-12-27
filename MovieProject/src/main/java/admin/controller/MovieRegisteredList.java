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

public class MovieRegisteredList extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();

		if("GET".equalsIgnoreCase(method)) {
			
			String search_movie_title = request.getParameter("search_movie_title");
			String search_category_code = request.getParameter("search_category_code");
			String size_per_page = request.getParameter("size_per_page");
			String current_showpage_no = request.getParameter("current_showpage_no");

			if(search_movie_title == null) {
				search_movie_title = "";
			}
			
			if(size_per_page == null ||
			  (!"5".equals(size_per_page)) && (!"10".equals(size_per_page)) && (!"15".equals(size_per_page))) {
				size_per_page = "5";
			}
			
			if(current_showpage_no == null) {
				current_showpage_no = "1";
			}
			
			if(search_category_code == null ||
			  (!"1".equals(search_category_code)) && (!"2".equals(search_category_code)) && (!"3".equals(search_category_code)) && (!"4".equals(search_category_code)) && 
			  (!"5".equals(search_category_code)) && (!"6".equals(search_category_code)) && (!"7".equals(search_category_code)) && (!"8".equals(search_category_code)) && 
			  (!"9".equals(search_category_code)) && (!"10".equals(search_category_code)) && (!"11".equals(search_category_code)) && (!"12".equals(search_category_code)) ) {
				search_category_code = "";
			}
			

			// 처음 보여주는 페이지, 모든 등록된 영화가 보인다.
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("search_movie_title", search_movie_title);
			paraMap.put("search_category_code", search_category_code);
			paraMap.put("size_per_page", size_per_page); 			  // 한 페이지당 보여줄 행의 개수
			paraMap.put("current_showpage_no", current_showpage_no);  // 현재 내가 보고자하는 페이지
			
			// **** 페이징 처리를 한 모든 등록된 영화 리스트 보여주기 시작 **** //
			// === 페이징 처리를 위한 검색유무에 따른 영화 결과값에 대한 총 페이지 수 알아오기 === //
			int total_page = mvdao.getTotalPage(paraMap);
			
			// === 주어진 페이지 외의 페이지로 이동할 경우를 방지한다 === //
			try {
				
				if(Integer.parseInt(current_showpage_no) > total_page ||
				   Integer.parseInt(current_showpage_no) <= 0 ) {
					current_showpage_no = "1";
					paraMap.put("current_showpage_no", current_showpage_no);
				}
				
			} catch (NumberFormatException e) {
				current_showpage_no = "1";
				paraMap.put("current_showpage_no", current_showpage_no);
			}
			
			// **** 페이지 바 만들기 시작 **** //
			String page_bar = "";
			int block_size = 10;   // 보여지는 페이지 번호의 개수
			
			int loop = 1;
			
			// === [page_no] 구하는 공식 --> 페이지바에서 보여지는 첫번째 번호 구하기 === //
			int page_no = ( (Integer.parseInt(current_showpage_no) - 1)/block_size ) * block_size + 1;
			
			// *** [맨처음][이전] 만들기 *** //
			page_bar += "<li class='page-item'><a class='page-link' href='movieRegisteredList.mp?search_category_code="+search_category_code+"&search_movie_title="+search_movie_title+"&size_per_page="+size_per_page+"&current_showpage_no=1'>[맨처음]</a></li>";
	           
			if(page_no != 1) {
        	   page_bar += "<li class='page-item'><a class='page-link' href='movieRegisteredList.mp?search_category_code="+search_category_code+"&search_movie_title="+search_movie_title+"&size_per_page="+size_per_page+"&current_showpage_no="+(page_no-1)+"'>[이전]</a></li>";
           }
	           
           while( !(loop > block_size || page_no > total_page)) {
        	   if(page_no == Integer.parseInt(current_showpage_no)) {
        		   page_bar += "<li class='page-item active'><a class='page-link' href='#'>"+page_no+"</a></li>";
        	   }
        	   else {
        		   page_bar += "<li class='page-item'><a class='page-link' href='movieRegisteredList.mp?search_category_code="+search_category_code+"&search_movie_title="+search_movie_title+"&size_per_page="+size_per_page+"&current_showpage_no="+page_no+"'>"+page_no+"</a></li>";
        	   }
              loop++;  
              
              page_no++;
           }// end of while( !(loop > blockSize || pageNo > totalPage))---------------------------------------
           
	           
           // *** [다음][마지막] 만들기 *** //
           if(page_no <= total_page) {   
        	   page_bar += "<li class='page-item'><a class='page-link' href='movieRegisteredList.mp?search_category_code="+search_category_code+"&search_movie_title="+search_movie_title+"&size_per_page="+size_per_page+"&current_showpage_no="+page_no+"'>[다음]</a></li>";
           }
	           
           page_bar += "<li class='page-item'><a class='page-link' href='movieRegisteredList.mp?search_category_code="+search_category_code+"&search_movie_title="+search_movie_title+"&size_per_page="+size_per_page+"&current_showpage_no="+total_page+"'>[마지막]</a></li>";
           // **** 페이지 바 만들기 끝  **** //
	           

	           try {
	        	   // **** 페이징 처리를 한 모든 등록된 영화 리스트 보여주기 시작 **** //
	        	   List<MovieVO> movieList = mvdao.selectMovieListPaging(paraMap);
		           request.setAttribute("movieList", movieList);
		           
		           request.setAttribute("search_category_code", search_category_code);
		           request.setAttribute("search_movie_title", search_movie_title);
		           
		           request.setAttribute("size_per_page", size_per_page);   // 변경된 회원명수의 값을 다시 넘겨서 view 단에서 보이게끔한다.
		           
		           request.setAttribute("page_bar", page_bar);      
	            
		           /* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
	               	  검색이 있는 또는 검색이 없는 영화의 총개수 알아오기 시작 <<< */
		           int total_movie_count = mvdao.getTotalMovieCount(paraMap);
		          
		           request.setAttribute("total_member_count", total_movie_count);
		           request.setAttribute("current_showpage_no", current_showpage_no);
	            
		           /* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
	               	  검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 끝  <<< */
	            
		           super.setRedirect(false);
		           super.setViewPage("/WEB-INF/admin/movieRegisteredList.jsp");
	            
	           } catch (SQLException e) {
	        	   e.printStackTrace();
	            
	        	   super.setRedirect(true);
	        	   super.setViewPage(request.getContextPath() + "/error.up");
	           }
			
			
			
			
			
			/*
			
			try {
				// **** 페이징 처리를 안한 모든 등록된 영화 리스트 보여주기 **** //
				List<MovieVO> movieList = mvdao.selectMovieList(paraMap);
				
				request.setAttribute("movieList", movieList);
				super.setViewPage("/WEB-INF/admin/movieRegisteredList.jsp");
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

			/*
			else {
				// 폼태그를 제출 후 검색어에 대한 영화를 조회
	
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("movie_title", movie_title);
				paraMap.put("fk_category_code", fk_category_code);
				paraMap.put("size_per_page", size_per_page); 			  // 한 페이지당 보여줄 행의 개수
				paraMap.put("current_showpage_no", current_showpage_no);  // 현재 내가 보고자하는 페이지
				
				
				try {
					// **** 페이징 처리를 안한 영화제목 검색 결과 보여주기 **** //
					List<MovieVO> movieList = mvdao.selectSearchMovieList(paraMap);
					
					request.setAttribute("movieList", movieList);
					super.setViewPage("/WEB-INF/admin/movieRegisteredList.jsp");
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
			*/
			
		}
		else {
			
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}----------------

}
