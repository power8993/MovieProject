package common.controller;

import java.sql.SQLException;
import java.util.List;

import common.model.indexDAO;
import common.model.indexDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.CategoryVO_yeo;
import movie.domain.MovieVO_sangwoo;
import movie.domain.MovieVO_yeo;
import movie.model.MovieDAO_imple_yeo;
import movie.model.MovieDAO_yeo;
//import myshop.domain.ImageVO;
//import myshop.model.ProductDAO;
//import myshop.model.ProductDAO_imple;
import notice.domain.NoticeDTO;

public class IndexController extends AbstractController {
	
	private indexDAO idao = new indexDAO_imple();
//	
//	public IndexController() {
//		pdao = new ProductDAO_imple();
//	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	try {
			
			List<NoticeDTO> noticeList = idao.NoticeSelectTopThree(); // 공지사항 최신글 3개 조회하기
			request.setAttribute("noticeList", noticeList);
			
			

			/////////////////////////////////////////////
			// 무비차트 (예매율순)
        	List<MovieVO_sangwoo> movies = idao.showMovieChart(); //첫 번째 슬라이드 카드
        	List<MovieVO_sangwoo> movies2 = idao.showMovieChart2();//두 번째 슬라이드 카드
        	request.setAttribute("movies", movies);
        	request.setAttribute("movies2", movies2);
        	
        	
        	//////////////////////////////////////////////
        	//상영예정작(현재날짜에 가까운 영화개봉일순)
        	List<MovieVO_sangwoo> laterMovies = idao.showLaterMovies(); //첫 번째 슬라이드 카드
        	List<MovieVO_sangwoo> laterMovies2 = idao.showLaterMovies2(); //두 번째 슬라이드 카드
        	request.setAttribute("laterMovies", laterMovies);
        	request.setAttribute("laterMovies2", laterMovies2);
        	
        	
        	
        	
        	/////////////////////////////////////////////////
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/index.jsp");
			

            
		} catch (SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/error.mp");
		}
	}

}
