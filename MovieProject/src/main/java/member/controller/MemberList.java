package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO_hongbi;
import member.model.MemberDAO_imple_hongbi;
import member.domain.*;

public class MemberList extends AbstractController {
	
	private MemberDAO_hongbi mdao = new MemberDAO_imple_hongbi();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String search_type = request.getParameter("search_type");
		String search_word = request.getParameter("search_word");	
		String size_per_page = request.getParameter("size_per_page");
		String current_showpage_no = request.getParameter("current_showpage_no"); // 현재 내가 보고자하는 페이지
		
		// ===== 입력하지 않았을 경우에 대한 NULL 처리 ===== //
		if(search_type == null ||
		   (!"user_id".equals(search_type)) && (!"email".equals(search_type))) {	
			search_type = "";
		}
		
		if(search_word == null) {
			search_word = "";
		}
		else {
			search_word = search_word.toLowerCase();	// 대소문자 구분 x
		}

		if(size_per_page == null||
		  (!"10".equals(size_per_page)) && (!"15".equals(size_per_page)) && (!"20".equals(size_per_page))) {
			size_per_page = "10";
		}
		
		if(current_showpage_no == null) {
			current_showpage_no = "1";
		}
		
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("search_type", search_type);
		paraMap.put("search_word", search_word);
		paraMap.put("size_per_page", size_per_page); 			  // 한 페이지당 보여줄 행의 개수
		paraMap.put("current_showpage_no", current_showpage_no);  // 현재 내가 보고자하는 페이지
		
		
		// **** 페이징 처리를 한 모든 회원 목록 또는 검색되어진 회원 목록 보여주기 **** //
		
		// 페이징 처리를 위한 검색이 있는 또는 검색이 없는 회원에 대한 총페이지수 알아오기 //
		int total_page = mdao.getTotalMemberPage(paraMap);
		//System.out.println("~~~~ 확인용 total_page = > " + total_page);
		
		// 총 페이지수가 아닌 페이지를 GET 방식으로 접근하려고 할 때의 처리
		try {
			
			if(Integer.parseInt(current_showpage_no) > total_page ||
			   Integer.parseInt(current_showpage_no) <= 0) {
				current_showpage_no = "1";
				paraMap.put("current_showpage_no", current_showpage_no);
			}
			
		} catch (NumberFormatException e) {
			current_showpage_no = "1";
			paraMap.put("current_showpage_no", current_showpage_no);
		}
		
		
		// 페이지바 만들기
		String page_bar = "";
		int block_size = 10;   // 한 번에 보여줄 수 있는 페이지바의 길이
		
		int loop = 1;
		
		// page_no 구하는 공식
		int page_no = ( (Integer.parseInt(current_showpage_no) - 1)/block_size ) * block_size + 1; 
		
		// [맨처음][이전] 만들기
		page_bar += "<li class='page-item'><a class='page-link' href='memberList.mp?search_type="+search_type+"&search_word="+search_word+"&size_per_page="+size_per_page+"&current_showpage_no=1'>[맨처음]</a></li>";
		
		if(page_no != 1) {
			page_bar += "<li class='page-item'><a class='page-link' href='memberList.mp?search_type="+search_type+"&search_word="+search_word+"&size_per_page="+size_per_page+"&current_showpage_no="+(page_no-1)+"'>[맨처음]</a></li>";
        }
		
		while( !(loop > block_size || page_no > total_page)) {
	       	if(page_no == Integer.parseInt(current_showpage_no)) {
	       		page_bar += "<li class='page-item active'><a class='page-link' href='#'>"+page_no+"</a></li>";
	       	}
	       	else {
	       		page_bar += "<li class='page-item'><a class='page-link' href='memberList.mp?search_type="+search_type+"&search_word="+search_word+"&size_per_page="+size_per_page+"&current_showpage_no="+page_no+"'>"+page_no+"</a></li>";
	       	}
	       	loop++;
	       	
	       	page_no++; 
		}// end of while( !(loop > blockSize || pageNo > totalPage))---------------------------------------
       
		// [다음][마지막] 만들기
		if(page_no <= total_page) {	
			page_bar += "<li class='page-item'><a class='page-link' href='memberList.mp?search_type="+search_type+"&search_word="+search_word+"&size_per_page="+size_per_page+"&current_showpage_no="+page_no+"'>[다음]</a></li>";
        }
        
		page_bar += "<li class='page-item'><a class='page-link' href='memberList.mp?search_type="+search_type+"&search_word="+search_word+"&size_per_page="+size_per_page+"&current_showpage_no="+total_page+"'>[마지막]</a></li>";
		
		try {
			// 페이징 처리를 한 모든 회원 리스트 보여주기 (select)
			List<MemberVO> memberList = mdao.selectMemberListPaging(paraMap);
			
			request.setAttribute("memberList", memberList);
			
			request.setAttribute("search_type", search_type);
			request.setAttribute("search_word", search_word);
			
			request.setAttribute("size_per_page", size_per_page);
			request.setAttribute("page_bar", page_bar);
			
			/* >>> 뷰단(movieRegisteredList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작  <<< */
			int total_member_count = mdao.getTotalMemberCount(paraMap);
			// System.out.println("확인용 total_movie_count : " + total_movie_count);
			
			request.setAttribute("total_member_count", total_member_count);
			request.setAttribute("current_showpage_no", current_showpage_no);
			
			/* >>> 뷰단(movieRegisteredList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 끝  <<< */
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/admin/memberList.jsp");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/error.mp");
		}
		
		

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}--------------------

}
