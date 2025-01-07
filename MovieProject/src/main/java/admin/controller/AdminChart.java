package admin.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO_hongbi;
import member.model.MemberDAO_hongbi;
import member.model.MemberDAO_imple_hongbi;

public class AdminChart extends AbstractController {

	private MemberDAO_hongbi mdao = new MemberDAO_imple_hongbi();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String data = request.getParameter("data");

		//System.out.println("Pie Chart: " + pie_chart);
		//System.out.println("Line Chart: " + line_chart);
		
		
		if("pie".equals(data)) {
			// === 오늘의 영화 예매 현황 === //

			try {
				// [관리자 메인페이지] 오늘의 영화 예매 현황을 차트로 보여주기(select)
				List<MemberVO_hongbi> reservedList = mdao.todayReservedChart();
				
				// movieList 를 JOSN 배열로 변환
				JSONArray reservedArray = new JSONArray();
				
				for (MemberVO_hongbi reserved : reservedList) {

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("reserved_cnt", reserved.getReserved_cnt());
					jsonObj.put("movie_title", reserved.getMvvo().getMovie_title());
					
					reservedArray.put(jsonObj);
				}// end of for-------------------------------
				
				// JSONArray 객체를 json 으로 변환 후 보내기
				response.setContentType("application/json"); // json 형태로 보내준다.
				response.getWriter().write(reservedArray.toString());
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				String message = "오늘의 영화 예매 현황 조회 실패";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");	
			}
			
		}
		else if("line".equals(data)) {
			// === 전체 영화 예매 추이 === //
			
			String range = request.getParameter("range");

			try {
				
				// movieList 를 JOSN 배열로 변환
				JSONArray reservedArray = new JSONArray();
				
				if("day".equals(range)) {
					// [관리자 메인페이지] 일주일간 전체 예매 현황을 차트로 보여주기(select)
					List<Map<String,Object>> reservedDayList = mdao.totalDayReservedChart();
					
					// Day 데이터 처리
				    for (Map<String, Object> reserved : reservedDayList) {
				        JSONObject jsonObj = new JSONObject();
				        jsonObj.put("day_pay_date", reserved.get("day_pay_date"));
				        jsonObj.put("day_pay_sum", reserved.get("day_pay_sum"));
				        jsonObj.put("day_reserved_cnt", reserved.get("day_reserved_cnt"));
				        reservedArray.put(jsonObj);  // Day 데이터 추가
				    }// end of for---------------------------------------------------------
				    
				}
				else if("month".equals(range)) {
					// [관리자 메인페이지] 한 달간 전체 예매 현황을 차트로 보여주기(select)
					List<Map<String,Object>> reservedMonthList = mdao.totalMonthReservedChart();
					
					 // Month 데이터 처리
				    for (Map<String, Object> reserved : reservedMonthList) {
				        JSONObject jsonObj = new JSONObject();
				        jsonObj.put("month_pay_date", reserved.get("month_pay_date"));
				        jsonObj.put("month_pay_sum", reserved.get("month_pay_sum"));
				        jsonObj.put("month_reserved_cnt", reserved.get("month_reserved_cnt"));
				        reservedArray.put(jsonObj);  // Month 데이터 추가
				    }// end of for---------------------------------------------------------
				    
				}
				else if("year".equals(range)) {
					// [관리자 메인페이지] 연간 전체 예매 현황을 차트로 보여주기(select)
					List<Map<String,Object>> reservedYearList = mdao.totalYearReservedChart();

				    // Year 데이터 처리
				    for (Map<String, Object> reserved : reservedYearList) {
				        JSONObject jsonObj = new JSONObject();
				        jsonObj.put("year_pay_date", reserved.get("year_pay_date"));
				        jsonObj.put("year_pay_sum", reserved.get("year_pay_sum"));
				        jsonObj.put("year_reserved_cnt", reserved.get("year_reserved_cnt"));
				        reservedArray.put(jsonObj);  // Year 데이터 추가
				    }// end of for---------------------------------------------------------
					
				}
			    			
				// JSONArray 객체를 json 으로 변환 후 보내기
				response.setContentType("application/json"); // json 형태로 보내준다.
				response.getWriter().write(reservedArray.toString());
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				String message = "일주일 간 전체 예매 현황 조회 실패";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");	
			}
		}
		//else if("area".equals(data)) {
		//	// === 방문자 수 추이 === // 
		//}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}---------------------

}
