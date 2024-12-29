package member.controller;

import java.io.PrintWriter;
import java.util.HashMap;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class SmsSend extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		String method = request.getMethod();
		String idleMemberMobile =request.getParameter("idleMemberMobile");
		System.out.println("idleMemberMobile(smssend.java) : " + idleMemberMobile);
		if("POST".equalsIgnoreCase(method)) {
	    	String passNumber = request.getParameter("passNumber");
	    	String randomNumber = request.getParameter("randomNumber");
	    	
			JSONObject jsonObj = new JSONObject();
	        System.out.println("randomNumber: " + randomNumber);
	        System.out.println("passNumber: " + passNumber);
	       // System.out.println("aes 암호화 된 서버 전화번호2 : " + aes.encrypt(idleMemberMobile));
        	System.out.println("암호화 전 서버 전화번호2 : " + idleMemberMobile);
	        if (passNumber != null && !passNumber.trim().isEmpty()) {
	            try {
	            	//System.out.println("Sha256 암호화 된 서버 전화번호1 : " + Sha256.encrypt(idleMemberMobile));
                	System.out.println("암호화 전 서버 전화번호1 : " + idleMemberMobile);
	                if (randomNumber.equals(passNumber)) {
	                	//휴면계정 인증이 완료되었으니 idle_staus, logingap 1과 0으로 초기화
	                	//System.out.println("암호화 된 서버 전화번호 : " + Sha256.encrypt(idleMemberMobile));
	                	System.out.println("암호화 전 서버 전화번호 : " + idleMemberMobile);
	                	int result1 = mdao.idleStatusUpdate(idleMemberMobile);
	                	int result2 = mdao.loginHistoryDelete(idleMemberMobile);
	                	jsonObj.put("result1", result1);
	                	jsonObj.put("result2", result2);
	                } else {
	                    System.out.println("인증번호가 틀렸습니다.");
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("잘못된 인증번호 형식입니다.");
	            }
	        } else {
	            System.out.println("인증번호가 입력되지 않았습니다.");
	            return;
	        }
	        	
	        // 응답 데이터 설정
	        respone.setContentType("application/json; charset=UTF-8");//응답데이터가 json 형식임을 알리는 용도
	        PrintWriter out = respone.getWriter(); // 이 출력 스트림을 사용하여 클라이언트에 데이터를 보낼 수 있음.
	        out.print(jsonObj.toString()); //문자열 형태의 JSON으로 변환  ex: jsonObj.put("userid", "user123"); >> {"userid":"user123"}로 변환
	        out.flush(); // 출력 버퍼에 있는 데이터를 즉시 클라이언트로 전송
			return;
	    }
			
			//난수 생성
		    int randomNumber = (int)((Math.random()*(9999-1000+1)) + 1000);
			// >> SMS발송 <<
			// HashMap 에 받는사람번호, 보내는사람번호, 문자내용 등 을 저장한뒤 Coolsms 클래스의 send를 이용해 보냅니다.

			// String api_key = "발급받은 본인의 API Key"; // 발급받은 본인 API Key
			String api_key = "NCSGYVWRAP12M93B"; // 나의 키

			// String api_secret = "발급받은 본인의 API Secret"; // 발급받은 본인 API Secret
			String api_secret = "QBQHN7YKB2GTNMT7ULHIOPTQSD0PTOXA";
			String mobile = request.getParameter("mobile");
			System.out.println("받은 전화번호: " + mobile);
			if (mobile == null || !mobile.matches("\\d{10,11}")) {
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
		    params.put("text", "HGV 휴면해제 인증번호는" + "[" + randomNumber + "]" + "입니다.");
		    // application name and version
		    params.put("app_version", "test app 1.2");
			
		    
		    
		    try {
		        JSONObject jsonObj = (JSONObject) coolsms.send(params);
		        	jsonObj.put("randomNumber", randomNumber);
		        	
		        // 응답 데이터 설정
		        respone.setContentType("application/json; charset=UTF-8");//응답데이터가 json 형식임을 알리는 용도
		        PrintWriter out = respone.getWriter(); // 이 출력 스트림을 사용하여 클라이언트에 데이터를 보낼 수 있음.
		        out.print(jsonObj.toString()); //문자열 형태의 JSON으로 변환  ex: jsonObj.put("userid", "user123"); >> {"userid":"user123"}로 변환
		        out.flush(); // 출력 버퍼에 있는 데이터를 즉시 클라이언트로 전송
				return;
		    } catch (CoolsmsException e) {
		    }
		
		
		
		    
	    
	}

}
