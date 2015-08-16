<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" href=<c:url value='/resources/Nav.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/stationChoose.css'/>>
<link rel="stylesheet" href=<c:url value='/resources/ValidationError.css'/>>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script type="text/javascript" src=<c:url value='/resources/cash.js'/>></script>
<title>Manager - SBB</title>
</head>
<body>
<%@ include file="Nav.jsp" %>
<div id="cover"></div>
<div id="dialog-message" title=""></div>
<div class="chunck">
	<div class="wrapper">
	<h2>New manager</h2>
		<table class="t">
			<tr>
				<td>
					E-mail:
				</td>
				<td>
					<input type="text" id="email">
				</td>
				<td>
					<h4 id="emailErr" style="color:red; font-size:10px;">
					</h4>
				</td>
			</tr>
			<tr>
				<td>
					Password:
				</td>
				<td>
					<input type="password" id="pas1">
				</td>
				<td>
					<h4 id="pasErr" style="color:red; font-size:10px;">
					</h4>
				</td>
			</tr>
			<tr>
				<td>
					Repeat password:
				</td>
				<td>
					<input type="password" id="pas2">
				</td>
			</tr>
		</table>
		<input type="button" class="submit" id="send" value="Send confirmation letter" style="width:200px;">
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		var sendBut = $('#send');
		$(sendBut).click(function(){
			$('#emailErr').empty();
			$('#pasErr').empty();
			var email = $('#email').val();
			var pas1 = $('#pas1').val();
			var pas2 = $('#pas2').val();
			var emailPat = new RegExp("^[_A-Za-z0-9-]+(\\." +
					"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
					"(\\.[A-Za-z]{2,3})$");
			var pasPat = new RegExp("^[A-Za-z0-9_]{4,15}$");
			var err = false;
			if (!emailPat.test(email)){
				$('#emailErr').text("Invalid e-mail address.")
				err=true;
			}
			if (!pasPat.test(pas1)){
				$('#pasErr').text("Invalid password ([A-Z a-z 0-9 _] 4-15 symbols)")
				err = true;
			} else
			if (pas1 != pas2) {
				$('#pasErr').text("Passwords should be equal.")
				err = true;
			}
			if (!err) {
				$.ajax({
					url:"/SBB_project_core/sendLetterToManager",
					type:"post",
					data:{
						"${_csrf.parameterName}" : "${_csrf.token}",
						email:email,
						password:pas1
					},
					beforeSend: function() {
						$('#cover').fadeIn(300);
					},
					complete: function() {
						$('#cover').fadeOut(300);
					},
					success:function(resp){
						if (resp == "fail") {
							$('#dialog-message').empty();
							$('#dialog-message').attr("title",'Error');
							$('span.ui-dialog-title').text("Error");
							$('#dialog-message').append('Registration failed.');
						} else
						if (resp == "exist") {
							$('#dialog-message').empty();
							$('#dialog-message').attr("title",'Info');
							$('span.ui-dialog-title').text("Info");
							$('#dialog-message').append('This e-mail address is already registered.');
						} else {
							$('#dialog-message').empty();
							$('#dialog-message').attr("title",'Info');
							$('span.ui-dialog-title').text("Info");
							$('#dialog-message').append('Verification letter has been sent.');
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
				});
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