package login.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject; // JSON 객체를 생성하기 위해 필요

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class PwdFind extends AbstractController {

    private MemberDAO mdao = new MemberDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String method = request.getMethod(); // "GET" 또는 "POST"

        if ("POST".equalsIgnoreCase(method)) {
            // 비밀번호 찾기에서 "찾기" 버튼을 클릭했을 경우

            String userid = request.getParameter("userid");
            String email = request.getParameter("email");

            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("userid", userid);
            paraMap.put("email", email);

            boolean isUserExist = mdao.isUserExist(paraMap);

            //////////////////////////////////////////////////////////////////

            boolean sendMailSuccess = false; // 메일이 정상적으로 전송되었는지 여부

            if (isUserExist) {
                // 회원이 존재하는 경우

                // 인증키를 랜덤하게 생성
                Random rnd = new Random();

                String certification_code = "";

                // 영문 소문자 5글자 생성
                for (int i = 0; i < 5; i++) {
                    char randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a');
                    certification_code += randchar;
                }

                // 숫자 7글자 생성
                for (int i = 0; i < 7; i++) {
                    int randnum = rnd.nextInt(10);
                    certification_code += randnum;
                }

                // 랜덤하게 생성한 인증코드를 이메일로 전송
                GoogleMail mail = new GoogleMail();
                try {
                    mail.send_certification_code(email, certification_code);
                    sendMailSuccess = true;

                    // 세션에 인증코드 저장
                    HttpSession session = request.getSession();
                    session.setAttribute("certification_code", certification_code);

                } catch (Exception e) {
                    e.printStackTrace();
                    sendMailSuccess = false;
                }
            }

            //////////////////////////////////////////////////////////////////

            // JSON 응답 생성
            JSONObject json = new JSONObject();
            json.put("isUserExist", isUserExist);
            json.put("sendMailSuccess", sendMailSuccess);
            json.put("email", email);
            json.put("userid", userid);

            // JSON 응답 설정
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json.toString());
            out.flush();
            return; // 더 이상 JSP 페이지로 포워딩하지 않음
        }
    }
}
