package mypage.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;
import reservation.domain.PaymentVO;

public class MyreservationListCancel extends AbstractController {
	
	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		//예매취소내역 리스트
		List<PaymentVO> myreservationList_cancel = mydao.myreservationList_cancel(loginuser.getUserid());
		
		JSONArray jsonArr = new JSONArray();
	      
	      if (myreservationList_cancel.size() > 0) {
	          for (PaymentVO pvo : myreservationList_cancel) {
	              JSONObject jsonObj = new JSONObject(); // {}
	              jsonObj.put("movie_title", pvo.getSvo().getMvo().getMovie_title());
	              jsonObj.put("start_time" , pvo.getSvo().getStart_time());
	              jsonObj.put("pay_success_date" , pvo.getPay_success_date());
	              jsonObj.put("pay_cancel_date", pvo.getPay_cancel_date());
	              jsonObj.put("pay_amount", pvo.getPay_amount());
	              
	              jsonArr.put(jsonObj);
	          }
	      }
	      
	      String json = jsonArr.toString();
	      //System.out.println("~~~ 확인용 json" +json);
	      
	      //request.setAttribute("json", json);
	      response.setContentType("application/json");
	      response.getWriter().write(json);		

	}

}
