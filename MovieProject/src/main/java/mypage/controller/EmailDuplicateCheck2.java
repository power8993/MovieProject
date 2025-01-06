package mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.*;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class EmailDuplicateCheck2 extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();
	private MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			String method = request.getMethod(); // "GET" 또는 "POST"
			if ("POST".equalsIgnoreCase(method)) {
			String email = request.getParameter("email");
			String userid = request.getParameter("userid");

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("email", email);
			paraMap.put("userid", userid);
			
		
			boolean isExists = mydao.emailDuplicateCheck2(paraMap);
		
			if(!isExists) {
				isExists= mdao.emailDuplicateCheck(email);
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("isExists", isExists);
			
			String json = jsonObj.toString();	
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			}
		}
	}

