<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="java.util.List" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>New route info - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div class="chunck">
	<div class="wrapper">
		<h2>New route info</h2>
		<%List<String> data = (List<String>)session.getAttribute("info"); %>
		<table class="t">
		<thead>
			<tr>
				<th>
					Route name
				</th>
				<th>
					Hours
				</th>
				<th>
					Minutes
				</th>
				<th>
					Overall cost
				</th>
				
			</tr>
		</thead>
			<tr>
				<td>
					<%=data.get(0) %>
				</td>
				<td>
					<%=data.get(1) %>
				</td>
				<td>
					<%=data.get(2) %>
				</td>
				<td>
					<%=data.get(3) %>
				</td>
			</tr>
		</table>
	</div>
	<div class="table_wrap" style="margin-left:20px;">
		<table class="t">
			<tr>
				<td>	
				<b>Stations</b>
				</td>
			</tr>
			<%for (int i=4; i<data.size(); i++){ %>
			<tr>
				<td>
					<%=data.get(i) %>
				</td>
			</tr>
			<%} %>
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