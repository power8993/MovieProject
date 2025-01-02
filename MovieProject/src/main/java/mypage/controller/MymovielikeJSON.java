package mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieLikeVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MymovielikeJSON extends AbstractController {
	
	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userid = request.getParameter("userid"); 
		String start = request.getParameter("start");
		String len = request.getParameter("len");
		
		Map<String, String> paraMap = new HashMap<>();
	      paraMap.put("userid", userid);  
	      paraMap.put("start", start); 
	      
	      String end = String.valueOf(Integer.parseInt(start) + Integer.parseInt(len) - 1);								
	      paraMap.put("end", end);		
	      
	      List<MovieLikeVO> mymovielikeList = mydao.selectBymymovielike(paraMap);
	      
	      JSONArray jsonArr = new JSONArray();
	      
	      if(mymovielikeList.size() > 0 ) {
	    	  
	    	  for(MovieLikeVO mlvo :mymovielikeList) {
	    		JSONObject jsonObj = new JSONObject(); // {}
	    		jsonObj.put("FK_USER_ID",mlvo.getFK_USER_ID());
	    		jsonObj.put("FK_SEQ_MOVIE_NO", mlvo.getFK_SEQ_MOVIE_NO());
	    		jsonObj.put("poster_file", mlvo.getMvo().getPoster_file());
	    		jsonObj.put("movie_title", mlvo.getMvo().getMovie_title());
	    		jsonObj.put("start_date", mlvo.getMvo().getStart_date());

	            jsonArr.put(jsonObj); 
	    
	    	  }//end of for ---
	    	  
	      }// end of if ---
	      
	      String json = jsonArr.toString();
	      //System.out.println("~~~ 확인용 json" +json);
	      
	      request.setAttribute("json", json);
	      
	      super.setRedirect(false);
	      super.setViewPage("/WEB-INF/jsonview.jsp");

	}

}
