package mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;


public class MyupcheckPwd extends AbstractController {

    private MypageDAO mydao = new MypageDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

        if (loginuser == null) {
            // 로그인이 되어 있지 않다면 로그인 페이지로 리다이렉트
            request.setAttribute("message", "로그인이 필요합니다.");
            request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }

		// ★★★ 마이 프로필--나의 포인트 목록 ★★★ //
		List<Map<String, Object>> myreservationprofile = mydao.myreservationprofile(loginuser.getUserid());
		request.setAttribute("myreservationprofile", myreservationprofile);
		
		// ★★★ 마이 프로필--나의 영화 랭킹 ★★★ //
		List<Map<String, String>> myranking = mydao.myranking(loginuser.getUserid());
		request.setAttribute("myranking", myranking);
        
        String method = request.getMethod(); // "GET" 또는 "POST"

        if ("POST".equalsIgnoreCase(method)) {
            String pwd = request.getParameter("pwd");

            // 세션에서 사용자 ID 가져오기
            String userid = loginuser.getUserid();

            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("userid", userid); // 세션에서 가져온 사용자 ID
            paraMap.put("pwd", pwd);

            boolean checkPassword = mydao.checkPassword(paraMap); // 비밀번호 검증 메서드

            if (checkPassword) {
                // 비밀번호가 일치하면 회원정보 수정 페이지로 이동
                super.setRedirect(true);
                super.setViewPage(request.getContextPath() + "/mypage/myupEdit.mp"); // 회원정보 수정 페이지
            } else {
                // 비밀번호가 일치하지 않을 경우
                request.setAttribute("message", "비밀번호가 일치하지 않습니다.");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/mypage/myupcheckPwd.jsp");
            }
        } else {
            // GET 요청 처리 (비밀번호 입력 페이지로 이동)
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/mypage/myupcheckPwd.jsp");
        }
    }
}
