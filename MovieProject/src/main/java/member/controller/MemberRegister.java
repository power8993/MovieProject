package member.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberRegister extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("GET".equalsIgnoreCase(method) ) {
//			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberRegister.jsp");
		}
		else {
			
			String name = request.getParameter("name");
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			String email = request.getParameter("email");
	        String hp1 = request.getParameter("hp1");
	        String hp2 = request.getParameter("hp2");
	        String hp3 = request.getParameter("hp3");
	        String gender = request.getParameter("gender");
	        String year = request.getParameter("year");
	        String month = request.getParameter("month");
	        String day = request.getParameter("day");
	        
	        if(Integer.parseInt(month)<10) {
	        	month = "0"+month;
	        }
	        if(Integer.parseInt(day)<10) {
	        	day = "0"+day;
	        }
	        String birthday = year+"-"+month+"-"+day;
	        
	        String mobile = hp1 + hp2 + hp3;
	        
	        MemberVO member = new MemberVO();
	        member.setUserid(userid);
	        member.setPwd(pwd);
	        member.setName(name);
	        member.setEmail(email);
	        member.setMobile(mobile);
	        member.setGender(gender);
	        member.setBirthday(birthday);

	       
	        
	        // 회원가입이 성공되어지면 자동으로 로그인 되도록 //
	        try {
	        	int n = mdao.registerMember(member);
	        	
	        	if(n == 1) {
	        	
	        		request.setAttribute("userid", userid);
	        		request.setAttribute("pwd", pwd);
	        		
	        		super.setRedirect(false);
	        		super.setViewPage("/WEB-INF/login/memberRegister_after_autoLogin.jsp");
	        		
	        	}
	        	
	        } catch (SQLException e) {
	        	System.out.println("값"+request.getParameter("name"));
				System.out.println(request.getParameter("userid"));
				System.out.println(request.getParameter("pwd"));
				System.out.println(request.getParameter("email"));
	        	e.printStackTrace();
	        	String message = "회원가입 실패";
	        	String loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동하는 것.
	        	
	        	request.setAttribute("message", message);
	        	request.setAttribute("loc", loc);
	        	
	        	super.setRedirect(false);
	        	super.setViewPage("/WEB-INF/msg.jsp");
			}
	        
		}
	        
		
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response)-----------------------------------------

}
