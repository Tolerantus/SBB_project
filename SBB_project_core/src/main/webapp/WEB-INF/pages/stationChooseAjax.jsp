<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Station schedule - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
	<div class="chunck">
	<div class="wrapper" >
		<h2>Schedule</h2>
		<table class="t">
			<tr>
				<td>
					From:
				</td>
				<td>
					<select name="station" id="station">
						<option disabled>your station</option>
						<c:forEach var="station" items="${allStations}"> 
							<option selected>${station}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<h4 id="error" style="color:red;"></h4>
		<c:if test="${allStations!=null }">
			<div>
				<input type="button" class="submit" value="Search journeys"
				id="find" name="${pageContext.request.contextPath}/searchJourneys/">
			</div>
		</c:if>
	</div>
	<div class="table_wrap"><table id="result_tab" class="t"></table></div>

</div>
<div class="menu">
		
	</div>			
	<script type="text/javascript">
		$(document).ready(function(){
			var searchButton = $("#find");
			var from = $('#station');
			$(searchButton).click(function(event){
				$.ajax({
					url : $(event.target).attr("name") + $('#station').val(),
					type : "GET",
					
					beforeSend: function(xhr) {
						xhr.setRequestHeader("Accept", "application/json");
						xhr.setRequestHeader("Content-Type", "application/json");
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success: function(list) {
						$("#error").empty();
						var resp = "";
						if (list != null && list.length > 0) {
							resp += "<tr><th> Journey </th><th> Department time </th><th> From </th><th> To </th></tr>"; 
							$.each(list, function(index, element) {
								var tokens = element.split(";");
								resp += "<tr>";
								$.each(tokens, function(index, token) {
									resp += "<td>" + token + "</td>";
								});
								resp += "</tr>";
							});
						} else {
							$("#error").text("Journeys were not planned");
						}
						$("#result_tab").empty();
						$("#result_tab").append(resp);
					}
				});
				event.preventDefault();
			});
		});
	</script>		
	<script type="text/javascript">
	$(window).load(function(){
	    $('#cover').fadeOut(300);
	    checkCash();
	});
	</script>


</body>
</html>