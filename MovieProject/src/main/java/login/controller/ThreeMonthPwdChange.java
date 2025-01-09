package login.controller;

import java.io.PrintWriter;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class ThreeMonthPwdChange extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); 
		
		if(!"POST".equalsIgnoreCase(method)) {// GET 방식인 경우
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login/threeMonthPwdChange.jsp");
			return; 
		}
		
		
		/////////POST 방식의 경우////////////
		
		//세션값 꺼내기
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		// 0이면 현재비밀번호와 사용자가 입력한 비밀번호 ajax, 1이면 비밀번호 변경하기 클릭 시 ajax
		String number = request.getParameter("number");
		String inputPwd = request.getParameter("inputPwd");//사용자가 입력한 비밀번호
		String pwd = request.getParameter("pwd");
		
		
		// JSON 객체 생성
        JSONObject jsonObj = new JSONObject();
		
        if("0".equals(number)) {
			//true면 현재 비밀번호와 사용자가 입력한 비밀번호가 일치함 
			boolean statusPwd = mdao.currentPwd(loginuser.getUserid(),inputPwd); 
	       
	        jsonObj.put("statusPwd", statusPwd); //true면 현재 비밀번호와 사용자가 입력한 비밀번호가 일치함 
		}
		
		if("1".equals(number)) { 
			//System.out.println("로그인유저 비밀번호 : " +loginuser.getPwd());//null
			
			int updatePwd = mdao.threeMonthPwdChange(loginuser.getUserid(),pwd);
			
	        jsonObj.put("updatePwd", updatePwd); //true면 현재 비밀번호와 사용자가 입력한 비밀번호가 일치함 
	      
		}
			
		// 응답 데이터 설정
		String json = jsonObj.toString();
        request.setAttribute("json", json);
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
