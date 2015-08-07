<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/error.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Ups.. something's gone wrong</title>
</head>
<body>
<div id="top-menu">
	<div class="user"><img alt="" src=<c:url value='/resources/images/1.png'/>><span class="user"><%=session.getAttribute("user")%></span></div>
	<div >
		<div id="menu">
			<c:url var="menuURL" value="/menu"/>
			<a href="${menuURL }"><img alt="" src=<c:url value='/resources/images/home.png'/>></a>
		</div>
	</div>
</div>
<h1 align="center">Something's gone wrong!</h1>

</body>
</html>