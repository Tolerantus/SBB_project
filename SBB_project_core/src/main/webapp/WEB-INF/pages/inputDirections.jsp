<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src=<c:url value='/resources/inputDirections.js'/>></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href=<c:url value='/resources/InputDirection.css'/>>
<title>Input direction data - SBB</title>
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
<h1>Input new data for this route</h1>
<%List<String> directions = (List<String>) session.getAttribute("requiredDirectionData");%>

<form action="<c:url value="/newRoute/newStartAndFinish/timeAndCost/newRoute"/>" method="post" name="build" id="build">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<table id="t">
	<%if (directions!=null&&directions.size()>0){ %>
		<tr>
			<th>
				direction
			</th>
			<th>
				hours
			</th>
			<th>
				minutes
			</th>
			<th>
				cost
			</th>
		</tr><%} %>
	
		<%for (String direction : directions){ %>
		<tr>
			<td>
				<%=direction %>
			</td>
			<td>
				<select name="<%=direction%>-time-hours" >
					
					<%for (int i=100; i>=0; i--){ %>
					<option selected><%=i %></option>
					<%} %>
					<option disabled>hours</option>
				</select>
			</td>
			<td>
				<select name="<%=direction%>-time-minutes">
					
					<%for (int i=59; i>=0; i--){ %>
					<option selected><%=i %></option>
					<%} %>
					<option disabled>minutes</option>
				</select>
			</td>
			<td>
				<input type="text" name="<%=direction%>-cost" placeholder="##...###.##">
			</td>
			
		</tr>
		<%} %>
		</table>
		<div align="center" id="route_name">
				
			
				<input type="text"  name="route" id="route" placeholder="route name">
		</div>
		
	
</form>
<c:if test="${error!=null }"><h4 align="center">Route with the same name is already exist</h4></c:if>



	<div align="center">
			<input type="button" class="submit" value="Submit" onclick="validate()">
	</div>
		
	
	
	
	</c:if>
<%-- <c:if test="${admin==false }">
<h3 align="center" style="color:red">You do not have permission to view this page!</h3>
	<form action="${menuURL }">
	<input type="submit" class="submit" value="Login">
	</form>
</c:if> --%>
<%-- </c:if> --%>
<c:if test="${pageContext.request.userPrincipal.name == null}">
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