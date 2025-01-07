package member.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO_hongbi;
import member.model.MemberDAO_imple_hongbi;
import movie.domain.MovieVO;
import member.domain.MemberVO;
import member.domain.MemberVO_hongbi;

public class MemberSelectOneDetail extends AbstractController {
	
	private MemberDAO_hongbi mdao = new MemberDAO_imple_hongbi();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
				// 관리자(admin)으로 로그인 했을 경우
			
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method)) {
				
				String user_id = request.getParameter("userid");	// 선택한 회원의 user_id 를 가져온다.
	
				try {
					// 회원 목록을 보여주는 페이지에서 회원 클릭 시, 해당 회원 상세 정보 보여주기(select)
					MemberVO_hongbi mvo = mdao.selectMemberDetail(user_id);
					
					if(mvo != null) {
						JSONObject jsonObj = new JSONObject();
	
						// SONObject의 mvvo 속성으로 포함되어 전달
						jsonObj.put("user_id", mvo.getUserid());
						jsonObj.put("name", mvo.getName());
						jsonObj.put("mobile", mvo.getMobile());
						jsonObj.put("email", mvo.getEmail());
						jsonObj.put("reserved_cnt", mvo.getReserved_cnt());
						jsonObj.put("pay_sum", mvo.getPay_sum());
						jsonObj.put("point_sum", mvo.getPoint_sum());
						
						response.setContentType("application/json"); // json 형태로 보내준다.
						response.getWriter().write(jsonObj.toString());
						
	//					HttpSession session = request.getSession();
	//					session.setAttribute("seq_movie_no", mvvo.getSeq_movie_no());
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					
					String message = "회원 상세 정보 보기 실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
					
				}
				
			}// end of if ("POST".equalsIgnoreCase(method)) {}---------------------------------------------
			else if("GET".equalsIgnoreCase(method)) {
				
				String user_id = request.getParameter("userid");	// 선택한 회원의 user_id 를 가져온다.
				
				try {
					// 회원 목록을 보여주는 페이지에서 회원 클릭 시, 해당 회원의 일주일 간 예매내역 차트로 보여주기(select)
					List<MemberVO_hongbi> reservedList = mdao.selectMemberReservedWeek(user_id);
					
					// movieList 를 JOSN 배열로 변환
					JSONArray reservedArray = new JSONArray();
					
					for (MemberVO_hongbi reserved : reservedList) {

						JSONObject jsonObj = new JSONObject();
						jsonObj.put("pay_date", reserved.getPaydate());
						jsonObj.put("pay_sum", reserved.getPay_sum());
						jsonObj.put("reserved_cnt", reserved.getReserved_cnt()); 
						
						reservedArray.put(jsonObj);
					}// end of for-------------------------------
					
					// JSONArray 객체를 json 으로 변환 후 보내기
					response.setContentType("application/json"); // json 형태로 보내준다.
					response.getWriter().write(reservedArray.toString());
					
				} catch (SQLException e) {
					e.printStackTrace();
					
					String message = "회원의 일주일 간 예매내역 조회 실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
					
				}
			}
			
		}// end of if(loginuser != null && "admin".equalsIgnoreCase(loginuser.getUserid())) {}---------------------------------
		else {
			// 일반사용자로 로그인 한 경우
			request.setAttribute("message", "관리자만 접근이 가능합니다.");
			request.setAttribute("loc", request.getContextPath() + "/index.mp");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}-------------------

}
