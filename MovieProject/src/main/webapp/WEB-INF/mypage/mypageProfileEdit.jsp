<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style type="text/css">

#tblProfileInput {
    width: 100%;
    table-layout: fixed;
    margin: 0 auto;
}


#previewimg {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    object-fit: cover;
    border: 2px solid #ccc;
    margin: 20px 0;
}


input[type="file"] {
    display: inline-block;
    margin: 10px 0;
}


.btn {
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    text-align: center;
    margin: 10px 5px; 
}



.error {
    color: red;
    font-size: 12px;
    display: block;
    margin-top: 5px;
}

.tbody{
margin: 10px 0;

}


/* 테이블 셀 내용 가운데 정렬 */
#tblProfileInput tr td {
    text-align: center; /* 텍스트와 이미지를 가운데 정렬 */
}


</style>


<script type="text/javascript">
    // 이미지 미리보기
    $(document).on("change", "input.img_file", function(e) {
        const inputFile = $(e.target).get(0);
        const file = inputFile.files[0];
        
        if (!file) {
            $("#previewimg").attr("src", ""); // 초기화
            return;
        }

        // 파일 타입 및 크기 검사
        const fileType = file.type;
        const fileSize = file.size / 1024 / 1024; // MB 단위
        if (!fileType.match("image/(jpeg|png|jpg)")) {
            alert("jpg 또는 png 형식의 이미지만 업로드 가능합니다.");
            $(this).val(""); // 입력값 초기화
            $("#previewimg").attr("src", ""); // 초기화
            return;
        }
        if (fileSize > 3) {
            alert("파일 크기는 3MB 이하로 업로드해야 합니다.");
            $(this).val(""); // 입력값 초기화
            $("#previewimg").attr("src", ""); // 초기화
            return;
        }

        // 이미지 미리보기 처리
        const fileReader = new FileReader();
        fileReader.onload = function(event) {
            $("#previewimg").attr("src", event.target.result);
        };
        fileReader.readAsDataURL(file);
    });

    // 프로필 업데이트 요청
    $(document).ready(function() {
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
                        alert("프로필 사진이 성공적으로 업데이트되었습니다.");
                        window.opener.location.reload(); //부모 창 업데이트
                        window.close(); //팝업창 닫아줌
                    } else {
                        alert("프로필 업데이트에 실패했습니다.");
                    }
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });
        });
    });

    // 취소 버튼 클릭 시 미리보기 초기화
    $("input[type='reset']").click(function() {
        $("#previewImg").attr("src", "");
    });
</script>

<form name="profileFrm" enctype="multipart/form-data">
    <table id="tblProfileInput" style="width: 50%;">
        <tbody>
        
            	<tr>
                <td class="prodInputName">프로필 사진</td>
                 </tr>
                 
                     <tr>
                        <td>
                            <img id="previewimg" width="150" height="150" style="border-radius: 50%; object-fit: cover;" />
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            <input type="file" name="profile" class="infoData img_file" accept="image/*" />
                        </td>
                    </tr>
             
            <tr>
                <td colspan="2" align="center" style="padding: 50px 0;">
                    <input type="button" value="프로필 사진 수정" id="btnUpdateProfile" class="btn btn-info btn-lg mr-5" />
                    <input type="reset" value="취소" class="btn btn-danger btn-lg" />
                </td>
            </tr>
            
        </tbody>
    </table>
</form>
