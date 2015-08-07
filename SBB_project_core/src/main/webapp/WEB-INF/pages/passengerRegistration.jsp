<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/passengerRegistration.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Validation Error.css'/>>
	<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>


<title>Passenger registration</title>
</head>
<body>
<c:if test="${user!=null}">

<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
<div >
	<div id="menu">
		<c:url var="menuUrl" value="/menu"/>
		<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>
<c:if test="${emptySeats !=null}">
	<c:if test="${emptySeats>0}">
		<h1 align="center">${emptySeats} seats available on this train</h1>
		<c:url var="regPasURL" value="/appropriateJourneys/buyTicket/passenger"/>
		<div class="wrapper">
			<form action="${regPasURL }" name="persData" method="post">
				<input type="hidden" name="action" value="REGISTER_PASSENGER">
				
					<c:if test="${error}">
					<div>
						<span id="samePassenger">Passenger with the same personal data has been registered on this journey!</span>
					</div>
					</c:if>
				
				
				<div>
					<input type="text" name="name" id="name" placeholder="name" class="name">
				</div>
				<div>
					<input type="text" name="surname" id="surname" placeholder="surname" class="name">
				</div>
				<div>
					<span id="birthday" class="date">Birthday: </span>
					<div class="date">
						<select id="year" name="year">
						<option disabled>year</option>
						<%for (int i=1915; i<=2015;i++){ %>
						<option><%=i %></option>
						<%} %>
						</select>
					</div>
					<div class="date">
						<select id="month" name="month">
						<option disabled>month</option>
						<%for (int i=1; i<=12; i++){ %>
						<option><%=i %></option>
						<%} %>
						</select>
					</div>
					<div class="date">
						<select id="day" name="day">
						</select>
					</div>
				</div>
			</form>
		</div>		
		
<script type="text/javascript" src=<c:url value='/resources/passengerRegistrationDate.js'/>></script>
<script type="text/javascript" src=<c:url value='/resources/passengerRegistrationValid.js'/>></script>
		
		
	</c:if>
</c:if>
<c:if test="${error !=null}">
	
</c:if>

<div class="menu">

	<div class="menu-item">
		<input type="button" value="Register passenger" onclick="validate(document.forms['persData'])">
	</div>

</div>
	
</c:if>
<c:if test="${user==null}">
	<h1 align="center" style="color:red">Unregistered user cannot look through this page!</h1>
	<div align="center">
		<c:url var="loginURL" value="/login"/>
		<form action="${loginURL }">
			<input type="submit" class="submit" value="Login">
		</form>
	</div>
</c:if>	
</body>
</html>