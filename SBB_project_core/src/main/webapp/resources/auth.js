/**
 * 
 */
function validate(form){

	$('#password').removeClass('error-box');
	
	$('#username').removeClass('error-box');
	
	var pass = $('#password').val();
	var username = $('#username').val();
	var error = false;
	var patt = new RegExp("^[A-Za-z0-9_]{4,15}$");
	var passTest = patt.test(pass);
	var nameTest = patt.test(username);
	if (pass==""||!passTest){
		
		$('#password').addClass('error-box');
		$('#pass-div').attr("title","invalid password");
		error = true;
	}
	if (username==""||!nameTest){
		$('#username').addClass('error-box')
		$('#login-div').attr("title","invalid login");
		error = true;
	}
	
	if (!error){
		$('#enter').submit();
	}
	
	

}