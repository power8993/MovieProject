package mypage.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyreservationReceipt extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      super.setRedirect(false);
	      super.setViewPage("/WEB-INF/mypage/myreservationReceipt.jsp");

	}

}
