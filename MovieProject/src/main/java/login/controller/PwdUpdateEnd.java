package login.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class PwdUpdateEnd extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");

		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		int n = 0 ;
		
		if("POST".equalsIgnoreCase(method)) {
			// "암호변경하기" 버튼을 클릭했을 경우
			
			String new_pwd = request.getParameter("pwd");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("new_pwd", new_pwd);
			
			n = 0;
			
			try {
				n = mdao.pwdUpdate(paraMap);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//request.setAttribute("n", n);
			
		} // end of if("POST".equalsIgnoreCase(method))---------------------------------
		
		// JSON 객체 생성
        JSONObject jsonObj = new JSONObject();
        if (userid != null) {
            jsonObj.put("userid", userid);
            jsonObj.put("method", method);
            jsonObj.put("n", n);
        } else {
            jsonObj.put("userid", JSONObject.NULL); // 존재하지 않으면 null로 설정
        }
        
        // 응답 데이터 설정
        response.setContentType("application/json; charset=UTF-8");//응답데이터가 json 형식임을 알리는 용도
        PrintWriter out = response.getWriter(); // 이 출력 스트림을 사용하여 클라이언트에 데이터를 보낼 수 있음.
        out.print(jsonObj.toString()); //문자열 형태의 JSON으로 변환  ex: jsonObj.put("userid", "user123"); >> {"userid":"user123"}로 변환
        out.flush(); // 출력 버퍼에 있는 데이터를 즉시 클라이언트로 전송
		/*
		 * request.setAttribute("userid", userid); request.setAttribute("method",
		 * method);
		 * 
		 * super.setRedirect(false);
		 * super.setViewPage("/WEB-INF/login/pwdUpdateEnd.jsp");
		 */
	}

}
