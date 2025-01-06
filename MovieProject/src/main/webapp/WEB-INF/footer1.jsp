<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
     <footer>
       <div id="footer_info">
          <p>(04377)서울특별시 용산구 한강대로 23길 55, 아이파크몰 6층(한강로동)<br>
			대표이사허민회사업자등록번호104-81-45690통신판매업신고번호2017-서울용산-0662 사업자정보확인<br>
			호스팅사업자CJ올리브네트웍스대표이메일cjcgvmaster@cj.net<br>© CJ CGV. All Rights Reserved
		</p>
		<a href="<%= ctxPath %>/admin/admin.mp" >관리자</a>
	<a href="<%= ctxPath %>/mypage/mypage.mp" >마이페이지</a>
       </div>
       
     </footer>
   
</body>
</html>