<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/auth.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Validation Error.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<title>Registration</title>
</head>
<body>
<c:url var="newUserURL" value="/newUser"/>
<c:url var="authURL" value="/login"/>
<div class="wrapper">
	
	<form action="${newUserURL }" method="Post" id="reg">
		<input type="hidden" name="action" value="NEW_USER">
		<h1>Registration</h1>
		<div id="login-div"><input type="text" name="username" class="input-login" placeholder="login" id="username"></div>
		<div id="pass-div1"><input type="password" name="password1" class="input-login" placeholder="password" id="password1"></div>
		<div id="pass-div2"><input type="password" name="password2" class="input-login" placeholder="repeat password" id="password2"></div>
		
		<div> 
		<input type="button" class="submit" onclick="validate()" value="register">
		<input type="button" class="submit" onclick="document.forms['back'].submit()" value="back">
		</div>
		
		<div>
		
		<span class="status">${error }</span>
		
		</div>
		
	</form>
</div>


<script type="text/javascript" src=<c:url value='/resources/NewUser.js'/>></script>
<form action="${authURL}" method="get" name="back"></form>
</body>
</html>