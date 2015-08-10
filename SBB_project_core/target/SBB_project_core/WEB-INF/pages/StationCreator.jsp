<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/StationCreator.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Validation Error.css'/>>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="resources/CreatingStation.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>New station - SBB</title>
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
<%-- <c:if test="${admin == true }"> --%>
<div class="user">
	<img alt="" src=<c:url value='/resources/images/1.png'/>>
	<span class="user">${pageContext.request.userPrincipal.name}|<a href="<c:url value="/logout" />" > Logout</a></span>
</div><div >
	<div id="menu">	
		
		<a href="<c:url value="/menu"/>"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>
<h1>Station creator</h1>
<div class="wrapper">
	<span>Type station name here</span>
	<div>
			
			<form action="<c:url var="createStation" value="/newStation"/>" method="post" id="creator">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="text" id="station" name="station" placeholder="station">
			</form>
	</div>
	<div>
		
			<c:if test="${err !=null }">
				<h5 align="center" style="color:red">Station with the same name is already exist</h5>
			</c:if>
	</div>
</div>

		<div align="center">
				<input type="button" class="submit" value = "Create station" onclick="validate()">
		</div>
	</c:if>
<%-- <c:if test="${admin==false }">
<h3 align="center" style="color:red">You do not have permission to view this page!</h3>
	<form action="${menuURL }">
	<input type="submit" class="submit" value="Login">
	</form>
</c:if>
	</c:if> --%>
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