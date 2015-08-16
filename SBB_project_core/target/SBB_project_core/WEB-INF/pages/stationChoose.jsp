<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<title>Station choosing - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div class="chunck">
<div class="wrapper">
	<h2>Choose station</h2>
	<table class="t">
		<tr>
			<td>
				From:
			</td>
			<td>
				<select name="st_dep" id="st_dep">
					<option disabled>department</option>
					<c:forEach var="station" items="${allStations}">
						<option >${station}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				To:
			</td>
			<td>
				<select name="st_arr" id="st_arr">
					<option disabled>destination</option>
					<c:forEach var="station" items="${allStations}"> 
						<option selected>${station}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				Date:
			</td>
			<td>
				<div class="date">
					<input  type="date" name="date" id="date">
				</div>
			</td>
		</tr>	
	</table>
	<h4 style="color:red;" id="error"></h4>
	<table class="t" id="person"></table>
	<c:if test="${allStations!=null }">
		<div>
			<input type="button" class="submit" value="Find Journeys" name="${pageContext.request.contextPath}/searchJourneys/" id="find">
			<input type="button" class="submit" value="Register" id="reg" hidden="true">
		</div>
	</c:if>
</div>
<div class="table_wrap"><table id="result_tab" class="t"></table></div>
</div>
<div id="dialog-message" title="">
</div>
	<c:if test="${simpleShedule == null}">
		<script type="text/javascript">
		function noEqualStations(){
			$('#find').show();
			if ($('#st_dep').val()==$('#st_arr').val()){
				$('#find').hide();
			}
		}
		$('select').change(noEqualStations);
		noEqualStations();
		</script>
	</c:if>

	<script type="text/javascript">
		$(document).ready(function(){
			var searchButton = $("#find");
			var from = $('#st_dep');
			var to = $('#st_arr');
			var date = $('#date');
			var regBut = $('#reg');
			var personTable = $('#person');
			$(searchButton).click(function(event){
				$('#error').empty();
				$.ajax({
					url : "/SBB_project_core/searchJourneys",
					type : "GET",
					data : {
						from: from.val(),
						to: to.val(),
						date: date.val()
					},
					
					beforeSend: function() {
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success: function(list) {
						var resp = "";
						if (list != null && list.length > 0) {
							resp += "<tr><th></th><th> Journey </th><th> Department </th><th> Destination </th><th> Cost </th><th> Empty seats </th></tr>"; 
							$.each(list, function(index, element) {
								var tokens = element.split(";");
								resp += "<tr>";
								resp += "<td><input type='radio' name='journey' value=" + tokens[0] + " checked align='left'></td>";
								$.each(tokens, function(index, token) {
									resp += "<td>" + token + "</td>";
								});
								resp += "</tr>";
							});
							regBut.show();
							var person = "";
							person +="<tr><td>Name</td><td><input type='text' name='name' id='name' placeholder='name'></td></tr>";
							person +="<tr><td>Surname</td><td><input type='text' name='surname' id='surname' placeholder='surname'></td></tr>";
							person +="<tr><td>Birthday</td><td><input type='date' name='birthday' id='birthday' max='2015-08-01'></td></tr>";
							personTable.empty();
							personTable.append(person);
						} else {$('#error').text("Journeys were not planned.");}
						$("#result_tab").empty();
						$("#result_tab").append(resp);
					}
				});
				event.preventDefault();
			});
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			var regButton = $("#reg");
			
			$(regButton).click(function(event){
				
				var birthday = $('#birthday').val();			
				var journey = $("input[type='radio'][name='journey']:checked").val();
				$('#name').removeClass('error-box');
				$('#surname').removeClass('error-box');
				
				var patt = new RegExp("^[A-Za-z\\-'\\s]{1,40}$");
				var name = $('#name').val();
				var surname = $('#surname').val();
				var error = false;
				if (name==""||!patt.test(name)){
					$('#name').addClass('error-box');
					
					error = true;
				}
				if (surname==""||!patt.test(surname)){
					$('#surname').addClass('error-box');
					
					error = true;
				}
				if (birthday=="") {
					$('#birthday').addClass('error-box');
					error = true;
				}
				
				if (!error){
				
				$.ajax({
					url : "/SBB_project_core/registerPassenger",
					type : "POST",
					data:{
						name:name,
						surname:surname,
						birthday:birthday,
						journey:journey,
						"${_csrf.parameterName}" : "${_csrf.token}"
					},
					
					beforeSend: function() {
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success: function(ticket) {
						var resp = "";
						if (ticket == null) {
							alert("null");
						} else {
							if (ticket == "exist") {
								resp += "This person has already registered on the journey!"
								$('#dialog-message').empty();
								$('#dialog-message').attr("title",'Purchasing failed');
								$('span.ui-dialog-title').text('Purchasing failed');
								$('#dialog-message').append(resp);
							} else 
							if (ticket == "money") {
								resp += "Not enough money on your account."
								$('#dialog-message').empty();
								$('#dialog-message').attr("title",'Purchasing failed');
								$('span.ui-dialog-title').text('Purchasing failed');
								$('#dialog-message').append(resp);
							} else
							if (ticket = "birthday") {
								resp += "Set birthday properly."
								$('#dialog-message').empty();
								$('#dialog-message').attr("title",'Purchasing failed');
								$('span.ui-dialog-title').text('Purchasing failed');
								$('#dialog-message').append(resp);
							} else {
								var tokens = ticket.split(";");
								resp += "<p>Ticket #" + tokens[0] + "</p>";
								resp += "<p>Passenger: " + tokens[1] + " " + tokens[2] + "</p>";
								resp +="<p>From: " + tokens[3] + " " + tokens[4] + "</p>";
								resp += "<p>To: " + tokens[5] + " " + tokens[6] + "</p>";
								resp += "<p>Cost: " + tokens[7]+ "</p>";
								$('#dialog-message').empty();
								$('#dialog-message').attr("title",'Success purchasing');
								$('span.ui-dialog-title').text('Success purchasing');
								$('#dialog-message').append(resp);
							}
								$("#dialog-message" ).dialog({
							      modal: true,
							      buttons: {
							        Ok: function() {
							          $( this ).dialog( "close" );
							        }
							      }
							  });
								checkCash();
						}
						
					},
					error: function (jqXHR, text, errorThrown) {
				        alert(jqXHR + " " + text + " " + errorThrown);
				    }
				});
				event.preventDefault();
				};
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