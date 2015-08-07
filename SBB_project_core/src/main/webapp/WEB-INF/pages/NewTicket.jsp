<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href=<c:url value='/resources/NewTicket.css'/> type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<title>You have bought the ticket!</title>
</head>


<body>
<c:if test="${user!=null}">
<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
<div >
	<div id="menu">
		<c:url var="menuURL" value="/menu"/>
		<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>
<h1 align="center">You have bought the ticket!</h1>
<%String ticketInfo = (String)request.getAttribute("ticketInfo");
String[] ticketInfo_tokens = ticketInfo.split(";");%>

<div class="wrapper">
	<div class="data">
		<b>Ticket №</b> <%=ticketInfo_tokens[0] %>
	</div>
	<div class="data">
		<b>Passenger</b>: <%=ticketInfo_tokens[1] %> <%=ticketInfo_tokens[2] %>
	</div>
	<div class="data">
		<b>Departure</b>: <%=ticketInfo_tokens[3] %> at <%=ticketInfo_tokens[4]%>
	</div>
	<div class="data">
		<b>Destination</b>: <%=ticketInfo_tokens[5] %> at <%=ticketInfo_tokens[6]%>
	</div>
	<div class="data">
		<b>Total cost</b>: <%=ticketInfo_tokens[7] %>
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