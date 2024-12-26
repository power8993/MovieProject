package login.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class Login extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if(!"POST".equalsIgnoreCase(method)) {
			// POST 방식으로 넘어온 것이 아니라면
			System.out.println(method);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login/login.jsp");
			return; 
		}
		else {
			// POST 방식으로 넘어온 것이라면
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			
			// ==== 클라이언트의 IP 주소를 알아오는 것 ==== //
			String clientip = request.getRemoteAddr();
			// 먼저, C:\NCS\workspace_jsp\MyMVC\src\main\webapp\JSP 파일을 실행시켰을 때 IP 주소가 제대로 출력되기위한 방법 참조
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("pwd", pwd);
			paraMap.put("clientip", clientip);
			
			MemberVO loginuser = mdao.login(paraMap);
			
			if(loginuser != null && loginuser.getIdle()== 99) { // 아이디비밀번호가 맞으며, 휴면계정이 아닐 경우
	//			System.out.println("~~~~ 확인용 로그인 성공 ^_________________________________^");
				
				//System.out.println("확인용 : "+loginuser.getIdle());
				
				
				HttpSession session = request.getSession();
	            // WAS 메모리에 생성되어져 있는 session 을 불러오는 것이다.
				
				session.setAttribute("loginuser", loginuser);
				// session(세션)에 로그인 되어진 사용자 정보인 loginuser 를 키이름을 "loginuser" 으로 저장시켜두는 것이다.
	
			
				 
				/*if(loginuser.isRequirePwdChange() ) { // 비밀번호를 변경한지 3개월 이상된 경우
					String message = "비밀번호를 변경하신지 3개월이 지났습니다.\\n암호를 변경하는 페이지로 이동합니다!!"; 
					String loc = request.getContextPath()+"/index.mp";
					// 원래는 위와같이 index.mp 이 아니라 암호를 변경하는 페이지로 URL을 잡아주어야 한다.!!
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false); 
					super.setViewPage("/WEB-INF/msg.jsp");
					
					return; // 메소드 종료 
				}
				else {*/ // 비밀번호를 변경한지 3개월 미만인 경우
					
					// 페이지 이동을 시킨다.
					/*
					 * super.setRedirect(true);
					 * super.setViewPage(request.getContextPath()+"/index.mp");
					 */
					
				//}
				
			} // end of if(loginuser != null)-------------------------------------------------------------------
			
			
			// JSON 객체 생성
	        JSONObject jsonObj = new JSONObject();
	        if (loginuser != null) {
	        	jsonObj.put("loginuser", loginuser.getIdle());
	        	jsonObj.put("getIdle", loginuser.getIdle());
	        } 
	        else { // 로그인 실패시 모든 것이 null값으로 ajax로 넘어감.
	        	jsonObj.put("loginuser",JSONObject.NULL); 
	        	jsonObj.put("getIdle", JSONObject.NULL);
	        }
	        jsonObj.put("ctxPath", request.getContextPath());
	        
	        // 응답 데이터 설정
	        response.setContentType("application/json; charset=UTF-8");//응답데이터가 json 형식임을 알리는 용도
	        PrintWriter out = response.getWriter(); // 이 출력 스트림을 사용하여 클라이언트에 데이터를 보낼 수 있음.
	        out.print(jsonObj.toString()); //문자열 형태의 JSON으로 변환  ex: jsonObj.put("userid", "user123"); >> {"userid":"user123"}로 변환
	        out.flush(); // 출력 버퍼에 있는 데이터를 즉시 클라이언트로 전송
			return;
		}
	}
}
