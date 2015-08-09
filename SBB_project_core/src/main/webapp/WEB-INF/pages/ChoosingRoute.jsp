<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/ChoosingRoute.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script type="text/javascript" src=<c:url value='/resources/ChoosingRoute.js'/>></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Choose route - SBB</title>
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
<%-- <c:if test="${admin==true }"> --%>
<div id="top-menu">
	<div class="user">
		<img alt="" src=<c:url value='/resources/images/1.png'/>>
		<span class="user">${pageContext.request.userPrincipal.name}|<a href="<c:url value="/logout" />" > Logout</a></span>
	</div>
	<div >
		<div id="menu">
			<a href="<c:url value="/menu"/>">
				<img alt="" src=<c:url value='/resources/images/home.png'/>>
			</a>
		</div>
	</div>
</div>
<h1>Choosing route</h1>
<c:if test="${sessionScope.routes !=null }">
	<form action="<c:url value="/routesInfo/newJourney/"/>" method="post" name="new" id="new">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<div id="routes">
		<ul>
			<c:forEach var="route" items="${sessionScope.routes }">
					<li>
					<input type="radio" name="route" value="${route.key}" checked>
					${route.value}
					</li>
			</c:forEach>
			
		</ul>
	</div >
		<div id="datetime">	
			<div class="time">
				<input type="time" id="time" name="time">
			</div>
			<div class="date">
				<input type="date" id="date" name="date">
			</div>
			<c:if test="${err!=null}">
			<span id="inv-date">Please, set time and date properly</span>
			</c:if>
		</div>
	</form>
</c:if>
<c:if test="${sessionScope.routes == null }">
	<h3 align="center">Routes were not found</h3>
</c:if>
<c:if test="${trainsLack != null }">
	<h3 align="center" style="color:red">Need more trains</h3>
</c:if>
	<div align="center">	
	
				<input type="button" class="submit" value="Plan journey" onclick="document.forms['new'].submit()">
				
				<form action="<c:url value="/newTrain"/>" method="get">
				</form>
				
				<form action="<c:url value="/newRoute"/>" method="get">
					<input type="submit" class="submit" value="Create Route">
				</form>
	</div>
	</c:if>
<c:if test="${admin==false }">
<h3 align="center" style="color:red">You do not have permission to view this page!</h3>
	<form action="<c:url value="/menu"/>" method="get">
	<input type="submit" class="submit" value="Login">
	</form>
</c:if>
<%-- </c:if> --%>
<%-- <c:if test="${user==null}">
	<h1 align="center" style="color:red">Unregistered user cannot look through this page!</h1>
	<div align="center">
		<c:url var="loginURL" value="/login"/>
		<form action="${loginURL }" method="get">
			<input type="submit" class="submit" value="Login">
		</form>
	</div>
</c:if>		 --%>

</body>
</html>