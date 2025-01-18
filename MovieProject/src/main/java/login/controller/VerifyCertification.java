package login.controller;

import java.io.PrintWriter;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyCertification extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			String userCertificationCode = request.getParameter("userCertificationCode");
			String userid = request.getParameter("userid");
			
			HttpSession session = request.getSession(); // 세션 불러오기
			String certification_code = (String)session.getAttribute("certification_code");
			
			boolean is_True_false = false;
			String loc = "";
			
			if(certification_code.equals(userCertificationCode)) {
				is_True_false = true;
				loc = request.getContextPath()+"/login/pwdUpdateEnd.mp?userid="+userid;
			}
			
			//////////////////////////////////////////////////////////////////
			
			// JSON 응답 생성
			JSONObject json = new JSONObject();
			json.put("is_True_false", is_True_false);
			json.put("userid", userid);
			
			// JSON 응답 설정
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			
			// !!!! 중요 !!!! //
	        // !!!! 세션에 저장된 인증코드 삭제하기 !!!! //
			session.removeAttribute("certification_code"); // ajax와는 상관없음
			
			return; // 더 이상 JSP 페이지로 포워딩하지 않음
			
			
			
		} // end of if("POST".equalsIgnoreCase(method))-----------------------------------------------
		
	}

}
