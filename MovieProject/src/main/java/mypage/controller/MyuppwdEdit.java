package mypage.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MyuppwdEdit extends AbstractController {

	MypageDAO mydao = new MypageDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userid = request.getParameter("userid");

		String method = request.getMethod(); // "GET" 또는 "POST"

		if ("POST".equalsIgnoreCase(method)) {
			// "암호변경하기" 버튼을 클릭했을 경우
			String new_pwd = request.getParameter("pwd");

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("new_pwd", new_pwd);
			
			int n = 0;
			try {
				n = mydao.mypwdUdate(paraMap);
			} catch (SQLException e) {

			}
			request.setAttribute("n", n);
		} // end of if---

		request.setAttribute("userid", userid);
		request.setAttribute("method", method);


		super.setRedirect(false);
		super.setViewPage("/WEB-INF/mypage/myuppwdEdit.jsp");

	}

}
