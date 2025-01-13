package mypage.controller;

import java.util.HashMap;
import java.util.Map;

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

	    // 비밀번호 변경 날짜를 가져오기
	    MemberVO mvo = mydao.Mylastpwdchangedate(paraMap);

	    // JSON 객체 생성
	    JSONObject jsonObj = new JSONObject();
	    jsonObj.put("lastpwdchangedate", mvo.getLastpwdchangedate());
	   

	    response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObj.toString());

        //System.out.println("~~~ 확인용 json: " + jsonObj.toString());
	}


	}
