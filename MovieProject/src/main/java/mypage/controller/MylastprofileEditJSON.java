package mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MylastprofileEditJSON extends AbstractController {

	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		String userid = loginuser.getUserid();
		
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);

		MemberVO mvo = mydao.MylastprofileEdit(paraMap);

		// JSON 객체 생성
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("profile", mvo.getProfile());

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObj.toString());

		//System.out.println("~~~ 확인용 json: " + jsonObj.toString());
	}
}
