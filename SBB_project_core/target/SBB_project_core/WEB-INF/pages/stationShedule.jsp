<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/stationShedule.css'/>>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Station schedule</title>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
</head>
<body>

<c:if test="${user!=null}">

<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
<div >
	<div id="menu">
		<c:url var="menuURL" value="/menu"/>
		<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
	</div>
</div>
<script type="text/javascript" src=<c:url value='/resources/returnToAuth.js'/>></script>
<h1 align="center">Schedule for station "${station}"</h1>
<c:if test="${journeysData !=null }">
	<table id="t" >
	<thead>
		<tr>
			<th>
				Time
			</th>
			<th>
				From
			</th>
			<th>
				To
			</th>
			<th>
				№
			</th>
		</tr>
	</thead>	
		<%List<String> journeysData = (List<String>)session.getAttribute("journeysData"); %>
		<%for (String singleData : journeysData){%>
		<%String[] tokens = singleData.split(";");%>
			<tr>
				<td>
					<%=tokens[1]%>
				</td>
				<td>
					<%=tokens[2]%>
				</td>
				<td>
					<%=tokens[3]%>
				</td>
				<td>
					<%=tokens[0]%>
				</td>
			</tr>
		<%}%>
		
	</table>
</c:if>
<c:if test="${journeysData ==null}">
<h3 align="center">Journeys is not planned.</h3>
</c:if>
<div class="wrapper">
	
	<div class="button-container">
				<c:url var="scheduleURL" value="/schedule"/>
				<form action="${scheduleURL }" method="post" name="back" >
				<input type="hidden" name="action" value="GET_SHEDULE">
				<input type="button" class="submit" value="Back" onclick="document.forms['back'].submit()">
				</form>
	</div>
</div>	
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