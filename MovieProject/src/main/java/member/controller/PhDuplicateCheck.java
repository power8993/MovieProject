package member.controller;

import java.io.PrintWriter;
import java.util.HashMap;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class PhDuplicateCheck extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String phoneNumber = request.getParameter("phoneNumber");//전화번호 인증 버튼 클릭 시 ajax로 전송되는 값
		
		String randomNumberAuth = request.getParameter("randomNumberAuth"); // 인증하기 버튼 클릭 시 ajax로 전송되는 값. 해당 버튼 클릭 시의 로직 구성을 위해.
		String authPassInput = request.getParameter("authPassInput"); //인증번호 전송 후 생기는 input의 value값. (사용자 입력 인증번호 값)
		System.out.println("phoneNumber:"+phoneNumber);
		
		if(randomNumberAuth.equals("randomNumberAuth")) { // 인증하기 버튼을 클릭한 경우를 구성하기위한 if문
			
			HttpSession session = request.getSession();
			
			String sessionRandomNumber = String.valueOf(session.getAttribute("randomNumber")); // Object를 String으로 변환
			// JSON 객체 생성
	        JSONObject jsonObj = new JSONObject();
			
			boolean isAuthStatus = false; // 인증번호가 일치하면 true, 틀리면 false;
			
			if(sessionRandomNumber.equals(authPassInput)) { // 인증번호가 일치할 시 
				isAuthStatus=true;
				session.removeAttribute("randomNumber");
				jsonObj.put("isAuthStatus", isAuthStatus); // 인증 상태 전달.
				
			}
			else { // 인증번호가 틀릴 시
				System.out.println("인증번호 틀림 입력인증번호 : " + authPassInput);
				session.removeAttribute("randomNumber");
				jsonObj.put("isAuthStatus", isAuthStatus); // 인증 상태 전달.
			}
				
			
			
	        // 응답 데이터 설정
	        response.setContentType("application/json; charset=UTF-8");//응답데이터가 json 형식임을 알리는 용도
	        PrintWriter out = response.getWriter(); 	// 이 출력 스트림을 사용하여 클라이언트에 데이터를 보낼 수 있음.
	        out.print(jsonObj.toString()); 				//문자열 형태의 JSON으로 변환  ex: jsonObj.put("userid", "user123"); >> {"userid":"user123"}로 변환
	        out.flush(); 
			return;
			
		}// end of if(randomNumberAuth.equals("randomNumberAuth")) {})--------------------
		
		
		//인증하고자 하는 전화번호가 존재하는지 확인. DB에 존재하면 true 존재하지않으면 false
		boolean isExists = mdao.PhoneDuplicateCheck(phoneNumber);
		
		
			// 중복확인을 통과한 경우
		  if (!isExists) { // 메세지를 보내주는 mp로 이동 해서 난수 값을 세션에 저장. 사용자가 입력한 인증번호와 세션에 저장된 인증번호로 비교
			//난수 생성
			    int randomNumber = (int)((Math.random()*(9999-1000+1)) + 1000);
			    
			    HttpSession session = request.getSession();// WAS 메모리에 생성되어져 있는 session 을 불러오는 것
	            
				session.setAttribute("randomNumber", randomNumber); // 인증번호(난수값)을 세션에 저장시켜둠. 인증버튼 클릭 시 삭제.
			    
				// >> SMS발송 <<
				// HashMap 에 받는사람번호, 보내는사람번호, 문자내용 등 을 저장한뒤 Coolsms 클래스의 send를 이용해 보냅니다.

				// String api_key = "발급받은 본인의 API Key"; // 발급받은 본인 API Key
				String api_key = "NCSGYVWRAP12M93B"; // 나의 키

				// String api_secret = "발급받은 본인의 API Secret"; // 발급받은 본인 API Secret
				String api_secret = "QBQHN7YKB2GTNMT7ULHIOPTQSD0PTOXA";
				String mobile = phoneNumber;
				if (mobile == null || !mobile.matches("\\d{10,11}")) {// 잘못된 전화번호 형식
					System.out.println("잘못된 전화번호 형식입니다.");
				    return;
				}
				
				Message coolsms = new Message(api_key, api_secret);
			    // 4 params(to, from, type, text) must be filled
			    HashMap<String, String> params = new HashMap<>();
			    // 수신전화번호
			    params.put("to", mobile);
			    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
			    params.put("from", "01043189993");
			    params.put("type", "SMS");
			    // 문자 내용 입력
			    params.put("text", "HGV 회원가입 전화번호 인증번호는" + "[" + randomNumber + "]" + "입니다.");
			    // application name and version
			    params.put("app_version", "test app 1.2");
			    
			    try {
			        JSONObject jsonObj = (JSONObject) coolsms.send(params);
			        jsonObj.put("randomNumber", randomNumber);
			        response.setContentType("application/json; charset=UTF-8");
			        PrintWriter out = response.getWriter();
			        out.print(jsonObj.toString());
			        out.flush();
			        return;
			    } catch (CoolsmsException e) {
			        System.out.println("메시지 전송 실패: " + e.getMessage());
			        e.printStackTrace();
			    }

			  
		  }// end of if (!isExists) {})-----------
		 
		
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
