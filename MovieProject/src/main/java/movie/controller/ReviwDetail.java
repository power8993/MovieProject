package movie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import movie.domain.MovieReviewVO;
import movie.model.MovieDAO_imple_wonjae;
import movie.model.MovieDAO_wonjae;

public class ReviwDetail extends AbstractController {

    private MovieDAO_wonjae mdao = new MovieDAO_imple_wonjae();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        // 영화 번호 파라미터
        String seq_movie_no = request.getParameter("seq_movie_no");

        // POST 요청일 때만 처리
        if ("POST".equalsIgnoreCase(method)) {
            String sizePerPage = "5";  // 한 페이지에 표시할 리뷰 수
            String currentShowPageNo = request.getParameter("currentShowPageNo");  // 현재 페이지 번호
            
            // 파라미터 맵 설정
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("sizePerPage", sizePerPage);
            paraMap.put("currentShowPageNo", currentShowPageNo);

            int totalPage = mdao.getTotalPage(paraMap, Integer.parseInt(seq_movie_no)); // 총 페이지 수 구하기

            // 유효하지 않은 페이지 번호 처리
            try {
                if (Integer.parseInt(currentShowPageNo) > totalPage || Integer.parseInt(currentShowPageNo) <= 0) {
                    currentShowPageNo = "1"; // 페이지 번호가 잘못되었으면 1로 설정
                    paraMap.put("currentShowPageNo", currentShowPageNo);
                }
            } catch (NumberFormatException e) {
                currentShowPageNo = "1";  // 페이지 번호가 잘못되었으면 1로 설정
                paraMap.put("currentShowPageNo", currentShowPageNo);
            }

            // 페이지 바 생성
            String pageBar = "";
            
            int blockSize = 10;  // 한 블록당 표시할 페이지 번호 개수
            
            int loop = 1;
            
            int pageNo = ((Integer.parseInt(currentShowPageNo) - 1) / blockSize) * blockSize + 1;  // 첫 번째 페이지 번호

            // 맨 처음, 이전 버튼
            pageBar += "<li class='page-item'><a class='page-link' href='reviwDetail.mp?seq_movie_no=" + seq_movie_no + "&sizePerPage=" + sizePerPage
                    + "&currentShowPageNo=1'>[맨처음]</a></li>";

            if (pageNo != 1) {
                pageBar += "<li class='page-item'><a class='page-link' href='reviwDetail.mp?seq_movie_no=" + seq_movie_no + "&sizePerPage=" + sizePerPage
                        + "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
            }

            // 페이지 번호 루프
            while (!(loop > blockSize || pageNo > totalPage)) {
                if (pageNo == Integer.parseInt(currentShowPageNo)) {
                    pageBar += "<li class='page-item active'><a class='page-link' href='#' data-page='" + pageNo + "'>" + pageNo + "</a></li>";
                } else {
                	pageBar += "<li class='page-item'><a class='page-link' href='#' data-page='" + pageNo + "'>" + pageNo + "</a></li>";
                }
                loop++;
                pageNo++;
            }

            // 다음, 마지막 버튼
            if (pageNo <= totalPage) {
                pageBar += "<li class='page-item'><a class='page-link' href='#' data-page='" + pageNo + "'>[다음]</a></li>";
            }
            // 마지막 버튼 생성
            pageBar += "<li class='page-item'><a class='page-link' href='#' data-page='" + totalPage + "'>[마지막]</a></li>";

            // 리뷰 목록 가져오기
            List<MovieReviewVO> reviews = mdao.selectReview(paraMap, Integer.parseInt(seq_movie_no));  // 영화 번호로 리뷰 목록 조회

            // 리뷰 목록을 JSON 형식으로 변환
            JSONArray mrArray = new JSONArray();
            for (MovieReviewVO mvo : reviews) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("userid", mvo.getFk_user_id());
                jsonObj.put("movie_rating", mvo.getMovie_rating());
                jsonObj.put("review_content", mvo.getReview_content());
                jsonObj.put("review_write_date", mvo.getReview_write_date());

                mrArray.put(jsonObj);
            }

            // JSON 응답 객체 생성
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("mrList", mrArray);  // 리뷰 목록
            
            jsonObj.put("sizePerPage", sizePerPage);
            
            jsonObj.put("pageBar", pageBar);  // 페이지 바

            jsonObj.put("currentShowPageNo", currentShowPageNo);  // 현재 페이지 번호

            // JSON 응답 문자열로 변환
            String json = jsonObj.toString();
            request.setAttribute("json", json);

            // JSON 뷰 페이지로 리디렉션
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
        } else {
            // 잘못된 접근 처리
            String message = "잘못된 접근입니다.";
            String loc = "javascript:history.back()";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
        }
    }
}
