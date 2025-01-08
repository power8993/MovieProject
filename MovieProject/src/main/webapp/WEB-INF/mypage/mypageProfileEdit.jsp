<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<form name="profileFrm" enctype="multipart/form-data">
    <table id="tblProfileInput" style="width: 80%;">
        <tbody>
            <!-- 프로필 사진 입력란 -->
            <tr>
                <td width="25%" class="prodInputName">프로필 사진</td>
                <td width="75%" align="left" style="border-top: hidden; border-bottom: hidden;">
                    <!-- 디폴트 이미지(서버에서 받아온 값 사용) -->
                    <img id="previewImg" src="/path/to/defaultProfileImage.jpg" style="width: 150px; height: 150px; border-radius: 50%;" alt="프로필 이미지 미리보기" />
                    <br><br>
                    <!-- 파일 선택 버튼 -->
                    <input type="file" name="profileImage" class="infoData img_file" accept="image/*" />
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center" style="padding: 50px 0;">
                    <input type="button" value="프로필 사진 수정" id="btnUpdateProfile" style="width: 120px;" class="btn btn-info btn-lg mr-5" />
                    <button type="button" id="cancelBtn">취소</button>
                </td>
            </tr>
        </tbody>
    </table>
</form>

<script>
$(document).ready(function() {
    // 1. 기본 디폴트 이미지 로드 (서버에서 불러온 이미지 경로 사용)
    var defaultImagePath = "/path/to/defaultProfileImage.jpg";  // 서버에서 받아온 디폴트 이미지 경로로 수정
    $("#previewImg").attr("src", defaultImagePath);  // 초기 디폴트 이미지 표시

    // 2. 파일 선택 시 미리보기 이미지 변경
    $(document).on("change", "input.img_file", function(e) {
        const input_file = $(e.target).get(0);
        const file = input_file.files[0];

        if (file) {
            const fileReader = new FileReader();
            
            // 이미지 미리보기
            fileReader.onload = function() {
                document.getElementById("previewImg").src = fileReader.result;  // 선택된 이미지로 미리보기 이미지 변경
            };

            fileReader.readAsDataURL(file);  // 파일을 DataURL로 읽어 미리보기로 표시
        }
    });

    // 3. 프로필 사진 수정 버튼 클릭 시
    $("input#btnUpdateProfile").click(function() {
        $("span.error").hide();  // 오류 메시지 숨기기

        // 파일이 선택되지 않았을 경우 에러 처리
        const fileInput = $("input[name='profileImage']");
        if (fileInput[0].files.length === 0) {
            fileInput.next().show();  // "필수입력" 오류 메시지 표시
            return;
        }

        // 폼 데이터 준비
        var formData = new FormData($("form[name='profileFrm']").get(0));

        // AJAX로 프로필 사진을 서버로 전송
        $.ajax({
            url: "/path/to/uploadProfileImage",  // 실제 서버 경로로 변경
            type: "post",
            data: formData,
            processData: false,  // 파일 전송 시 설정
            contentType: false,  // 파일 전송 시 설정
            dataType: "json",
            success: function(json) {
                if (json.result === 1) {
                    alert("프로필 사진이 수정되었습니다.");
                    location.reload();  // 페이지 새로고침하여 프로필 사진 업데이트
                } else {
                    alert("프로필 사진 수정에 실패했습니다.");
                }
            },
            error: function(request, status, error) {
                alert("파일 업로드 실패: " + error);
            }
        });
    });

    // 취소 버튼 클릭 시 폼 초기화
    $("input[type='reset']").click(function() {
        $("span.error").hide();
        $("input[name='profileImage']").val("");  // 파일 입력 초기화
        $("img#previewImg").attr("src", defaultImagePath);  // 디폴트 이미지로 되돌리기
    });
    document.getElementById("cancelBtn").addEventListener("click", function() {
        // 부모 창에서 팝업을 닫는 함수 호출
        if (window.opener && window.opener.popupWindow) {
            window.opener.popupWindow.close();
        }
        window.close();  // 현재 팝업도 닫기
    });
});
</script>
