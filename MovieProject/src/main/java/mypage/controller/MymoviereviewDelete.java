package mypage.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MymoviereviewDelete extends AbstractController {
	
	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 String method = request.getMethod();
	      
	      if (!"POST".equalsIgnoreCase(method)) {
	         // GET 방식이라면

	         String message = "비정상적인 경로로 들어왔습니다";
	         String loc = "javascript:history.back()";

	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);

	         super.setViewPage("/WEB-INF/msg.jsp");
	         return;
	      }
	      else if ("POST".equalsIgnoreCase(method) && super.checkLogin(request)) {
	         // POST 방식이고 로그인을 했다라면
	         
	         String seq_review_no = request.getParameter("seq_review_no");
	         
	         int n = mydao.mymoviereviewDelete(seq_review_no);
	         
	         JSONObject jsobj = new JSONObject();  // {}
	         jsobj.put("n", n);   // {"n":1}
	         
	         String json = jsobj.toString();   // "{"n":1}"
	         
	         request.setAttribute("json", json);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/jsonview.jsp");
	      }      


	}

}
