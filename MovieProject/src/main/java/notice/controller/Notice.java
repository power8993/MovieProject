package notice.controller;

import java.sql.SQLException;
import java.util.List;
import common.controller.AbstractController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.domain.NoticeDTO;
import notice.model.*;

public class Notice extends AbstractController {

    private NoticeDAO ndao = new NoticeDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        String pathname = "";
        try {
            // DB에서 공지사항 목록을 가져옴
            List<NoticeDTO> NoticeList = ndao.selectNotice();
            // 가져온 데이터를 request에 저장
            request.setAttribute("NoticeList", NoticeList);
            
            // 공지사항 목록을 보여줄 JSP 페이지 경로
            pathname = "/WEB-INF/notice/notice.jsp"; 
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 발생 시 오류 페이지
            request.setAttribute("errorMessage", "공지사항 오류 발생");
            pathname = ""; // 오류 페이지 경로
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(pathname);
        dispatcher.forward(request, response);
    }
}
