package reservation.controller;

import java.util.HashMap;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.nurigo.java_sdk.api.Message;

public class SendReservationSMS extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST"
	    
		String message = "";
		String loc = "";
	    
		if("POST".equalsIgnoreCase(method)) {
			// POST 방식이면
			
			String name = request.getParameter("name");
			String ticketPrice = request.getParameter("ticketPrice");
			String mobile = request.getParameter("mobile");
			
			String content = "[HCV] 안녕하세요 " + name + " 님\n영화 예매가 완료되었습니다.\n결제금액:" + ticketPrice + " 원\n감사합니다."; 
			
			// String api_key = "발급받은 본인의 API Key";  // 발급받은 본인 API Key
			String api_key = "NCSREYS1NVVZCGOU"; // 김성훈꺼
			
			// String api_secret = "발급받은 본인의 API Secret";  // 발급받은 본인 API Secret 
			String api_secret = "LVPPN6MKAY72DYYIITSMAI6VSCL1SZFS"; // 김성훈꺼
			
			Message coolsms = new Message(api_key, api_secret);
			// net.nurigo.java_sdk.api.Message 임. 
			// 먼저 다운 받은  javaSDK-2.2.jar 와 json-simple-1.1.1.jar 를 /MyMVC/src/main/webapp/WEB-INF/lib/ 안에 넣어서  build 시켜야 함.
			HashMap<String, String> paraMap = new HashMap<>();
			paraMap.put("to", mobile); // 수신번호
			paraMap.put("from", "01091994753"); // 발신번호
			// 2020년 10월 16일 이후로 발신번호 사전등록제로 인해 등록된 발신번호로만 문자를 보내실 수 있습니다
			paraMap.put("type", "SMS"); // Message type ( SMS(단문), LMS(장문), MMS, ATA )
			paraMap.put("text", content); // 문자내용
			
			paraMap.put("app_version", "JAVA SDK v2.2"); // application name and version
			
			JSONObject jsobj = (JSONObject) coolsms.send(paraMap);
	       
			String json = jsobj.toString();
		     
			request.setAttribute("json", json);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
		else {
			// GET 방식이면
			
			message = "비정상적인 경로로 들어왔습니다.";
			loc = "javascript:history.back()";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}

}
