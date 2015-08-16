/**
 * 
 */
function validate(form){
	$('#name').removeClass('error-box');
	$('#surname').removeClass('error-box');
	
	var patt = new RegExp("^[A-Za-z\\-'\\s]{1,40}$");
	var name = $('#name').val();
	var surname = $('#surname').val();
	var error = false;
	if (name==""||!patt.test(name)){
		$('#name').addClass('error-box');
		
		error = true;
	}
	if (surname==""||!patt.test(surname)){
		$('#surname').addClass('error-box');
		
		error = true;
	}
	
	if (!error){
		form.submit();
	}
}

