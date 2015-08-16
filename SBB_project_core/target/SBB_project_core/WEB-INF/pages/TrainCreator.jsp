<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel='stylesheet' href=<c:url value='/resources/Nav.css'/>>
<link rel='stylesheet' href=<c:url value='/resources/ValidationError.css'/>>
<link rel='stylesheet' href=<c:url value='/resources/stationChoose.css'/>>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script type="text/javascript" src=<c:url value='/resources/TrainCreator.js'/>></script>
<script type="text/javascript" src=<c:url value='/resources/CreatingStation.js'/>></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<title>Station/Train creator - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div id="dialog-message" title=""></div>
<div class="chunck">
	<div class="wrapper">
		<h2>Train creator</h2>
		<table class="t">
			<tr>
				<td>
					Seats:
				</td>
				<td>
					<select name="seats" id="seats">
						<option disabled>seats</option>
						<c:forEach var="i" begin="50" end="200" step="50">
							<option selected>${i }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<input type="button" class="submit" value="Create train" id="trainBut">
	</div>
	<div class="wrapper">
		<h2>Station creator</h2>
		<table class="t">
			<tr>
				<td>
					Station name:
				</td>
				<td>
					<input type="text" id="station" name="station" placeholder="station">
				</td>
			</tr>
		</table>
		<h4 id="error" style="color:red"></h4>
		<input type="button" class="submit" value="Create station" id="stationBut">
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		var trainBut = $('#trainBut');
		
		$(trainBut).click(function(){
			var seats = $('#seats').val();
			$.ajax({
				url:"/SBB_project_core/train",
				type:"get",
				data: {seats:seats},
				beforeSend: function(xhr) {
					
					$('#cover').fadeIn(300);
				},
				complete: function() {
					$('#cover').fadeOut(300);
				},
				success:function(result) {
					$('#dialog-message').empty();
					if (result=="success") {
						$('#dialog-message').attr("title",'Info');
						$('span.ui-dialog-title').text("Info");
						$('#dialog-message').append('Train created. Capacity = ' + seats);
					} else 
					if (result=="fail") {
						$('#dialog-message').attr("title",'Error');
						$('span.ui-dialog-title').text("Error");
						$('#dialog-message').append('Creating failed');
					}
					
				$("#dialog-message" ).dialog({
				      modal: true,
				      buttons: {
				        Ok: function() {
				          $( this ).dialog( "close" );
				        }
				      }
				  });
				},
				error: function (jqXHR, text, errorThrown) {
			        alert(jqXHR + " " + text + " " + errorThrown);
			    }
					
			});
		});
	});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		var stationBut = $('#stationBut');
		
		$(stationBut).click(function(){
			var station = $('#station').val();
			var patt =  new RegExp("^([A-Z])([a-z\\s]{1,20})[-]?[A-Z]?[a-z\\s]{0,19}[a-z0-9]$");
			var err = false;
			$('#station').removeClass('error-box');
			$('#error').empty();
			if (!patt.test(station)){
				$('#station').addClass('error-box');
				$('#error').text("Name must contain 3-25 symbols {A-Z a-z 0-9}")
				err=true;
			}
			if(!err){
				$.ajax({
					url:"/SBB_project_core/station",
					type:"get",
					data: {station:station},
					beforeSend: function(xhr) {
						
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success:function(status) {
						$('#dialog-message').empty();
						if (status == "exist"){
							$('#dialog-message').attr("title",'Info');
							$('span.ui-dialog-title').text("Info");
							$('#dialog-message').append('This stations is already exist.');
						} else 
						if (status=="fail"){
							$('#dialog-message').attr("title",'Error');
							$('span.ui-dialog-title').text("Error");
							$('#dialog-message').append("Creating failed");
						} else {
								$('#dialog-message').attr("title",'Info');
								$('#ui-id-1').text("Info");
								$('#dialog-message').append('Station "' + station + '"  created. ');
						}
						
						$("#dialog-message" ).dialog({
					      modal: true,
					      buttons: {
					        Ok: function() {
					          $( this ).dialog( "close" );
					        }
					      }
					  });
					},
					error: function (jqXHR, text, errorThrown) {
				        alert(jqXHR + " " + text + " " + errorThrown);
				    }
				});
			}
			
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