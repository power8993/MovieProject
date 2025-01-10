package mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MyuppwdEdit extends AbstractController {

	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

			String method = request.getMethod(); // "GET" 또는 "POST"

			if ("POST".equalsIgnoreCase(method)) {
				// "암호변경하기" 버튼을 클릭했을 경우
				
				String userid = loginuser.getUserid();
				String new_pwd = request.getParameter("pwd");

				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("userid", userid);
				paraMap.put("new_pwd", new_pwd);

				int n = 0;
				try {
					n = mydao.mypwdUdate(paraMap);
					
					JSONObject jsobj = new JSONObject();  // {}
			         jsobj.put("n", n);   // {"n":1}
			         
			         String json = jsobj.toString();   // "{"n":1}"
			         
			         request.setAttribute("json", json);
			         
			         super.setRedirect(false);
			         super.setViewPage("/WEB-INF/jsonview.jsp");

		} catch (Exception e) {
			e.printStackTrace();

			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/error.jsp");
		}
		} else {
			request.setAttribute("message", "로그인이 필요합니다.");
			request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}

	}
	}
