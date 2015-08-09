<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/Menu.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<title>Menu</title>
</head>
<body>


<c:if test="${pageContext.request.userPrincipal.name != null}">
<div class="user">
	<img alt="" src=<c:url value='/resources/images/1.png'/>>
	<span class="user">${pageContext.request.userPrincipal.name}|<a href="<c:url value="/logout" />" > Logout</a></span>
</div>
<div class="wrapper">
	<div class="menu-item">
		<form action="<c:url value="/schedule"/>" method="get">
			<input type="submit" value="Show the schedule">
		</form>
	</div>
	<div class="menu-item">
		<form action="<c:url value="/stationsChoosing"/>" method="get">
			<input type="submit" name="buy" value="Buy a ticket">
		</form>
	</div>
	<div class="menu-item">
		<form action="<c:url value="/myTickets"/>" method="get">
			<input type="submit" name="check" value="Check my tickets">
		</form>
	</div>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="menu-item2">
		<form action="<c:url value="/newRoute"/>" method="get" name="newRouteForm" id="newRoute" >
			<input type="submit" value="Create route" >
		</form>
	</div>
	
	<div class="menu-item2">
		<form action="<c:url value="/creatingTrain"/>" method="get">
			<input type ="submit" value="Create train"> 
		</form>
	</div>
	
	<div class="menu-item2">
		<form action="<c:url value="/routesInfo"/>" method="get">
			<input type="submit" value="Create journey">
		</form>
	</div>
	
	<div class="menu-item2">
		<form action="<c:url value="/newStationForm"/>" method="get">
			<input type="submit" value="Create station">
		</form>
	</div>
	
	<div class="menu-item2">
		<form action="<c:url value="/journeyList"/>" method="get">
			<input type="submit" value="Show passengers...">
		</form>
	</div>
	
	<div class="menu-item2">
		<form action="<c:url value="/resetDB"/>" method="get">
			<input type="submit" value="Reset DataBase">
		</form>
	</div>
	
	<div class="menu-item2">
		<form action="<c:url value="/initDB"/>" method="get">
			<input type="submit" value="Init DataBase">
		</form>	
	</div>
	</sec:authorize>
	<%-- </c:if> --%>
	
	
</div>
</c:if>


<c:if test="${pageContext.request.userPrincipal.name == null}">
	<h1 align="center" style="color:red">Unregistered user cannot look through this page!</h1>
	<div align="center">
		
		<form action="<c:url value="/login"/>">
			<input type="submit" class="submit" value="Login">
		</form>
	</div>
</c:if>	

</body>
</html>