package mypage.controller;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import mypage.model.MypageDAO;
import mypage.model.MypageDAO_imple;
import org.json.JSONObject;

import common.controller.AbstractController;

public class MypageProfileEdit extends AbstractController {

    MypageDAO mydao = new MypageDAO_imple();

    private String extractFileName(String partHeader) {
        for (String cd : partHeader.split("\\;")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
                int index = fileName.lastIndexOf(File.separator);
                return fileName.substring(index + 1);
            }
        }
        return null;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

        if (loginuser == null) {
            request.setAttribute("message", "로그인이 필요합니다.");
            request.setAttribute("loc", request.getContextPath() + "/login/login.mp");
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }

        String userid = loginuser.getUserid();
        String method = request.getMethod();

        if (!"POST".equalsIgnoreCase(method)) {
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/mypage/mypageProfileEdit.jsp");
        } else {
            // 파일 업로드 처리
            ServletContext svlCtx = session.getServletContext();
            String uploadFileDir = svlCtx.getRealPath("/Profile_image");
            //System.out.println("=== 첨부되어지는 이미지 파일이 올라가는 절대경로 uploadFileDir ==> " + uploadFileDir);
            //==> C:\NCS\workspace_jsp\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MovieProject\Profile_image

            String profile = null;
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getHeader("Content-Disposition").contains("filename=")) {
                    String fileName = extractFileName(part.getHeader("Content-Disposition"));

                    if (part.getSize() > 0) {
                        // 파일명 생성 (UUID 사용)
                        String newFilename = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                        
                        // 업로드된 파일 저장
                        part.write(uploadFileDir + File.separator + newFilename);
                        part.delete(); // 파일 업로드 후 파트 삭제
                        
                        if ("profile".equals(part.getName())) {
                            profile = newFilename; // 프로필 이미지 파일명 저장
                        }
                    }
                }
            }

            // 프로필 이미지가 존재하면 DB 업데이트
            if (profile != null) {
                int result = mydao.profileUpdate(userid, profile); // 프로필 이미지 DB 업데이트
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("result", result);
                
                String json = jsonObj.toString(); // 문자열로 변환 
                request.setAttribute("json", json);
            }

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
        }
    }
}
