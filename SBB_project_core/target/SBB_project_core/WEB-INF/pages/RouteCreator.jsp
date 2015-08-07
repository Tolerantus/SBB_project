<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/RouteCreator.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Route creator</title>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
</head>
<body>

<c:if test="${user!=null}">
<c:if test="${admin==true }">
<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
<div >
	<div id="menu">
		<c:url var="menuURL" value="/menu"/>
		<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>

<h1 align="center">Choose start and finish stations of a new route or type new</h1>
<div class="wrapper">
<c:url var="startFinishURL" value="/newRoute/newStartAndFinish"/>
<form action="${startFinishURL }" method="post" name="creator" id="creator">
<input type="hidden" name="action" value="CREATE_START_FINISH">
<div id="st_choose">
	<input class="type" type="text" name="dep" id="st_dep" placeholder="department">

	<input class="type" type="text" name="arr" id="st_arr" placeholder="destination">

	
	<c:if test="${allStations != null }">
	
			<select id="old_st_dep" name = "old_st_dep">
					<option disabled="disabled">Start</option>
					<c:forEach var="st" items="${allStations}">
					<option selected>${st}</option>
					</c:forEach>
			</select>
		
			<select id="old_st_arr" name = "old_st_arr">
					<option disabled="disabled">Finish</option>
				<c:forEach var="st" items="${allStations}">
					<option selected>${st}</option>
				</c:forEach>
			</select>
		</c:if>
	</div>
		
</form>
</div>
	<div align="center">
			<input type="button" class="submit" value="Create" onclick="validate()">
	</div>	

<script type="text/javascript" src=<c:url value='/resources/RouteCreator.js'/>></script>
</c:if>
<c:if test="${admin==false }">
<h3 align="center" style="color:red">You do not have permission to view this page!</h3>
	<form action="${menuURL }">
	<input type="submit" class="submit" value="Login">
	</form>
</c:if>
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