package admin.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class MovieRegisterSearch extends AbstractController {
	
	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
String method = request.getMethod();
        
        if("GET".equalsIgnoreCase(method)) {
            
            String movie_title = request.getParameter("movie_title");  // 영화 제목 파라미터 가져오기
            
            try {
                // 영화를 등록 페이지에서 [등록된 영화 조회]를 통해 검색한 영화들을 보여주는 메서드
                List<MovieVO> movieList = mvdao.selectMovieRegister(movie_title);
                
                // 영화 리스트를 JSON 배열로 변환
                JSONArray movieArray = new JSONArray();
                
                for (MovieVO movie : movieList) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("poster_file", movie.getPoster_file());  // 포스터 파일 경로
                    jsonObj.put("movie_title", movie.getMovie_title());  // 영화 제목
                    jsonObj.put("fk_category_code", movie.getFk_category_code());  // 영화 카테고리 코드
                    jsonObj.put("movie_grade", movie.getMovie_grade());  // 영화 등급
                    jsonObj.put("register_date", movie.getRegister_date());  // 등록일
                    
                    movieArray.put(jsonObj);  // JSON 배열에 추가
                }

                // 요청에 JSON 데이터만 담아서 전달
                request.setAttribute("movie_List", movieArray);  // 영화 목록
                request.setAttribute("ctxPath", request.getContextPath());  // 컨텍스트 경로

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/movie/movieRegisterSearch.jsp");  // JSP로 포워딩

            } catch (SQLException e) {
                e.printStackTrace();
                
                String message = "등록된 영화 조회 실패";
                String loc = "javascript:history.back()";
                
                request.setAttribute("message", message);
                request.setAttribute("loc", loc);
                
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/error.jsp");  // 오류 페이지로 리디렉션
            }
        }

	}

}
