package notice.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.domain.NoticeDTO;
import notice.model.NoticeDAO_imple;

public class NoticeDetail extends AbstractController {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 공지사항 번호를 파라미터로 받기
        String seq_Notice_no = request.getParameter("seq");
        
        // NoticeDAO_imple 객체를 생성하여 상세 공지사항 조회
        NoticeDAO_imple noticeDAO = new NoticeDAO_imple();
        NoticeDTO notice = noticeDAO.detailNotice(Integer.parseInt(seq_Notice_no));

        // 조회한 공지사항 정보를 request에 담기
        request.setAttribute("notice", notice);

        // noticeDetail.jsp로 이동
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/notice/noticeDetail.jsp");
    }
}
