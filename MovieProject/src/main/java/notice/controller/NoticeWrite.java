package notice.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.domain.NoticeDTO;
import notice.model.NoticeDAO;
import notice.model.NoticeDAO_imple;

public class NoticeWrite extends AbstractController {
	
	private NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 String method = request.getMethod(); // "GET" 또는 "POST"
		    
		    if("GET".equals(method)) {
	            super.setRedirect(false);
		        super.setViewPage("/WEB-INF/notice/noticeWrite.jsp");
		    }
		    else {
		        String notice_subject = request.getParameter("notice_subject");
		        String notice_content = request.getParameter("notice_content");
		        notice_content=notice_content.replace("\r\n","<br>");
		        
		        NoticeDTO ndto = new NoticeDTO();
		        ndto.setNotice_subject(notice_subject);
		        ndto.setNotice_content(notice_content);
		        
		        try {
		            int n = ndao.insertNotice(ndto);
		            
		            if (n == 1) {
		                // 정상적으로 등록되었으면 목록 페이지로 리다이렉트
		                super.setRedirect(true);
		                super.setViewPage(request.getContextPath() + "/notice/notice.mp"); // 경로가 잘못되지 않았는지 확인
		                System.out.println("공지사항입력완료");
		            } else {
		                super.setRedirect(true);
		                super.setViewPage(request.getContextPath() + "/notice/noticeWrite.jsp"); // 실패 시 작성 페이지로 돌아가게
		                System.out.println("공지사항입력실패");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            
		            String message = "글작성 실패ㅜㅜ";
		            String loc = "javascript:history.back()";  // 자바스크립트를 이용한 이전페이지로 이동하는 것
		            request.setAttribute("message", message);
		            request.setAttribute("loc", loc);
		            
		            super.setRedirect(false);
		            super.setViewPage("/WEB-INF/notice/notice.jsp");
		        }
		    }
		
	}
}
