package movie.controller;

import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.model.MovieDAO_imple_wonjae;
import movie.model.MovieDAO_wonjae;

public class MovieAge extends AbstractController {

	private MovieDAO_wonjae mdao = new MovieDAO_imple_wonjae();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 영화 번호 파라미터
        String seq_movie_no = request.getParameter("seq_movie_no");
		
		// 서버에서 성별 예매 분포 데이터를 가져오기
		Map<String, Integer> age = mdao.getAge(Integer.parseInt(seq_movie_no));

		JSONObject jsobj = new JSONObject();
		
		jsobj.put("age", age);
		
		String json = jsobj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");

	}

}
