<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<title>Station choosing - SBB</title>
</head>
<body>
<c:if test="${pageContext.request.userPrincipal.name != null}">
<div id="top-menu">
	<div class="user">
	<img alt="" src=<c:url value='/resources/images/1.png'/>>
	<span class="user">${pageContext.request.userPrincipal.name}|<a href="<c:url value="/logout" />" > Logout</a></span>
</div><div >
		<div id="menu">
			
			<a href="<c:url value="/menu"/>"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
		</div>
	</div>
</div>
	<h2>Choose station</h2>
	
	<div class="wrapper" align="center">
		<form action="<c:url value='/appropriateJourneys'/>" method="post" name="stations">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		
		<c:if test="${simpleShedule == null}">
		<div class="selector" align="center">
			<span><img class="flag" alt="" src=<c:url value='/resources/images/start.png'/>></span>
			<select name="st_dep" id="st_dep">
				<option disabled>department</option>
				<c:forEach var="station" items="${allStations}">
					<option >${station}</option>
				</c:forEach>
			</select>
		
		
				<select name="st_arr" id="st_arr">
					<option disabled>destination</option>
					<c:forEach var="station" items="${allStations}"> 
					<option selected>${station}</option>
					</c:forEach>
				</select>
				<span><img class="flag"  alt="" src=<c:url value='/resources/images/stop.png'/>></span>
		</div>
		<div class="date">
			
			<input  type="date" name="date">
		</div>
	</c:if>
	<c:if test="${simpleShedule != null}">
		
		<div class="selector" align="center">
				<span id="flag"><img class="flag" alt="" src=<c:url value='/resources/images/start.png'/>></span>
				<select name="station" id="station">
					<option disabled>your station</option>
					<c:forEach var="station" items="${allStations}"> 
					<option selected>${station}</option>
					</c:forEach>
				</select>
		</div>
	</c:if>
	</form>
</div>


<div align="center" class="noway">
	<c:if test="${error !=null }">
		<span>No trains found!</span>
	</c:if>
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

	
	<div class="menu" align="center">
		<c:if test="${allStations!=null }">
		<div>
				<input type="button" class="submit" value="Find trains" onclick="document.forms['stations'].submit()" id="find">
		</div>
		</c:if>
		
	</div>			
			


</c:if>
<c:if test="${pageContext.request.userPrincipal.name == null}">
	<h1 align="center" style="color:red">Unregistered user cannot look through this page!</h1>
	<div align="center">
		<form action="<c:url value='/login'/>">
			<input type="submit" class="submit" value="Login">
		</form>
	</div>
</c:if>	
</body>
</html>