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

	    // 결과가 있을 경우 처리
	    if (mvo != null) {
	        jsonObj.put("pwdChangeDate", mvo.getLastpwdchangedate());
	    } else {
	        jsonObj.put("pwdChangeDate", "정보 없음");
	    }

	    // JSON 응답으로 반환
	    String json = jsonObj.toString();
	    System.out.println("~~~ 확인용 json: " + json);
	    
	    request.setAttribute("json", json);
	    super.setRedirect(false);
	    super.setViewPage("/WEB-INF/jsonview.jsp");
	}


	}
