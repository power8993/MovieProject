package member.controller;

import java.io.PrintWriter;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class PhDuplicateCheck extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String phoneNumber = request.getParameter("phoneNumber");
		System.out.println(phoneNumber);
		//인증하고자 하는 전화번호가 존재하는지 확인. DB에 존재하면 false 존재하지않으면 true
		boolean isExists = mdao.PhoneDuplicateCheck(phoneNumber);
		/*
		 * if (isExists) { // 메세지를 보내주는 mp로 이동 해서 난수 값을 세션에 저장. 사용자가 입력한 인증번호와 세션에 저장된 인증번호로 비교}
		 */
		
		// JSON 객체 생성
        JSONObject jsonObj = new JSONObject();
        
        jsonObj.put("isExists", isExists);
        
        // 응답 데이터 설정
        response.setContentType("application/json; charset=UTF-8");//응답데이터가 json 형식임을 알리는 용도
        PrintWriter out = response.getWriter(); // 이 출력 스트림을 사용하여 클라이언트에 데이터를 보낼 수 있음.
        out.print(jsonObj.toString()); //문자열 형태의 JSON으로 변환  ex: jsonObj.put("userid", "user123"); >> {"userid":"user123"}로 변환
        out.flush(); // 출력 버퍼에 있는 데이터를 즉시 클라이언트로 전송

	}

}
