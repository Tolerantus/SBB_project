/**
 * 
 */
function validate(){
	var email = $('#username').val();
	var pas1 = $('#password').val();
	var pas2 = $('#password2').val();
	var pattPas = new RegExp("^[A-Za-z0-9_]{4,15}$");
	var pattEmail = new RegExp("^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,3})$");
	var error = false;
	if (email==""||!pattEmail.test(email)){
		error = true;
	}
	if (pas1==""||!pattPas.test(pas1)){
		error = true;
	}
	
	if (pas2==""||!pattPas.test(pas2)){
		error = true;
	}
	if (pas1!=pas2){
		error = true;
	}
	if (!error){
		$('#reg').submit();
	}
}

