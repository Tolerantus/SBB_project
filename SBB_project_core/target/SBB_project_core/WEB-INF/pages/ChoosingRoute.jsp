<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link rel='stylesheet' href=<c:url value='/resources/stationChoose.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script type="text/javascript" src=<c:url value='/resources/ChoosingRoute.js'/>></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Choose route - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div id="dialog-message" title=""></div>
<div class="chunck">
	<div class="wrapper">
		<h2>Journey planning</h2>
		<c:if test="${sessionScope.routes !=null }">
			
		<table class="t">
			<tr>
				<td>
					Set time:
				</td>
				<td>
					<input type="time" id="time" name="time">
				</td>
				<td>
					<input type="date" id="date" name="date">
				</td>
			</tr>
		</table>		
						
		</c:if>
		<c:if test="${sessionScope.routes == null }">
			<h4 style="color:red;">Routes were not found</h4>
		</c:if>
		<input type="button" class="submit" value="Plan journey" id="plan">
		<form action="<c:url value="/creatingTrain"/>" method="get">
			<input type="submit" class="submit" value="Create train" id="train">
		</form>
				
		<form action="<c:url value="/newRoute"/>" method="get">
			<input type="submit" class="submit" value="Create Route">
		</form>
	</div>
	<c:if test="${sessionScope.routes ne null}">
		<div class="table_wrap">
			<table class="t">
				<tr>
					<td>
						<% %>
					</td>
					<th>
						#
					</th>
					<th>
						Route
					</th>
				</tr>
				<c:forEach var="route" items="${sessionScope.routes }">
					<tr>
						<td>
							<input type="radio" name="route" value="${route.key}" checked>
						</td>
						<td>
							${route.key}
						</td>
						<td>
							${route.value}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		var planBut = $('#plan');
		$(planBut).click(function(){
			var route = $("[name='route']").val();
			var time = $('#time').val();
			var date = $('#date').val();
			$.ajax({
				url: "/SBB_project_core/journey",
				type:"get",
				data:{
						route:route,
						time:time,
						date:date
					},
				beforeSend: function(xhr) {
					$('#cover').fadeIn(300);
				},
				complete: function() {
					$('#cover').fadeOut(300);
				},
				success:function(resp){
					$('#dialog-message').empty();
					if (resp == "time"){
						$('#dialog-message').attr("title",'Error');
						$('span.ui-dialog-title').text("Error");
						$('#dialog-message').append('Please, set date and time properly.');
					} else 
					if (resp=="train"){
						$('#dialog-message').attr("title",'Info');
						$('span.ui-dialog-title').text("Info");
						$('#dialog-message').append("All train are busy at this time.");
					} else
					if (resp=="fail"){
						$('#dialog-message').attr("title",'Error');
						$('span.ui-dialog-title').text("Error");
						$('#dialog-message').append("Creation failed");
					} else {
							$('#dialog-message').attr("title",'Info');
							$('span.ui-dialog-title').text("Info");
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
				}
			})
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