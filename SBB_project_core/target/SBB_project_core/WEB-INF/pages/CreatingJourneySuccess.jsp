<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/CreatingJourneySuccess.css'/>>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<title>Journey created</title>
</head>
<body>
<c:if test="${user!=null}">
<c:if test="${admin==true }">
<div id="top-menu">
	<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
	<div >
		<div id="menu">
		<c:url var="menuURL" value="/menu"/>
			<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
		</div>
	</div>
</div>
<h1 align="center">New journey has been planned successfully!</h1>

<table id="t">
	<tr>
		<th>
		№
		</th>
		<th>
		Route
		</th>
		<th>
		Departure time
		</th>
		<th>
		Train №
		</th>
	</tr>
	<tr>
		<td>
			${id }
		</td>
		<td>
			${route }
		</td>
		<td>
			${date }
		</td>
		<td>
			${train }
		</td>
	</tr>
</table>
	</c:if>
<c:if test="${admin==false }">
<h3 align="center" style="color:red">You do not have permission to view this page!</h3>
	<form action="${menuURL }" >
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