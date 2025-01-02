package mypage.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import movie.domain.MovieReviewVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class Mymoviereview extends AbstractController {

   MypageDAO mydao = new MypageDAO_imple();

   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (super.checkLogin(request)) {
         HttpSession session = request.getSession();
         MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

         if (loginuser == null) {
            request.setAttribute("message", "로그인 세션이 만료되었습니다.");
            request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
         }

         String userid = loginuser.getUserid();
            
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("userid", userid);

            try {
            List<MovieReviewVO> mymoviereviewList = mydao.mymoviereviewList(paraMap);
               request.setAttribute("mymoviereviewList", mymoviereviewList);
               
               super.setRedirect(false);
               super.setViewPage("/WEB-INF/mypage/mymoviereview.jsp");
               
            }catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(request.getContextPath()+"/error.up");
			}
      } else {
         request.setAttribute("message", "로그인이 필요합니다.");
         request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
         super.setViewPage("/WEB-INF/msg.jsp");
      }
   }
}