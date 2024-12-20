<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
   String ctxPath = request.getContextPath();
    //     /MyMVC
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/reservation/reservation.css" />

<jsp:include page="../header1.jsp" />

<div class="container">
	
	<div style="display: flex;">
		<div style="border:solid 1px blue; width:45%; margin: 0 auto;">
			<h5 class="title">영화</h5>
		</div>
		<div style="border:solid 1px orange; width:10%; margin: 0 auto;">
			<h5 class="title">영화</h5>
		</div>
		<div style="border:solid 1px green; width:45%; margin: 0 auto;">
			<h5 class="title">영화</h5>
		</div>
	
	</div>

	<%--<div class="row">
		  <div class="col-md-4">
		      <h5 class="title">영화</h5>
		      <div>
		      	
		      </div>
		  </div>
		  <div class="col-md-4">
		      <h5 class="title">영화</h5>
		      <div>
		      	
		      
		     </div>
		  </div>
		  <div class="col-md-4">
		      <h5 class="title">영화</h5>
		      <div>
		      	
		      
		      </div>
		  </div>

		  
	</div>
 --%>
 
 
</div>

<jsp:include page="../footer1.jsp" />