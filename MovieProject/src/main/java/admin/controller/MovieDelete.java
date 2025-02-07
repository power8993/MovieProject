package admin.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.model.MovieDAO;
import movie.model.MovieDAO_imple;

public class MovieDelete extends AbstractController {

	private MovieDAO mvdao = new MovieDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
		
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method)) {
				
				// 폼태그의 값 받기	
				String seq = request.getParameter("seq");
	
				try {
	
					// **** 영화를 삭제하는 메소드(seq에 해당하는 영화를 delete) **** //
					int n = mvdao.deleteMovie(seq);
	
					if (n == 1) {
						String message = "영화가 비활성화 되었습니다.";
						String loc = "movieRegisteredList.mp";
	
						request.setAttribute("message", message);
						request.setAttribute("loc", loc);
						
						super.setRedirect(false);
						super.setViewPage("/WEB-INF/msg.jsp");
					}
	
				} catch (SQLException e) {
					e.printStackTrace();
	
					String message = "영화 삭제 실패";
					String loc = "javascript:history.back()";
	
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
	
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
			}
			
		}// end of if(loginuser != null && "admin".equalsIgnoreCase(loginuser.getUserid())) {}------------------------------
		else {
			// 일반사용자로 로그인 한 경우
			request.setAttribute("message", "관리자만 접근이 가능합니다.");
			request.setAttribute("loc", request.getContextPath() + "/index.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}-------------------------

}
