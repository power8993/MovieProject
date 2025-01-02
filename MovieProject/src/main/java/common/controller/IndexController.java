package common.controller;

import java.sql.SQLException;
import java.util.List;

import common.model.indexDAO;
import common.model.indexDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import myshop.domain.ImageVO;
//import myshop.model.ProductDAO;
//import myshop.model.ProductDAO_imple;
import notice.domain.NoticeDTO;

public class IndexController extends AbstractController {
	
	private indexDAO idao = new indexDAO_imple();
//	
//	public IndexController() {
//		pdao = new ProductDAO_imple();
//	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	try {
			
			List<NoticeDTO> noticeList = idao.NoticeSelectTopThree(); // 공지사항 최신글 3개 조회하기
			
			request.setAttribute("noticeList", noticeList);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/index.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/error.mp");
		}
	}

}
