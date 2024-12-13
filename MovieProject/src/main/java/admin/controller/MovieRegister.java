package admin.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MovieRegister extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equalsIgnoreCase(method)) { // [영화등록] 버튼 클릭 후 폼태그 보이기
			
			super.setViewPage("/WEB-INF/admin/movieRegister.jsp");
			
		}

	}

}
