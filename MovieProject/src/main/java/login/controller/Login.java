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
		
		HttpSession session = request.getSession(); // 기존 세션 가져오기 (없으면 null)
	    if (session.getAttribute("loginuser") != null) {// 이미 로그인된 상태라면 메인 페이지로 리디렉션
	    	super.setRedirect(true);
	    	super.setViewPage(request.getContextPath() + "/index.mp"); // 메인 페이지 URL
	        return;
	    }
		
		if(!"POST".equalsIgnoreCase(method)) {
			// POST 방식으로 넘어온 것이 아니라면
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login/login.jsp");
			return; 
		}
		else {
			// POST 방식으로 넘어온 것이라면
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			String successRegister = request.getParameter("successRegister");// 회원가입이 성공했다면 "1", 실패 했다면 null
			
			// ==== 클라이언트의 IP 주소를 알아오는 것 ==== //
			String clientip = request.getRemoteAddr();
			// 먼저, C:\NCS\workspace_jsp\MyMVC\src\main\webapp\JSP 파일을 실행시켰을 때 IP 주소가 제대로 출력되기위한 방법 참조
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("pwd", pwd);
			paraMap.put("clientip", clientip);
			

			
			String lastLogin = mdao.lastLogin(paraMap); // 마지막 로그인 날짜 구하기
			String idleChange = mdao.idleChange(paraMap); //휴면 전환 날짜 날짜 구하기
			
			MemberVO loginuser = mdao.login(paraMap);
			
			int RequirePwdChange = 0; // 비밀번호 변경 3개월 이상 1, 3개원 미만 0
			
			if(loginuser != null && loginuser.getIdle()== 99) { // 아이디비밀번호가 맞으며, 휴면계정이 아닐 경우
	
				//System.out.println("비번변경일 : "+loginuser.getLastpwdchangedate());
				
				//HttpSession session = request.getSession();// WAS 메모리에 생성되어져 있는 session 을 불러오는 것
	            
				session.setAttribute("loginuser", loginuser);// session(세션)에 로그인 되어진 사용자 정보인 loginuser 를 키이름을 "loginuser" 으로 저장시켜두는 것
	
				if("1".equals(successRegister)) { // 회원가입 성공 후 자동 로그인
				    super.setRedirect(true); // 페이지 이동을 처리
				    super.setViewPage(request.getContextPath() + "/index.mp"); // index.mp로 이동
					return; 

				}
				if(loginuser.isRequirePwdChange() ) { // 비밀번호를 변경한지 3개월 이상된 경우
					//System.out.println("비밀번호를 변경하신지 3개월이 지났습니다.");
					RequirePwdChange = 1;
				}
				else { // 비밀번호를 변경한지 3개월 미만인 경우
					//System.out.println("비밀번호를 변경하신지 3개월미만입니다.");
					RequirePwdChange = 0;
				}
			} // end of if(loginuser != null)-------------------------------------------------------------------
			
			
			// JSON 객체 생성
	        JSONObject jsonObj = new JSONObject();
	        if (loginuser != null) {
	        	jsonObj.put("userid", userid); // id를 이용해서 휴면계정의 아이디와 일치하는 전화번호만 인증이 가능하도록.
	        	jsonObj.put("loginuser", loginuser.getIdle()); // 아이디비밀번호가 맞으며, 휴면계정이 아닐 경우만 값이 삽입됨.
	        	jsonObj.put("getIdle", loginuser.getIdle());
	        	jsonObj.put("lastLogin", lastLogin);
	        	jsonObj.put("idleChange", idleChange);
	        	jsonObj.put("RequirePwdChange", RequirePwdChange); // 비밀번호 변경 필요 여부 전달
	        } 
	        else { // 로그인 실패시 모든 것이 null값으로 ajax로 넘어감.
	        	jsonObj.put("loginuser",JSONObject.NULL); 
	        	jsonObj.put("getIdle", JSONObject.NULL);
	        	jsonObj.put("lastLogin",JSONObject.NULL); 
	        	jsonObj.put("idleChange", JSONObject.NULL);
	        }
	        jsonObj.put("ctxPath", request.getContextPath());
	        
	        String json = jsonObj.toString();
	         request.setAttribute("json", json);
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/jsonview.jsp");
		}
	}
}
