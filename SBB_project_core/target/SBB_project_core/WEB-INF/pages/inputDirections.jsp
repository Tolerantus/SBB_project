<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src=<c:url value='/resources/inputDirections.js'/>></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Directions - SBB</title>
</head>

<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div class="chunck">
<div class="wrapper" style="width: auto;">
<h2>Input new data for this route</h2>
<%List<String> directions = (List<String>) session.getAttribute("requiredDirectionData");%>

<form action="<c:url value="/newRoute/newStartAndFinish/timeAndCost/newRoute"/>" method="post" name="build" id="build">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<table class="t">
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
				<input type="text" name="<%=direction%>-cost" placeholder="##...###.##" style="height:30px;">
			</td>
			
		</tr>
		<%} %>
		<!-- </table>
		<table class="t"> -->
			<tr>
				<td>
					Route name:
				</td>
				<td>
					<input type="text"  name="route" id="route" placeholder="route name">
				</td>
			</tr>
		</table>
		
</form>
	<c:if test="${error!=null }"><h4 style="color:red;">Route with the same name is already exist</h4></c:if>
	<input type="button" class="submit" value="Submit" onclick="validate()">
</div>
	<div class="table_wrap">
		<table class="t" id="route">
			<c:forEach var="st" items="${sessionScope.newRoute}">
				<tr>
					<td><b>${st}</b></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<script type="text/javascript">
	$(window).load(function(){
	    $('#cover').fadeOut(300);
	    checkCash();
	});
</script>
</body>
</html>