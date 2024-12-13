<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/admin.css" >
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/admin/movieRegister.css" >
    
<jsp:include page="/WEB-INF/header1.jsp" />

	<div class="mycontainer">
		<div class="myheader">
		
		</div>
		
		<div class="mybody">
			<section id="mysidebar">
				<div>AdminPage Home</div>
				<li>영화관리</li>
					<ul>
						<li><a href="<%= ctxPath%>/admin/movieRegister.up">영화 등록</a></li>
						<li><a>영화 수정/삭제</a></li>
					</ul>
				<li>공지관리</li>
					<ul>
						<li>공지 등록</li>
						<li>공지 수정/삭제</li>
					</ul>
				<li>유저관리</li>
					<ul>
						<li>유저 검색</li>
					</ul>
				<li>통계</li>
				
			</section>
			
			<section id="mycontent">
				<div>
					<div class="myrow">
					  <div class="col-75">
					    <div class="container">
					      <form action="/action_page.php">
					
					        <div class="myrow">
					          <div class="col-50">
					            <h3>Billing Address</h3>
					            <label for="fname"><i class="fa fa-user"></i> Full Name</label>
					            <input type="text" id="fname" name="firstname" placeholder="John M. Doe">
					            <label for="email"><i class="fa fa-envelope"></i> Email</label>
					            <input type="text" id="email" name="email" placeholder="john@example.com">
					            <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
					            <input type="text" id="adr" name="address" placeholder="542 W. 15th Street">
					            <label for="city"><i class="fa fa-institution"></i> City</label>
					            <input type="text" id="city" name="city" placeholder="New York">
					
					            <div class="myrow">
					              <div class="col-50">
					                <label for="state">State</label>
					                <input type="text" id="state" name="state" placeholder="NY">
					              </div>
					              <div class="col-50">
					                <label for="zip">Zip</label>
					                <input type="text" id="zip" name="zip" placeholder="10001">
					              </div>
					            </div>
					          </div>
					
					          <div class="col-50">
					            <h3>Payment</h3>
					            <label for="fname">Accepted Cards</label>
					            <div class="icon-container">
					              <i class="fa fa-cc-visa" style="color:navy;"></i>
					              <i class="fa fa-cc-amex" style="color:blue;"></i>
					              <i class="fa fa-cc-mastercard" style="color:red;"></i>
					              <i class="fa fa-cc-discover" style="color:orange;"></i>
					            </div>
					            <label for="cname">Name on Card</label>
					            <input type="text" id="cname" name="cardname" placeholder="John More Doe">
					            <label for="ccnum">Credit card number</label>
					            <input type="text" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
					            <label for="expmonth">Exp Month</label>
					            <input type="text" id="expmonth" name="expmonth" placeholder="September">
					
					            <div class="myrow">
					              <div class="col-50">
					                <label for="expyear">Exp Year</label>
					                <input type="text" id="expyear" name="expyear" placeholder="2018">
					              </div>
					              <div class="col-50">
					                <label for="cvv">CVV</label>
					                <input type="text" id="cvv" name="cvv" placeholder="352">
					              </div>
					            </div>
					          </div>
					
					        </div>
					        <label>
					          <input type="checkbox" checked="checked" name="sameadr"> Shipping address same as billing
					        </label>
					        <input type="submit" value="Continue to checkout" class="btn">
					      </form>
					    </div>
					  </div>
					

					</div>
				</div>
			</section>
		</div>
	</div>

<jsp:include page="/WEB-INF/footer1.jsp" />  