package mypage.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MyupcheckPwd extends AbstractController {

	private MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String method = request.getMethod(); // "GET" 또는 "POST"

	    if ("POST".equalsIgnoreCase(method)) {
	        String pwd = request.getParameter("pwd");
	        String userid = request.getParameter("userid");

	        Map<String, String> paraMap = new HashMap<>();
	        paraMap.put("userid", userid);
	        paraMap.put("pwd", pwd);

	        boolean checkPassword = mydao.checkPassword(paraMap); // 비밀번호 검증 메서드


	        if (checkPassword) {
	            // 비밀번호가 일치하면 회원정보 수정 페이지로 이동
	            super.setRedirect(true);
	            super.setViewPage(request.getContextPath() +"/mypage/myupEdit.mp"); // 회원정보 수정 페이지
	        } else {
	            // 비밀번호가 일치하지 않을 경우
	            request.setAttribute("message", "비밀번호가 일치하지 않습니다.");
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/mypage/myupcheckPwd.jsp");
	        }
	    } else {
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/mypage/myupcheckPwd.jsp");
	    }
	    
	    
	}

}
