package notice.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeDTO;
import notice.model.NoticeDAO;
import notice.model.NoticeDAO_imple;

public class NoticeEdit extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // "GET" 또는 "POST"

		HttpSession session = request.getSession();

		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		if (loginuser != null && "admin".equals(loginuser.getUserid())) {
			if (!"POST".equalsIgnoreCase(method)) {
				String seq_notice_no = request.getParameter("seq");
				request.setAttribute("seq_notice_no", seq_notice_no);

				String notice_subject = request.getParameter("subject");
				request.setAttribute("notice_subject", notice_subject);

				String notice_content = request.getParameter("notice_content");
				notice_content = notice_content.replace("<br>", "\r\n");
				request.setAttribute("notice_content", notice_content);

				super.setRedirect(false);
				super.setViewPage("/WEB-INF/notice/noticeEdit.jsp");
			} else {
				String seq_notice_no = request.getParameter("seq_notice_no");
				String notice_subject = request.getParameter("notice_subject");
				String notice_content = request.getParameter("notice_content");
				notice_content = notice_content.replace("\r\n", "<br>").replace("\n", "<br>");

				NoticeDTO ndto = new NoticeDTO();
				ndto.setSeq_notice_no(Integer.parseInt(seq_notice_no));

				ndto.setNotice_subject(notice_subject);
				ndto.setNotice_content(notice_content);

				try {
					int n = ndao.editNotice(ndto);

					if (n == 1) {
						// 정상적으로 등록되었으면 목록 페이지로 리다이렉트
						super.setRedirect(true);
						super.setViewPage(request.getContextPath() + "/notice/noticeDetail.mp?seq=" + seq_notice_no);
					} else {
						super.setRedirect(false);
						super.setViewPage("/WEB-INF/notice/noticeEdit.jsp"); // 실패 시 작성 페이지로 돌아가게
					}
				} catch (SQLException e) {
					e.printStackTrace();

					String message = "글작성 실패ㅜㅜ";
					String loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동하는 것
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);

					super.setRedirect(false);
					super.setViewPage("/WEB-INF/notice/notice.jsp");
				}
			}
		} 
		else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우
			String message = "관리자만 접근이 가능합니다.";
			String loc = "javascript:history.back()";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);

			// super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}
}
