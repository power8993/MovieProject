package notice.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeDTO;
import notice.model.*;

public class Notice extends AbstractController {

	private NoticeDAO ndao = new NoticeDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// DB에서 공지사항 목록을 가져옴
		// List<NoticeDTO> NoticeList = ndao.selectNotice();
		// 가져온 데이터를 request에 저장
		// request.setAttribute("NoticeList", NoticeList);
		HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String sizePerPage = "10";
		String currentShowPageNo = request.getParameter("currentShowPageNo"); // 내가 현재 보고자하는 페이지

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("sizePerPage", sizePerPage);
		paraMap.put("currentShowPageNo", currentShowPageNo); // 한페이지당 보여줄 행의 개수

		int totalPage = ndao.getTotalPage(paraMap);

		try {
			if (Integer.parseInt(currentShowPageNo) > totalPage || Integer.parseInt(currentShowPageNo) <= 0) {
				currentShowPageNo = "1";
				paraMap.put("currentShowPageNo", currentShowPageNo);
			}
		} catch (NumberFormatException e) {
			currentShowPageNo = "1";
			paraMap.put("currentShowPageNo", currentShowPageNo);
		}

		String pageBar = "";

		int blockSize = 10;
		// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.

		int loop = 1;
		// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.

		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== //
		int pageNo = ((Integer.parseInt(currentShowPageNo) - 1) / blockSize) * blockSize + 1;
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.

		// === !!! [맨처음][이전] 만들기 === *** //
		pageBar += "<li class='page-item'><a class='page-link' href='notice.mp?sizePerPage=" + sizePerPage
				+ "&currentShowPageNo=1'>[처음]</a></li>";

		if (pageNo != 1) {
			pageBar += "<li class='page-item'><a class='page-link' href='notice.mp?sizePerPage=" + sizePerPage
					+ "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
		}

		while (!(loop > blockSize || pageNo > totalPage)) {
			if (pageNo == Integer.parseInt(currentShowPageNo)) {
				pageBar += "<li class='page-item active'><a class='page-link' href='#'>" + pageNo + "</a></li>";
			} else {
				pageBar += "<li class='page-item'><a class='page-link' href='notice.mp?sizePerPage=" + sizePerPage
						+ "&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a></li>";
			}

			loop++; // 1 2 3 4 5 ...
			pageNo++; // 1 2 3 4 5 ...
		} // end of while

		// *** [다음][마지막] 만들기 *** //
		if (pageNo <= totalPage) { // 위의 while 문을 빠져나오면 pageNo 는 43으로 빠져나오기 때문에 <= 를 조건식으로 둔다.
			pageBar += "<li class='page-item'><a class='page-link' href='notice.mp?&sizePerPage=" + sizePerPage
					+ "&currentShowPageNo=" + pageNo + "'>[다음]</a></li>";
		}

		pageBar += "<li class='page-item'><a class='page-link' href='notice.mp?sizePerPage=" + sizePerPage
				+ "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";

		try {
			List<NoticeDTO> NoticeList = ndao.selectNotice(paraMap);

			request.setAttribute("NoticeList", NoticeList);

			request.setAttribute("sizePerPage", sizePerPage);

			request.setAttribute("pageBar", pageBar);

			
			int totalNoticeCount = ndao.getTotalNoticeCount(paraMap);

			request.setAttribute("totalNoticeCount", totalNoticeCount);
			request.setAttribute("currentShowPageNo", currentShowPageNo);
			
			request.setAttribute("loginuser", loginuser);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/notice/notice.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/error.mp");
		}
	}
}
