<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src = <c:url value='/resources/StationAdding.js'/>></script>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<script type="text/javascript">
	$(window).load(function(){
	    $('#cover').fadeOut(300);
	    checkCash();
	});
</script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>New stations - SBB</title>
</head>


<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div class="chunck">
<div class="wrapper">
<h2>Add stations</h2>

<div id="editor">

<form action="<c:url value="/newRoute/newStartAndFinish/newStation"/>" method="post" name="add" id="addForm">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	<div id="selector">
		<table class="t">
			<tr>
				<td>
					Step:
				</td>
				<td>
					<select id="step" name="step">
						<option disabled>step</option>
						<%List<String> stations = (List<String>)session.getAttribute("newRoute"); %>
						<%for (int i=0; i<stations.size()-1; i++){%>
						<option selected ><%=i %></option>
						<%} %>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					Select old station:
				</td>
				<td>
					<c:if test="${allStations !=null}">
						<select id="stations" name = "stations">
							<option disabled="disabled">station</option>
							<c:forEach var="st" items="${allStations}">
								<option selected>${st}</option>
							</c:forEach>
						</select>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>
					Type new station:
				</td>
				<td>
					<input type="text" name="newStation" id="typed" placeholder="station">
				</td>
			</tr>
		</table>
	</div>	
</form >
</div>

<form action="<c:url value='/newRoute/newStartAndFinish/timeAndCost'/>" method="post" name="getTime">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
	
<div class="actions">
	<div align="center">
			<input type="button" class="submit" value="Add station" onclick="validate()">
	</div>

	<div align="center">
			<input type="button" class="submit" value="Stop" onclick="document.forms['getTime'].submit()">
	</div>
</div>	
</div>	
	<div class="table_wrap">
		<table class="t" id="route">
				<c:forEach var="st" items="${sessionScope.newRoute}">
					<form action="<c:url value="/newRoute/delete"/>" method="get" name="${st }">
						<input type="hidden" value="${st }" name="station">
					</form>
						<tr>
							<td><b>${st}</b></td>
							<td><input type="button" value="delete" onclick="document.forms['${st}'].submit()"></td>
						</tr>
					
				</c:forEach>
		</table>
	</div>
</div>
<script type="text/javascript">
		
			
			function del(station){
				
				$.ajax({
					url:address,
					type:"get",
					contentType: 'application/x-www-form-urlencoded; charset=utf-8',
					dataType: "JSON",
					
					beforeSend: function(xhr) {
						xhr.setRequestHeader("Accept", "application/json");
						xhr.setRequestHeader("Content-Type", "application/json");
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success:function(stations){
						var resp = "";
						$.each(stations, function(index, element){
							resp += "<tr>";
							resp += "<td>" + element + "</td>" + "<td><button onclick=del('/SBB_project_core/deleteStationInRoute/" + element + "')>delete</button></td>";
							resp += "</tr>";
						});
						$('#stations').empty();
						$('#stations').append(resp);
						if (stations.lenght >1){
							$('#insert').show();
						} else {
							$('#insert').hide();
						}
					}
				});
			}
		
		</script>
</body>
</html>