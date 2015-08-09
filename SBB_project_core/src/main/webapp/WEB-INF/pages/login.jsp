<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns:th="http://www.thymeleaf.org" xmlns:tiles="http://www.thymeleaf.org">
<head>
	<link rel="stylesheet" href=<c:url value='/resources/auth.css'/>>
	<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
	<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script type="text/javascript" src=<c:url value='/resources/auth.js'/>></script>
	<title>Login - SBB</title>
</head>
<body onload='document.loginForm.username.focus();'>
<div class="wrapper">
	<form name='loginForm' th:action="@{/login}"  method="post">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<h1>Login</h1>
		<div id="fields">
			<div id="login-div">
				<input type="text" name="username" class="input-login" placeholder="e-mail" id="username">
				<div id="login-status">
					<span class="error-mes">${error}</span>
					<span class="successLogout">${msg}</span> <!--  -->
				</div>
			</div>
			<div id="pass-div">
				<input type="password" name="password" class="input-login" placeholder="password" id="password" >
				<div id="password-status" class="error-mes"><!--  --></div>
			</div>
		</div>
		<div> 
			<input type="submit" class="submit" value="submit" id="submit" >
			<a class="link" onclick="document.forms['reg'].submit()">register</a>
		</div>
		
	</form>
</div>

<form action="<c:url value="/newUserForm"/>" method="get" id="register" name='reg'></form>
</body>
</html>