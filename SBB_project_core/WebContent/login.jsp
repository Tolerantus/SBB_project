<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link rel="stylesheet" href=<c:url value='/resources/auth.css'/>>
	<link rel="stylesheet" href=<c:url value='/resources/Validation Error.css'/>>
	<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<%boolean correctData = false; %>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#username').keyup(function(){
				
				var username = $('#username');
				var login_status = $('#login-status');
				username.removeClass('error-box');
				login_status.addClass('error-mes');
				var data = $('#username').val();
				var patt = new RegExp("^[_A-Za-z0-9-]+(\\." +
						"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
						"(\\.[A-Za-z]{2,})$");
				
				if (data.length < 4||data.length > 15) {
					username.addClass('error-box');
					login_status.text("login should be longer than 3 and shorter than 16 symbols");
					login_status.slideDown('slow');
					correctData = false;
				} else
					if (!patt.test(data)) {
						username.addClass('error-box');
						login_status.text("login contains invalid symbols");
						login_status.slideDown('slow');
						correctData = false;
					} else
						{
							login_status.slideUp('slow');
							correctData = true;
						}
			});
		});
		</script>
		<script type="text/javascript">
		$(document).ready(function(){
			$('#password').keyup(function(){
				var password = $('#password');
				var password_status = $('#password-status');
				password.removeClass('error-box');
				password_status.addClass('error-mes');
				var data = $('#password').val();
				var patt = new RegExp("^[A-Za-z0-9_]{4,15}$");
				
				if (data.length < 4||data.length > 15) {
					password.addClass('error-box');
					password_status.text("password should be longer than 3 and shorter than 16 symbols");
					password_status.slideDown('slow');
					correctData = false;
				} else
					if (!patt.test(data)) {
						password.addClass('error-box');
						password_status.text("password contains invalid symbols");
						password_status.slideDown('slow');
						correctData = false;
					} else {
						password_status.slideUp('slow');
						correctData = true;
					}
			});
		});
	</script>
	
	
	
		<title>Authorization</title>
</head>
<body onload='document.loginForm.username.focus();'>
<c:url var="authURL" value="/menu"/>
<div class="wrapper">
	<form action="<c:url value='/j_spring_security_check' />" method='POST' id="enter" name="loginForm">
		<h1>Login</h1>
		<div id="fields">
			<div id="login-div">
				<input type="text" name="username" class="input-login" placeholder="login" id="username">
				<div id="login-status" class="error-mes">${error}<!--  --></div>
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
<c:url var="regURL" value="/login"/>
<form action="${regURL }" method="post" id="register" name='reg'></form>
</body>
</html>