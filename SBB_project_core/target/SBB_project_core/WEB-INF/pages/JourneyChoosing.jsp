<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<title>Passengers - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div id="dialog-message" title=""></div>
<div class="chunck">
	<div class="wrapper">
		<h2>Passenger scanner</h2>
		<table class="t">
			<tr>
				<td>
					From:
				</td>
				<td>
					<input type="date" id="from">
				</td>
			</tr>
			<tr>
				<td>
					To:
				</td>
				<td>
					<input type="date" id="to">
				</td>
			</tr>
		</table>
		<h4 id="error" style="color:red;"></h4>
		<table class="t" id="journeys_tab">
			
		</table>
		<input type="button" class="submit" value="Show passengers" id="show">
		<input type="button" class="submit" value="Search journeys" id="search">
		
		
	</div>
	<div class="table_wrap">
		<table class="t" id="passengers">
			
		</table>
	</div>
</div>	
	<script type="text/javascript">
		$(document).ready(function(){
			var searchBut = $('#search');
			$(searchBut).click(function(){
				var start = $('#from').val();
				var stop = $('#to').val();
				$.ajax({
					url:"/SBB_project_core/journeys",
					type:"get",
					data:{
						start:start,
						stop:stop
					},
					beforeSend: function(xhr) {
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success:function(list){
						if (list == null || list.length == 0) {
							$('#error').text("No one journey in chosen period.")
							$('#journeys_tab').empty();
							$('#passengers').empty();
						} else {
							var resp = "";
							resp += "<tr><td>Journeys:</td><td>";
							resp += "<select name='journey' id='journey'>";
							resp += "<option disabled>journey</option>";
							$.each(list, function(index, element){
								resp += "<option selected>" + element + "</option>";
							});
							resp += "</td></tr>";
							$('#journeys_tab').empty();
							$('#passengers').empty();
							$('#error').empty();
							$('#journeys_tab').append(resp);
						}
					}
				});
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			var showBut = $('#show');
			$(showBut).click(function(){
				var journey = $('#journey').val();
				$.ajax({
					url:"/SBB_project_core/passengers",
					type:"get",
					data:{journey:journey},
					beforeSend: function(xhr) {
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success:function(resp){
						$('#passengers').empty();
						var tableCont = "";
						if (resp.length==0){
							$('#dialog-message').attr("title",'Info');
							$('span.ui-dialog-title').text("Info");
							$('#dialog-message').empty();
							$('#dialog-message').append('No one passenger has registered on this journey.');
							$("#dialog-message" ).dialog({
							      modal: true,
							      buttons: {
							        Ok: function() {
							          $( this ).dialog( "close" );
							        }
							      }
							  });
						} else
						if (resp.length==1 && resp[0]=="fail") {
							$('#dialog-message').attr("title",'Error');
							$('span.ui-dialog-title').text("Error");
							$('#dialog-message').empty();
							$('#dialog-message').append('Scanning failed.');
							$("#dialog-message" ).dialog({
							      modal: true,
							      buttons: {
							        Ok: function() {
							          $( this ).dialog( "close" );
							        }
							      }
							  });
						} else {
							tableCont += "<tr><th>Ticket #</th><th>Department</th><th>Destination</th><th>Purchase date</th><th>Name</th><th>Surname</th><th>Birthday</th></tr>";

							$.each(resp, function(index, element){
								var tokens = element.split(";");
								tableCont += "<tr><td>" + tokens[0] + "</td><td>" + tokens[1] + "</td><td>" + tokens[2] + "</td><td>" + tokens[3] + "</td><td>" + tokens[4] + "</td><td>" + tokens[5] + "</td><td>" + tokens[6] + "</td></tr>";
							});
							
							$('#passengers').append(tableCont);
						}
					}
				});
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