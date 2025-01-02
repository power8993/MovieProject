package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class IdleClear extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		String userid = request.getParameter("userid");
		
		if(!"POST".equalsIgnoreCase(method)) {// POST 방식으로 넘어온 것이 아니라면
			//System.out.println("메소드: "+method);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login/idleClear.jsp");
			return; 
		}
		
		// POST 방식으로 넘어온 경우 
		
		//휴면계정 아이디의 전화번호 구하기
		String idleMemberMobile = mdao.idleMemberMobile(userid);
		
		
	    request.setAttribute("idleMemberMobile", idleMemberMobile);
	    //System.out.println("회원전화번호(idleClear.java)" + idleMemberMobile);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/idleClear.jsp");
		return; 
	}

}
