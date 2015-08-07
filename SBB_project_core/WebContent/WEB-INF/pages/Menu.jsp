<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
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
<c:url var="getShedule" value="/schedule"/>
<c:url var="findJourneyURL" value="/stationsChoosing"/>
<c:url var="checkTicketsURL" value="/myTickets"/>
<c:if test="${user!=null}">
<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
<div class="wrapper">
	<div class="menu-item">
		<form action="${getShedule }" method="post">
			<input type="hidden" name="action" value="GET_SHEDULE">
			<input type="submit" value="Show the schedule">
		</form>
	</div>
	<div class="menu-item">
		<form action="${findJourneyURL }" method="post">
			<input type="hidden" name="action" value="FIND_JOURNEY">
			<input type="submit" name="buy" value="Buy a ticket">
		</form>
	</div>
	<div class="menu-item">
		<form action="${checkTicketsURL }" method="post">
			<input type="hidden" name="action" value="CHECK_TICKETS">
			<input type="submit" name="check" value="Check my tickets">
		</form>
	</div>
	<c:url var="createRouteURL" value="/newRoute"/>
	<c:if test="${admin==true }">
	<div class="menu-item2">
		<form action="${createRouteURL }" method="post" name="newRouteForm" id="newRoute" >
			<input type="hidden" name="action" value="CREATE_ROUTE">
			<input type="submit" value="Create route" >
		</form>
	</div>
	<c:url var="createTrainURL" value="/creatingTrain"/>
	<div class="menu-item2">
		<form action="${createTrainURL }" method="post">
			<input type ="submit" value="Create train"> 
		</form>
	</div>
	<c:url var="getRoutesInfoURL" value="/routesInfo"/>
	<div class="menu-item2">
		<form action="${getRoutesInfoURL }" method="post">
			<input type="hidden" name="action" value="GET_ROUTES_INFO">
			<input type="submit" value="Create journey">
		</form>
	</div>
	<c:url var="stationCreatorURL" value="/newStationForm"/>
	<div class="menu-item2">
		<form action="${stationCreatorURL }" method="post">
			<input type="submit" value="Create station">
		</form>
	</div>
	<c:url var="journeyBriefURL" value="/journeyList"/>
	<div class="menu-item2">
		<form action="${journeyBriefURL }" method="post">
			<input type="hidden" name="action" value="GET_ALL_JOURNEYS_BRIEF">
			<input type="submit" value="Show passengers...">
		</form>
	</div>
	<c:url var="resetURL" value="/resetDB"/>
	<div class="menu-item2">
		<form action="${resetURL }" method="post">
			<input type="hidden" name="action" value="RESET_DB">
			<input type="submit" value="Reset DataBase">
		</form>
	</div>
	<c:url var="initURL" value="/initDB"/>
	<div class="menu-item2">
		<form action="${initURL }" method="post">
			<input type="hidden" name="action" value="INIT_DB">
			<input type="submit" value="Init DataBase">
		</form>	
	</div>
	</c:if>
	<c:url var="outURL" value="/out"/>
	<div class="menu-item">
		<form action="${outURL }" method="post">
			<input type="hidden" name="action" value="OUT">
			<input type="submit" name="out" value="LogOut">
		</form>
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