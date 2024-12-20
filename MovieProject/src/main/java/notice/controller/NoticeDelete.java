package notice.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.model.NoticeDAO_imple;

public class NoticeDelete extends AbstractController {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // POST 요청으로 공지사항 번호(seq_notice_no) 파라미터 받기
        String seq_notice_no = request.getParameter("seq_notice_no"); // 파라미터 이름을 seq_notice_no로 변경
        
        // seq_notice_no가 존재하지 않으면 삭제를 진행할 수 없음
        if (seq_notice_no != null) {
            try {
                // 삭제 확인을 위해 사용자에게 확인 메시지를 보여주는 경우
                if ("POST".equalsIgnoreCase(request.getMethod())) {
                    // NoticeDAO_imple 객체 생성
                    NoticeDAO_imple ndao = new NoticeDAO_imple();
                    
                    // 공지사항 삭제
                    int result = ndao.deleteNotice(Integer.parseInt(seq_notice_no));
                    
                    if (result == 1) {
                        // 삭제가 성공했으면 목록 페이지로 리다이렉트
                        super.setRedirect(true);
                        super.setViewPage(request.getContextPath() + "/notice/notice.mp");
                    } else {
                        // 삭제 실패 시 (예: 해당 공지사항이 존재하지 않는 경우)
                        request.setAttribute("message", "삭제할 공지사항이 없습니다.");
                        request.setAttribute("loc", request.getContextPath() + "/notice/noticeDetail.mp?seq=" + seq_notice_no); // 상세 페이지로 이동
                        super.setRedirect(false);
                        super.setViewPage("/WEB-INF/msg.jsp"); // 메시지 페이지
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 오류 발생 시 메시지 출력
                request.setAttribute("message", "공지사항 삭제 중 오류가 발생했습니다.");
                request.setAttribute("loc", request.getContextPath() + "/notice/notice.mp");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
            }
        } else {
            // seq_notice_no가 없으면 오류 메시지
            request.setAttribute("message", "삭제할 공지사항 번호가 없습니다.");
            request.setAttribute("loc", request.getContextPath() + "/notice/notice.mp");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
        }
    }
}
