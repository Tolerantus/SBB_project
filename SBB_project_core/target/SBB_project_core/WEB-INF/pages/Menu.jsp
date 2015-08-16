<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>SBB - Menu</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div class="img_conteiner">
	
	<div class="figurer">
		<figure>
			<img alt="" src="/SBB_project_core/resources/images/train-6.jpg" class="image">
			<figcaption>The Swiss Federal Railways runs a large network of rail connections and helps coordinate a bus system that reaches into just about every corner of Switzerland</figcaption>
		</figure>
		
		<figure>
			<img alt="" src="/SBB_project_core/resources/images/train-5.jpg" class="image">
			<figcaption>Switzerland: See it! Feel it! Love it!</figcaption>
		</figure>
		<figure>
			<img alt="" src="/SBB_project_core/resources/images/train-3.JPG" class="image">
			<figcaption>
				A SBB CFF FFS train
			</figcaption>
		</figure>
		
	</div>
	
	<div class="figurer">
		<figure>
			<img alt="" src="/SBB_project_core/resources/images/train-4.jpg" class="image">
			<figcaption>
				Samsung Electronics sent out a press release last week saying its gadgets had been chosen for a technology buildout at Swiss Federal Railways.
			</figcaption>
		</figure>
		<figure>
			<img alt="" src="/SBB_project_core/resources/images/train-2.jpg" class="image">
			<figcaption>
				The inside of a double deck intercity train
			</figcaption>
		</figure>
		
		<figure>
			<img alt="" src="/SBB_project_core/resources/images/train-1.jpg" class="image">
			<figcaption>
				InterCity on the Gotthard Line
			</figcaption>
		</figure>
		
	</div>
</div>

<script type="text/javascript">
	$(window).load(function(){
	    $('#cover').fadeOut(300);
	    checkCash();
	});
</script>
</body>
</html>