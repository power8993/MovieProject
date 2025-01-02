package mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MylastpwdchangedateJSON extends AbstractController {

	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");
		
		Map<String, String> paraMap = new HashMap<>();
	      paraMap.put("userid", userid); 
	      
	      List<MemberVO> MylastpwdchangedateList = mydao.Mylastpwdchangedate(paraMap);
	      
	      JSONArray jsonArr = new JSONArray();
	      
	      if(MylastpwdchangedateList.size() > 0 ) {
	    	  for(MemberVO mvo :MylastpwdchangedateList) {
		    		JSONObject jsonObj = new JSONObject();
		    		jsonObj.put("lastpwdchangedate", mvo.getLastpwdchangedate());
	      }
	      
	      String json = jsonArr.toString(); //문자열로 변환
	      System.out.println("~~~ 확인용 json" +json);
	      
	      request.setAttribute("json", json);
	      
	      super.setRedirect(false);
	      super.setViewPage("/WEB-INF/jsonview.jsp");
	      }

	}
	}
