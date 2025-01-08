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
		String range = request.getParameter("range");  // 'range' 값 처리

	    try {
	        JSONArray jsonArray = new JSONArray();
	        
	        // Pie 차트 처리
	        if ("pie".equals(data)) {
	            List<MemberVO_hongbi> reservedList = mdao.todayReservedChart();
	            for (MemberVO_hongbi reserved : reservedList) {
	                JSONObject jsonObj = new JSONObject();
	                jsonObj.put("reserved_cnt", reserved.getReserved_cnt());
	                jsonObj.put("movie_title", reserved.getMvvo().getMovie_title());
	                jsonArray.put(jsonObj);
	            }
	        }
	        // Line 차트 처리
	        else if ("line".equals(data)) {
	            if ("day".equals(range)) {
	                List<Map<String, Object>> reservedDayList = mdao.totalDayReservedChart();
	                for (Map<String, Object> reserved : reservedDayList) {
	                    JSONObject jsonObj = new JSONObject();
	                    jsonObj.put("day_pay_date", reserved.get("day_pay_date"));
	                    jsonObj.put("day_pay_sum", reserved.get("day_pay_sum"));
	                    jsonObj.put("day_reserved_cnt", reserved.get("day_reserved_cnt"));
	                    jsonArray.put(jsonObj);
	                }
	            } else if ("month".equals(range)) {
	                List<Map<String, Object>> reservedMonthList = mdao.totalMonthReservedChart();
	                for (Map<String, Object> reserved : reservedMonthList) {
	                    JSONObject jsonObj = new JSONObject();
	                    jsonObj.put("month_pay_date", reserved.get("month_pay_date"));
	                    jsonObj.put("month_pay_sum", reserved.get("month_pay_sum"));
	                    jsonObj.put("month_reserved_cnt", reserved.get("month_reserved_cnt"));
	                    jsonArray.put(jsonObj);
	                }
	            }
	            // Year 차트 처리
	            else if ("year".equals(range)) {
	                List<Map<String, Object>> reservedYearList = mdao.totalYearReservedChart();
	                for (Map<String, Object> reserved : reservedYearList) {
	                    JSONObject jsonObj = new JSONObject();
	                    jsonObj.put("year_pay_date", reserved.get("year_pay_date"));
	                    jsonObj.put("year_pay_sum", reserved.get("year_pay_sum"));
	                    jsonObj.put("year_reserved_cnt", reserved.get("year_reserved_cnt"));
	                    jsonArray.put(jsonObj);
	                }
	            }
	        }
	        // Area 차트 처리
	        else if ("area".equals(data)) {
	            List<Map<String, Object>> visitedCntList = mdao.totalVisitedCountChart();
	            for (Map<String, Object> visited : visitedCntList) {
	                JSONObject jsonObj = new JSONObject();
	                jsonObj.put("visit_date", visited.get("visit_date"));
	                jsonObj.put("visited_cnt", visited.get("visited_cnt"));
	                jsonArray.put(jsonObj);
	            }
	        }
	        // Bar 차트 처리
	        else if ("bar".equals(data)) {
	            List<Map<String, Object>> total_payment_amount = mdao.totalDayReservedChart();
	            for (Map<String, Object> payment : total_payment_amount) {
	                JSONObject jsonObj = new JSONObject();
	                jsonObj.put("pay_date", payment.get("day_pay_date"));
	                jsonObj.put("pay_sum", payment.get("day_pay_sum"));
	                jsonArray.put(jsonObj);
	            }
	        }
	        else if ("statistics".equals(data)) {
	        	// [관리자 메인페이지] 금일 및 누적 매출과 방문자 수 보여주기(select)
	        	Map<String, Integer> map = mdao.statisticalAnalysis();
	        	
	        	if(map != null) {
					JSONObject jsonObj = new JSONObject();

					// SONObject의 mvvo 속성으로 포함되어 전달
					jsonObj.put("today_visit_cnt", map.get("today_visit_cnt"));
					jsonObj.put("total_visit_cnt", map.get("total_visit_cnt"));
					jsonObj.put("today_pay_sum", map.get("today_pay_sum"));
					jsonObj.put("total_pay_sum", map.get("total_pay_sum"));

					response.setContentType("application/json"); // json 형태로 보내준다.
					response.getWriter().write(jsonObj.toString());
					
					return;  // 응답을 보낸 후 메소드 종료(map 으로 보낼 것이기 때문에)
				}
	        	
	        }

	        // 결과를 클라이언트로 반환
	        response.setContentType("application/json");
	        response.getWriter().write(jsonArray.toString());
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        String message = "데이터 조회 중 오류가 발생했습니다.";
	        String loc = "javascript:history.back()";
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/msg.jsp");
	    }
	}
}
