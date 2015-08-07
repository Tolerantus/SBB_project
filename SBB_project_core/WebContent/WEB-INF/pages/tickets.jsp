<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/tickets.css'/>>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<title>Tickets</title>
</head>
<body>

<c:if test="${user!=null}">
<div id="top-menu">
<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
<div >
	<div id="menu">
		<c:url var="menuURL" value="/menu"/>
		<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>
</div>
<h1>Your tickets</h1>
<%
if (request.getAttribute("tickets")==null){
%>
<h3 align="center">You didn't buy any tickets!</h3>
<%
}else{%>
<table id="t">
<thead>
<tr>
	<th>
		Ticket №
	</th>
	<th>
		Passenger
	</th>
	<th>
		From
	</th>
	<th>
		Dep. time
	</th>
	<th>
		To
	</th>
	<th>
		Arr. time
	</th>
	<th>
		Cost
	</th>
</tr>
</thead>
<%
List<String> tickets = (List<String>)request.getAttribute("tickets");

for (String s:tickets){
	String[] data = s.split(",");
%>
<tr>
<%
	for (String token: data){
%>

<td>
<%=token%>
</td>

<%
}
%>
</tr>
<%
}
}
%>
</table>

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