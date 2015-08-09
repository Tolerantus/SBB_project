<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/JourneyChoosing.css'/>>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Passengers - SBB</title>
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
<%-- <c:if test="${admin==true }"> --%>
<div class="user">
	<img alt="" src=<c:url value='/resources/images/1.png'/>>
	<span class="user">${pageContext.request.userPrincipal.name}|<a href="<c:url value="/logout" />" > Logout</a></span>
</div>
	<div >
	<div id="menu">
		<c:url var="menuURL" value="/menu"/>
		<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>
<h1>Passenger scanner</h1>
	<c:if test="${journeys!=null }">
	<div class="wrapper">
	<span>Choose journey</span>
	<c:url var="getPassengersURL" value="/journeyList/passengers"/>
			<form action="${getPassengersURL }" method="post" id="pas" name="pas">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<select name="journey">
					<option disabled>journey</option>
					<c:forEach var="journey" items="${journeys}">
					<option>${journey }</option>
					</c:forEach>
				</select>
			</form>
			
			<c:if test="${err!=null }">
			<h5 style="color:red" align="center">Passengers were not registered</h5>
			</c:if>
	</div>
	</c:if>
	<c:if test="${journeys ==null }">
	<h1 align="center">Journeys should be planned</h1>
	</c:if>
	<div align="center">
		<input type="button" class="submit" value="Show passengers" onclick="document.forms['pas'].submit()">
	</div>
	</c:if>
<%-- <c:if test="${admin==false }">
<h1 align="center" style="color:red">You do not have permission to view this page!</h1>
	<div align="center">
	<form action="${menuURL }">
	<input type="submit" class="submit" value="Login">
	</form>
	</div>
</c:if>
	</c:if> --%>
<c:if test="${user==null}">
	<h1 align="center" style="color:red">Unregistered user cannot look through this page!</h1>
	<div align="center">
		
		<form action="<c:url value="/login"/>">
			<input type="submit" class="submit" value="Login">
		</form>
	</div>
</c:if>	
	
</body>
</html>