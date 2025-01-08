<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
    // 이미지 미리보기
    $(document).on("change", "input.img_file", function(e) {
        const input_file = $(e.target).get(0);
        const file = input_file.files[0];
        
        // 파일이 선택되지 않으면 미리보기 초기화
        if (!file) {
            document.getElementById("previewImg").src = "";
            return;
        }

        // 파일 크기 체크 (MB 단위로 표시)
        const fileSize = file.size / 1024 / 1024; // MB
        if (!(file.type === 'image/jpeg' || file.type === 'image/png')) {
            alert("jpg 또는 png 파일만 가능합니다.");
            $(this).val("");  // 파일 입력값 초기화
            document.getElementById("previewImg").src = "";
            return;
        } else if (fileSize > 3) {  // 3MB 초과
            alert("3MB 이상인 파일은 업로드할 수 없습니다.");
            $(this).val("");  // 파일 입력값 초기화
            document.getElementById("previewImg").src = "";
            return;
        }

        const fileReader = new FileReader();
        fileReader.onload = function() {
            // 이미지 미리보기
            document.getElementById("previewImg").src = fileReader.result;
        };

        fileReader.readAsDataURL(file);  // 파일을 DataURL로 읽어 미리보기로 표시
    });

    // 폼 제출 시 Ajax 요청
    $("#btnUpdateProfile").click(function() {
        const formData = new FormData($("form[name='profileFrm']")[0]);

        $.ajax({
            url: "${pageContext.request.contextPath}/mypage/mypageProfileEdit.mp",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function(json) {
                if (json.result == 1) {
                    // 성공 후 리다이렉트
                    location.href = "${pageContext.request.contextPath}/mypage/mypage.mp";
                }
            },
            error: function(request, status, error) {
                // 서버 에러 처리 시 더 구체적인 메시지 제공
                alert("서버 에러: " + error + " - 첨부된 이미지가 3MB를 초과하여 프로필 등록에 실패했습니다.");
            }
        });
    });

    // 취소 버튼 클릭 시 초기화
    $("input[type='reset']").click(function() {
        $("img#previewImg").attr("src", "");  // 미리보기 이미지 초기화
    });
</script>

<form name="profileFrm" enctype="multipart/form-data">
    <table id="tblProfileInput" style="width: 80%;">
        <tbody>
            <tr>
                <td width="25%" class="prodInputName">프로필 사진</td>
                <td width="75%" align="left" style="border-top: hidden; border-bottom: hidden;">
                    <tr>
                        <td>
                            <!-- 이미지 미리보기를 동그란 형태로 표시 -->
                            <img id="previewImg" width="150" height="150" style="border-radius: 50%; object-fit: cover;" />
                        </td>
                    </tr>

                    <tr>    
                        <td width="75%" align="left" style="border-top: hidden; border-bottom: hidden;">
                            <input type="file" name="profile" class="infoData img_file" accept='image/*' />
                            <span class="error">필수입력</span>
                        </td>
                    </tr>

            <tr>
                <td colspan="2" align="center" style="padding: 50px 0;">
                    <input type="button" value="프로필 사진 수정" id="btnUpdateProfile" style="width: 120px;" class="btn btn-info btn-lg mr-5" />
                    <input type="reset" value="취소" style="width: 120px;" class="btn btn-danger btn-lg" />
                </td>
            </tr>
        </tbody>
    </table>
</form>
