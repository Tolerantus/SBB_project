<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Route creator - SBB</title>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src=<c:url value='/resources/RouteCreator.js'/>></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>

<div class="chunck">
<div class="wrapper" style="width:500px">
<h2 align="center">Choose start and finish of a new route or type new</h2>

<form action="<c:url value="/newRoute/newStartAndFinish"/>" method="post" name="creator" id="creator">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<div id="st_choose">
	<table class="t">
		<tr>
			<td>
				Start:
			</td>
			<td>
				<input class="type" type="text" name="dep" id="st_dep" placeholder="department">
			</td>
			<c:if test="${allStations ne null }">
				<td>
					<select id="old_st_dep" name = "old_st_dep">
						<option disabled="disabled">Start</option>
						<c:forEach var="st" items="${allStations}">
							<option selected>${st}</option>
						</c:forEach>
					</select>
				</td>
			</c:if>
		</tr>
		<tr>
			<td>
				Finish:
			</td>
			<td>
				<input class="type" type="text" name="arr" id="st_arr" placeholder="destination">
			</td>
			<c:if test="${allStations ne null }">
				<td>
					<select id="old_st_arr" name = "old_st_arr">
						<option disabled="disabled">Finish</option>
						<c:forEach var="st" items="${allStations}">
							<option selected>${st}</option>
						</c:forEach>
					</select>
				</td>
			</c:if>
		</tr>
	</table>
	
	
</div>
	<input type="button" class="submit" value="Create" id="create" >	
</form>
</div>
<div class="table_wrap">
	<table id="stations" class="t"></table>
</div>
</div>
		<script type="text/javascript">
			$(document).ready(function(){
				var createButton = $('#create');
				
				$(createButton).click(function(){
					
					var st_dep = $('#st_dep').val();
					var st_arr = $('#st_arr').val();
					var patt = new RegExp("^([A-Z])([a-z]{1,20})[-]?[A-Z]?[a-z]{0,19}[a-z0-9]$");
					var err = false;
					var isAnyStations = $('#old_st_dep').length?true:false;
					$('#st_dep').removeClass('error-box');
					$('#depStat').removeClass('error-mes');
					$('#depStat').empty();
					$('#st_arr').removeClass('error-box');
					$('#arrStat').removeClass('error-mes');
					$('#arrStat').empty();
					$('#old_st_dep').removeClass('error-box');
					$('#old_st_arr').removeClass('error-box');
					$('#selDep').removeClass('error-mes');
					$('#selDep').empty();
					
						if (st_dep!=""&&!patt.test(st_dep)){
							err = true;
							$('#st_dep').addClass('error-box');
							$('#depStat').addClass('error-mes');
							$('#depStat').text('invalid station name');
						}
						if (st_arr!=""&&!patt.test(st_arr)){
							err = true;
							$('#st_arr').addClass('error-box');
							$('#arrStat').addClass('error-mes');
							$('#arrStat').text('invalid station name');
						}
						
						if (st_arr==""&&!isAnyStations){
							$('#st_arr').addClass('error-box');
							$('#arrStat').addClass('error-mes');
							$('#arrStat').text('invalid station name');
							err=true;
						}
						if (st_dep==""&&!isAnyStations){
							err = true;
							$('#st_dep').addClass('error-box');
							$('#depStat').addClass('error-mes');
							$('#depStat').text('invalid station name');
						}
						if (st_arr==""&&st_dep==""&&$('#old_st_dep').val()==$('#old_st_arr').val()){
							$('#old_st_dep').addClass('error-box');
							$('#old_st_arr').addClass('error-box');
							$('#selDep').addClass('error-mes');
							$('#selDep').text('start and finish must be different');
							err=true;
						}
					
					if (!err){
						$('#creator').submit();
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