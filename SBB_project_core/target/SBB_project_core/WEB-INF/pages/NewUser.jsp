<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/auth.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<c:url value="/resources/auth.js"/>"></script>
<script type="text/javascript" src="<c:url value='/resources/NewUser.js'/>"></script>
<title>Registration</title>
</head>
<body>
<form action="<c:url value='/login'/>" method="get" name="back"></form>

<div class="wrapper">
	
	<form action="<c:url value='/newUser'/>" method="Post" id="reg">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="action" value="NEW_USER">
		<h1>Registration</h1>
		<div id="login-div">
			<input type="text" name="username" class="input-login" placeholder="e-mail" id="username">
			<div id="login-status">
					<span class="error-mes">${error}</span>
					<!--  -->
				</div>
		</div>
		<div id="pass-div1">
			<input type="password" name="password1" class="input-login" placeholder="password" id="password">
			<div id="password-status" class="error-mes"><!--  --></div>
		</div>
		<div id="pass-div2">
			<input type="password" name="password2" class="input-login" placeholder="repeat password" id="password2">
			<div id="password-status2" class="error-mes"><!--  --></div>
		</div>
		
		<div> 
		<input type="button" class="submit" onclick="validate()" value="register">
		<input type="button" class="submit" onclick="document.forms['back'].submit()" value="back">
		</div>
		
		<div>
		
		
		</div>
		
	</form>
</div>



</body>
</html>