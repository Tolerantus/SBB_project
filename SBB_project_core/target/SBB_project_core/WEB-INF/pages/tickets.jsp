<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Tickets - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div class="wrapper" style="width:auto;">
	<h2>Your tickets</h2>
	<c:if test="${tickets ==null }">
		<h4 style="color:red;">You didn't buy any tickets!</h4>
	</c:if>
	<c:if test="${tickets ne null}">
		<table class="t">
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
						Department
					</th>
					<th>
						To
					</th>
					<th>
						Arrival
					</th>
					<th>
						Cost
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ticket" items="${tickets }">
					<tr>
					<c:forTokens items="${ticket }" delims="," var="token">
						<td>${token }</td>
					</c:forTokens>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>
<script type="text/javascript">
	$(window).load(function(){
	    $('#cover').fadeOut(300);
	    checkCash();
	});
	</script>
</body>
</html>