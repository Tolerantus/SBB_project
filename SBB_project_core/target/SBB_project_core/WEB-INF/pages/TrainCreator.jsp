<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href=<c:url value='/resources/TrainCreator.css'/>>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>

<script type="text/javascript" src=<c:url value='/resources/TrainCreator.js'/>></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>New train - SBB</title>
</head>
<body>
<c:if test="${pageContext.request.userPrincipal.name != null}">
<%-- <c:if test="${admin==true }"> --%>
<div id="top-menu">
<div class="user">
	<img alt="" src=<c:url value='/resources/images/1.png'/>>
	<span class="user">${pageContext.request.userPrincipal.name}|<a href="<c:url value="/logout" />" > Logout</a></span>
</div>	<div >
		<div id="menu">
			
			<a href="<c:url value="/menu"/>"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
		</div>
	</div>
</div>
<h1>Train creator</h1>
<c:url var="createTrainURL" value="/newTrain"/>
<form action="${createTrainURL }" name='creator' method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<input type="hidden" name="action" value="CREATE_TRAIN">
<div id="creator">
		<div align="center">
				Choose number of seats
		</div>	
		<div align="center">
				<select name="seats">
					<option disabled>seats</option>
					<c:forEach var="i" begin="50" end="200">
						<option selected>${i }</option>
					</c:forEach>
				</select>
		</div>
	</div>
</form>

	<div align="center">
	
			<input class="submit" type="button" value="Create train" onclick="document.forms['creator'].submit()">
	
	</div>	
	
	
	
	</c:if>
<%-- <c:if test="${admin==false }">
<h3 align="center" style="color:red">You do not have permission to view this page!</h3>
	<form action="${menuURL }">
	<input type="submit" class="submit" value="Login">
	</form>
</c:if>
</c:if> --%>
<c:if test="${pageContext.request.userPrincipal.name == null}">
	<h1 align="center" style="color:red">Unregistered user cannot look through this page!</h1>
	<div align="center">
		
		<form action="<c:url value="/login"/>">
			<input type="submit" class="submit" value="Login">
		</form>
	</div>
</c:if>	
</body>
</html>