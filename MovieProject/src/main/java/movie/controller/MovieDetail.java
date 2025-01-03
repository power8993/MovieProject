package movie.controller;

import org.json.JSONObject;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.domain.MovieVO_wonjae;
import movie.model.*;

public class MovieDetail extends AbstractController {

    private MovieDAO_wonjae mdao = new MovieDAO_imple_wonjae();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String method = request.getMethod();
    	
        HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

        String seq_movie_no = request.getParameter("seq_movie_no"); 
        
        String json = "";
        
        if("POST".equalsIgnoreCase(method)) {       	
	        // 로그인 체크
	        if (loginuser != null) {           
	            boolean isLiked = mdao.checkMovieLike(loginuser, Integer.parseInt(seq_movie_no));

	            JSONObject jsonObj = new JSONObject();
				jsonObj.put("isLiked", isLiked);
	
				json = jsonObj.toString();
	        }
	        else {
	            // 로그인하지 않은 경우에는 기본값을 반환 (isLiked = false)
	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("isLiked", false);  // 로그인하지 않았으면 기본값 false로 설정

	            json = jsonObj.toString();
	        }
  
	        request.setAttribute("json", json);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/jsonview.jsp");
        }
        else {       	
        	// DB에서 해당 영화 정보를 가져옴
	        MovieVO_wonjae mvo = mdao.movieDetail(Integer.parseInt(seq_movie_no));       	        
	        
        	if(mvo == null) {
        		String message = "잘못된 접근입니다.";
            	String loc = "javascript:history.back()";
            	
            	request.setAttribute("message", message);
            	request.setAttribute("loc", loc);
            	
            	super.setRedirect(false);
    	        super.setViewPage("/WEB-INF/msg.jsp");
        	}
        	else {
        		// 영화 정보와 로그인된 사용자 정보를 뷰 페이지에 전달
    	        request.setAttribute("mvo", mvo);
    	        request.setAttribute("loginuser", loginuser);
        		
        		// 뷰 페이지로 이동
    	        super.setRedirect(false);
    	        super.setViewPage("/WEB-INF/movie/movieDetail.jsp");
        	}
        }
    }
}
