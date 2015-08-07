<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Journeys</title>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link rel="stylesheet" href=<c:url value='/resources/apprJours.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
</head>




<body>
<c:url var="buyTicketURL" value="/appropriateJourneys/buyTicket"/>
<c:if test="${user != null}">
<div id="top-menu">
	<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
	<div class="menu">
		<div id="menu">
			<c:url var="menuURL" value="/menu"/>
			<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
		</div>
	</div>
</div>
<h1>Journeys from station "${st_dep }" to station "${st_arr }"</h1>
<c:if test="${journeysData != null}">
<form action="${buyTicketURL }" method="post" name="jours">
<table id="t">
<thead>
	<tr>
		<th>
		</th>
		<th>
			№
		</th>
		<th>
			Departure
		</th>
		<th>
			Arrival
		</th>
		<th>
			Total cost
		</th>
	</tr>
</thead>
<%List<String> data = (List<String>)session.getAttribute("journeysData");
for (String s : data){String[] tokens = s.split(";");%>
<tr>
	<td>
	<input type="radio" name="journey" value="<%=tokens[0]%>" checked align="left">
	</td>
	<td>
	<%=tokens[0] %>
	</td>
	<td>
	<%=tokens[1] %>
	</td>
	<td>
	<%=tokens[2] %>
	</td>
	<td>
	<%=tokens[3] %>
	</td>
</tr>
<%}%>
</table>
</form>
<div class="wrapper">
	<div class="button-container">
		<input  class="submit" type="button" value="Next" onclick="document.forms['jours'].submit()" id="next">
	</div>
	
	<div class="button-container">
		<input class="submit" type="button" value="Back" onclick="document.forms['back'].submit()"> 
	</div>
</div>
</c:if>
<c:if test="${journeysData == null}">
<h3 align="center">Appropriate trains did not found</h3>
</c:if>

	

<c:if test="${journeysData == null}">
<script type="text/javascript">$('#next').hide();</script>
</c:if>
<c:url var="journeysURL" value="/schedule/journeys"/>
<form action="${journeysURL }" method="post" name="back"></form>
</c:if>
<c:if test="${user==null}">
	<h1 align="center" style="color:red">Unregistered user cannot look through this page!</h1>
	<div align="center">
		<form action="Auth.jsp">
			<input type="submit" class="submit" value="Login">
		</form>
	</div>
</c:if>	

</body>
</html>