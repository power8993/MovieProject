package login.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class IdFind extends AbstractController {
    
    private MemberDAO mdao = new MemberDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String method = request.getMethod(); // "GET" 또는 "POST"
        
        if ("POST".equalsIgnoreCase(method)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("name", name);
            paraMap.put("email", email);
            
            String userid = mdao.findUserid(paraMap);
            
            // JSON 객체 생성
            JSONObject jsonObj = new JSONObject();
            if (userid != null) {
                jsonObj.put("userid", userid);
            } else {
                jsonObj.put("userid", JSONObject.NULL); // 존재하지 않으면 null로 설정
            }
            
            // 응답 데이터 설정
            response.setContentType("application/json; charset=UTF-8");//응답데이터가 json 형식임을 알리는 용도
            PrintWriter out = response.getWriter(); // 이 출력 스트림을 사용하여 클라이언트에 데이터를 보낼 수 있음.
            out.print(jsonObj.toString()); //문자열 형태의 JSON으로 변환  ex: jsonObj.put("userid", "user123"); >> {"userid":"user123"}로 변환
            out.flush(); // 출력 버퍼에 있는 데이터를 즉시 클라이언트로 전송
        }
    }
}
