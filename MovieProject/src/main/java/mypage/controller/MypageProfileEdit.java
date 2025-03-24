package mypage.controller;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;

public class MypageProfileEdit extends AbstractController {

    private MypageDAO mydao = new MypageDAO_imple();

    private String extractFileName(String partHeader) {
        for (String cd : partHeader.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf(File.separator) + 1);
            }
        }
        return null;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

        String userid = loginuser.getUserid();
        String method = request.getMethod();

        if (!"POST".equalsIgnoreCase(method)) {
            // GET 요청: 프로필 수정 페이지로 이동
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/mypage/mypageProfileEdit.jsp");
            return;
        }

        // POST 요청: 파일 업로드 처리
        ServletContext svlCtx = session.getServletContext();
        String uploadFileDir = svlCtx.getRealPath("/Profile_image");

        // 디렉토리 생성 여부 확인
        File dir = new File(uploadFileDir);
        if (!dir.exists()) dir.mkdirs();

        String profile = null; // 업로드된 프로필 파일명

        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            if (part.getHeader("Content-Disposition").contains("filename=")) {
                String fileName = extractFileName(part.getHeader("Content-Disposition"));

                if (part.getSize() > 0) {
                    // 업로드된 파일명을 고유하게 생성
                    String newFilename = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));

                    // 파일 저장
                    part.write(uploadFileDir + File.separator + newFilename);
                    part.delete(); // 임시 저장된 파일 삭제

                    if ("profile".equals(part.getName())) {
                        profile = newFilename; // 프로필 이미지 파일명 저장
                    }
                }
                
            }
        }

        int result = 0;
        if (profile != null) {
            result = mydao.profileUpdate(userid, profile);
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", result);

        String json = jsonObj.toString();
        request.setAttribute("json", json);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/jsonview.jsp");
    }
}
