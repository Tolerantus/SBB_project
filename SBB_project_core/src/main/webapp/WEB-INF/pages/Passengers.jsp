<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="java.util.List" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/JourneyChoosing.css'/>>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<title>Passengers - SBB</title>
</head>
<body>
<c:if test="${pageContext.request.userPrincipal.name != null}">
<%-- <c:if test="${admin==true }"> --%>
<div class="user">
	<img alt="" src=<c:url value='/resources/images/1.png'/>>
	<span class="user">${pageContext.request.userPrincipal.name}|<a href="<c:url value="/logout" />" > Logout</a></span>
</div><div >
	<div id="menu">
		
		<a href="<c:url value="/menu"/>"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>
<h1>Passengers registered on "${journey}"</h1>

<table id="t">
	<tr>
		<th>
		Name
		</th>
		<th>
		Surname
		</th>
		<th>
		Birthday
		</th>
	</tr>
	<%List<String> passengers = (List<String>)request.getAttribute("passengers"); %>
	<%for (String s : passengers){ %>
	<%String[] tokens = s.split(" "); %>
	<tr>
		<td>
			<%=tokens[0] %>
		</td>
		<td>
			<%=tokens[1] %>
		</td>
		<td>
			<%=tokens[2] %>
		</td>
	</tr>
	<%} %>
</table>

</c:if>
<%-- <c:if test="${admin==false }">
<h1 align="center" style="color:red">You do not have permission to view this page!</h1>
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